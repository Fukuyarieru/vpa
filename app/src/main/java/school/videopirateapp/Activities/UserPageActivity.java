package school.videopirateapp.Activities;

import static android.view.View.INVISIBLE;
import static school.videopirateapp.Utilities.ByteListToBitmap;
import static school.videopirateapp.Utilities.Feedback;
import static school.videopirateapp.Utilities.MapToArrayList;
import static school.videopirateapp.Utilities.openUserOwnerOptionsDialog;
import static school.videopirateapp.Utilities.openUserViewerOptionsDialog;
import static school.videopirateapp.Utilities.openVideoPage;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import school.videopirateapp.datastructures.Comment;
import school.videopirateapp.datastructures.Playlist;
import school.videopirateapp.datastructures.User;
import school.videopirateapp.datastructures.Video;
import school.videopirateapp.database.Database;
import school.videopirateapp.GlobalVariables;
import school.videopirateapp.ListViewComponents.CommentAdapter;
import school.videopirateapp.ListViewComponents.PlaylistAdapter;
import school.videopirateapp.ListViewComponents.VideoAdapter;
import school.videopirateapp.R;
import school.videopirateapp.Utilities;

public class UserPageActivity extends AppCompatActivity {


    User user;
    ArrayList<Video> videos;
    ArrayList<Playlist> playlists;
    ArrayList<Comment> comments;

    ImageView UserImage;
    TextView UserDescription;
    TextView UserName;
    ListView listView;
    Button btnVideos;
    Button btnPlaylists;
    Button btnComments;
    Button btnBack;
    Button btnUserOptions;

    VideoAdapter videoAdapter;
    CommentAdapter commentAdapter;
    PlaylistAdapter playlistAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);


        UserImage = findViewById(R.id.User_Page_ImageView_UserImage);
        UserDescription = findViewById(R.id.User_Page_TextView_UserDescription);
        UserName = findViewById(R.id.User_Page_TextView_UserName);
        listView = findViewById(R.id.User_Page_ListView_List);
        btnVideos = findViewById(R.id.User_Page_Button_Videos);
        btnPlaylists = findViewById(R.id.User_Page_Button_Playlists);
        btnComments = findViewById(R.id.User_Page_Button_Comments);
        btnBack = findViewById(R.id.User_Page_Button_Back);
        btnUserOptions = findViewById(R.id.User_Page_Button_UserOptions);

        btnBack.setText("Back");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        user = Database.getUser(intent.getStringExtra("user"));

        videos = Database.getVideosArray((ArrayList<String>) user.getUploads().getVideos().values());

        playlists = new ArrayList<>();
        ArrayList<String> arrPlaylistsStr = user.getOwnedPlaylists();
        for (String str : arrPlaylistsStr) {
            playlists.add(Database.getPlaylist(str));
        }

        comments=new ArrayList<>();
        ArrayList<ArrayList<String>> ArrArr = MapToArrayList(user.getComments());
        for (ArrayList<String> arr : ArrArr) {
            comments.addAll(arr);
        }

        UserName.setText(user.getName());
        UserDescription.setText(user.getDescription());

        ShowVideos();


        if(GlobalVariables.loggedUser.isEmpty() || !GlobalVariables.loggedUser.get().getName().equals(user.getName())) {
            btnUserOptions.setVisibility(INVISIBLE);
        } else {
            btnUserOptions.setText("User Options");
            btnUserOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (GlobalVariables.loggedUser.isPresent() && GlobalVariables.loggedUser.get().getName().equals(user.getName())) {
                        openUserOwnerOptionsDialog(UserPageActivity.this, user);
                    } else {
                        openUserViewerOptionsDialog(UserPageActivity.this, user);
                    }
                }
            });
        }

        UserImage.setImageBitmap(ByteListToBitmap(user.getImage()));
    }


    public void ShowVideos() {
        Log.i("UserPageActivity: ShowVideos", "Showing " + videos.size() + " videos for user: " + user.getName());
        VideoAdapter videoAdapter = new VideoAdapter(this, R.layout.activity_video_listview_component, videos);
        listView.setAdapter(videoAdapter);
    }

    public void ShowPlaylists() {
        Log.i("UserPageActivity: ShowPlaylists", "Showing " + playlists.size() + " playlists for user: " + user.getName());
        PlaylistAdapter playlistAdapter = new PlaylistAdapter(this, R.layout.activity_playlist_list_view_component, playlists);
        listView.setAdapter(playlistAdapter);
    }

    public void ShowComments() {
        Log.i("UserPageActivity: ShowComments", "Showing " + comments.size() + " comments for user: " + user.getName());
        CommentAdapter commentAdapter = new CommentAdapter(this, R.layout.activity_comment_listview_component, comments);
        listView.setAdapter(commentAdapter);
    }

//    public void openCommentOptionsDialog(View view) {
//        Utilities.openCommentOptionsDialog(this, Comment.Default()); //TODO, replace the default impl, change it to find the local comment
//    }

    public void openVideo(View view) {
        TextView tvVideoTitle = view.findViewById(R.id.Video_ListView_Component_TextView_VideoTitle);
        String videoTitle = tvVideoTitle.getText().toString();
        openVideoPage(this, videoTitle);
    }

    public void ListViewVideos(View view) {
        ShowVideos();
    }

    public void ListViewPlaylists(View view) {
        ShowPlaylists();
    }

    public void ListViewComments(View view) {
        ShowComments();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = null;
                if (requestCode == 1 && data != null && data.getData() != null) {
                    // Gallery image
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                } else if (requestCode == 2 && data != null && data.getExtras() != null) {
                    // Camera image
                    bitmap = (Bitmap) data.getExtras().get("data");
                }

                if (bitmap != null) {
                    // Process and compress the image
                    bitmap = Utilities.processAndCompressImage(bitmap, 800, 800);
                    
                    // Show preview dialog
                    Dialog previewDialog = new Dialog(this);
                    previewDialog.setContentView(R.layout.activity_image_preview_dialog);
                    
                    ImageView previewImage = previewDialog.findViewById(R.id.ImagePreview_Dialog_ImageView);
                    Button btnConfirm = previewDialog.findViewById(R.id.ImagePreview_Dialog_Button_Confirm);
                    Button btnCancel = previewDialog.findViewById(R.id.ImagePreview_Dialog_Button_Cancel);
                    
                    previewImage.setImageBitmap(bitmap);

                    Bitmap finalBitmap = bitmap;
                    btnConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Convert bitmap to byte array and update user
                            ArrayList<Byte> imageBytes = Utilities.BitmapToByteArray(finalBitmap);
                            user.setImage(imageBytes);
                            Database.updateUser(user);
                            // Update the image view
                            UserImage.setImageBitmap(finalBitmap);
                            Feedback(UserPageActivity.this, "Profile picture updated successfully");
                            previewDialog.dismiss();
                        }
                    });
                    
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            previewDialog.dismiss();
                        }
                    });
                    
                    previewDialog.show();
                }
            } catch (Exception e) {
                Log.e("UserPageActivity", "Error loading image", e);
                Feedback(this, "Error loading image: " + e.getMessage());
            }
        }
    }
}