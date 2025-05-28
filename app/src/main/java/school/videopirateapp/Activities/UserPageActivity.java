package school.videopirateapp.Activities;

import static school.videopirateapp.Utilities.Feedback;
import static school.videopirateapp.Utilities.MapToArrayList;
import static school.videopirateapp.Utilities.openCommentOptionsDialog;
import static school.videopirateapp.Utilities.openVideoPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.Playlist;
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;
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

    VideoAdapter videoAdapter;
    CommentAdapter commentAdapter;
    PlaylistAdapter playlistAdapter;

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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        user = Database.getUser(intent.getStringExtra("user"));
        Feedback(this, user.toString());

        videos = Database.getVideosArray(user.getUploads().getVideos());
//        for (Video video : videos) {
//            Log.i("TEST", video.toString());
//        }

        playlists = new ArrayList<>();
        ArrayList<String> arrPlaylistsStr = user.getOwnedPlaylists();
        for (String str : arrPlaylistsStr) {
            playlists.add(Database.getPlaylist(str));
        }

        comments = new ArrayList<Comment>();
        // todo, what a mess
        ArrayList<ArrayList<Comment>> ArrArr = MapToArrayList(user.getComments());
        for (ArrayList<Comment> arr : ArrArr) {
            for (Comment comment : arr) {
                comments.add(comment);
            }
        }
//        comments=user.getComments();

        PageInit();

        ShowVideos();
    }

    public void PageInit() {
        // assuming "user" cannot be null
        UserName.setText(user.getName());
        UserDescription.setText("NEEDS TO BE IMPLEMENETED, IN DATABASE");
    }

    public void ShowVideos() {
//        for (Video video : videos) {
//            Log.i("TEST", video.toString());
//        }
        Toast.makeText(this, "There are " + videos.size() + " videos", Toast.LENGTH_SHORT).show();

        VideoAdapter videoAdapter = new VideoAdapter(this, R.layout.activity_video_listview_component, videos);
        listView.setAdapter(videoAdapter);
    }

    public void ShowPlaylists() {
        Toast.makeText(this, "There are " + playlists.size() + " playlists", Toast.LENGTH_SHORT).show();
        PlaylistAdapter playlistAdapter = new PlaylistAdapter(this, R.layout.activity_playlist_list_view_component, playlists);
        listView.setAdapter(playlistAdapter);
    }

    public void ShowComments() {
        Toast.makeText(this, "There are " + comments.size() + " comments", Toast.LENGTH_SHORT).show();
        CommentAdapter commentAdapter = new CommentAdapter(this, R.layout.activity_comment_listview_component, comments);
        listView.setAdapter(commentAdapter);
    }

    public void openPlaylistActivity(View view) {
        Intent intent = new Intent(this, PlaylistPageActivity.class);
        startActivity(intent);
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

    public void openUserPage(View view) {
        // this function here is responsible for the onClick on comments in the userPage listview, no action needed
//        Intent intent=new Intent(this, UserPageActivity.class);
//        TextView tvUserName=view.findViewById(R.id.Comment_ListView_Component_TextView_UserName);
//        String userName=tvUserName.getText().toString();
//        intent.putExtra("user",userName);
//        startActivity(intent);
    }


    public void editComment(View view) {
    }

    public void openCommentPage(View view) {
    }

    public void deleteComment(View view) {
    }
}