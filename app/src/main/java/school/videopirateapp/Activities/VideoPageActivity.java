package school.videopirateapp.Activities;

import static school.videopirateapp.Utilities.ByteListToBitmap;
import static school.videopirateapp.Utilities.Feedback;
import static school.videopirateapp.Utilities.openUserPage;
import static school.videopirateapp.Utilities.openVideoOwnerOptionsDialog;
import static school.videopirateapp.Utilities.openVideoViewerOptionsDialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import school.videopirateapp.datastructures.Comment;
import school.videopirateapp.datastructures.User;
import school.videopirateapp.datastructures.Video;
import school.videopirateapp.database.Comments;
import school.videopirateapp.database.Database;
import school.videopirateapp.GlobalVariables;
import school.videopirateapp.ListViewComponents.CommentAdapter;
import school.videopirateapp.R;
import school.videopirateapp.Utilities;

public class VideoPageActivity extends AppCompatActivity {

    // activity_video_page.xml

    Video currentVideo;

    TextView tvUploader;
    TextView tvVideoTitle;
    TextView tvScore;
    EditText etComment;
    Button btnAddComment;
    Button btnUpvote;
    Button btnDownvote;
    Button btnBack;
    Button btnVideoOptions;
    Button btnUserPage;
    ListView lvComments;
    CommentAdapter commentAdapter;
    VideoView videoView;
    ImageView uploaderImage;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_page);

        tvVideoTitle = findViewById(R.id.Video_Page_TextView_VideoTitle);
        tvUploader = findViewById(R.id.Video_Page_TextView_Uploader);
        etComment = findViewById(R.id.Video_Page_EditText_Comment);
        btnAddComment = findViewById(R.id.Video_Page_Button_AddComment);
        lvComments = findViewById(R.id.Video_Page_ListView_Comments);
        btnBack = findViewById(R.id.Video_Page_Button_Back);
        btnUpvote = findViewById(R.id.Video_Page_Button_Upvote);
        btnDownvote = findViewById(R.id.Video_Page_Button_Downvote);
        btnVideoOptions = findViewById(R.id.Video_Page_Button_VideoOptions);
        btnUserPage = findViewById(R.id.Video_Page_Button_UserPage);
        tvScore = findViewById(R.id.Video_Page_TextView_Score);
        uploaderImage = findViewById(R.id.Video_Page_ImageView_UploaderImage);
        videoView = findViewById(R.id.Video_Page_VideoView_Video);

        btnBack.setText("Back");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        String videoTitle = intent.getStringExtra("videoTitle");

        tvVideoTitle.setText(videoTitle);

        // Show loading indicator or disable UI elements here

        // Refresh database and load data only after refresh is complete
        Database.refresh();
        currentVideo = Database.getVideo(videoTitle);
        if (currentVideo != null) {
            Log.e("CURRENT VIDEO", currentVideo.getComments().toString());
            String UploaderName = currentVideo.getUploader();
            tvUploader.setText(UploaderName);

            // Get fresh comments from database
            ArrayList<Comment> comments = Comments.getComments(currentVideo.getComments());
            commentAdapter = new CommentAdapter(this, R.layout.activity_comment_listview_component, comments);
            lvComments.setAdapter(commentAdapter);

            // Enable UI elements or hide loading indicator here
        } else {
            Utilities.Feedback(this, "Failed to load video");
            finish();
        }

        String UploaderName=currentVideo.getUploader();

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        if (currentVideo.getUrl().isEmpty()) {
            Log.e("VideoPageActivit: currentVideo.getUrl()", "Video URL is empty");
            currentVideo.setUrl("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4");
        }

        btnUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("VideoPageActivity: btnUpvote", "btnUpvote clicked");
                if (GlobalVariables.loggedUser.isEmpty()) {
                    Feedback(VideoPageActivity.this, "You must be logged in to upvote");
                } else {
                    if (GlobalVariables.loggedUser.get().getUpvotes().contains(currentVideo.getTitle())) {
                        Feedback(VideoPageActivity.this, "You have this video already upvoted");
                    } else {
                        Database.upvoteVideo(currentVideo, GlobalVariables.loggedUser.get());
                        tvScore.setText(String.valueOf(currentVideo.getVoteScore()));
                        Feedback(VideoPageActivity.this, "Video upvoted");
                    }
                }
            }
        });

        btnDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("VideoPageActivity: btnDownvote", "btnDownvote clicked");
                if (GlobalVariables.loggedUser.isEmpty()) {
                    Feedback(VideoPageActivity.this, "You must be logged in to downvote");
                } else {
                    if (GlobalVariables.loggedUser.get().getDownvotes().contains(currentVideo.getTitle())) {
                        Feedback(VideoPageActivity.this, "You have this video already downvoted");
                    } else {
                        Database.downvoteVideo(currentVideo, GlobalVariables.loggedUser.get());
                        tvScore.setText(String.valueOf(currentVideo.getVoteScore()));
                        Feedback(VideoPageActivity.this, "Video downvoted");
                    }
                }
            }
        });

        videoView.setVideoPath(currentVideo.getUrl());

        btnVideoOptions.setText("Video Options");
        btnVideoOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.loggedUser.isPresent() && GlobalVariables.loggedUser.get().getName().equals(currentVideo.getUploader())) {
                    openVideoOwnerOptionsDialog(VideoPageActivity.this, currentVideo);
                } else {
                    openVideoViewerOptionsDialog(VideoPageActivity.this, currentVideo);
                }
            }
        });

        btnAddComment.setText("Add Comment");
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etComment.getText().toString().isEmpty()) {
                    Utilities.Feedback(VideoPageActivity.this, "Comments cannot be empty");
                } else if (GlobalVariables.loggedUser.isEmpty()) {
                    Utilities.Feedback(VideoPageActivity.this, "You must be logged in to add comments");
                } else {
                    String commentStr = etComment.getText().toString().trim();
                    Comment newComment = new Comment(commentStr, GlobalVariables.loggedUser.get().getName(), currentVideo.getCommentsContext());
                    Database.addComment(newComment);
                    commentAdapter.add(newComment);
//                    tvScore.setText(String.valueOf(currentVideo.getVoteScore()));
                    Utilities.Feedback(VideoPageActivity.this, "Comment added");
                    etComment.setText("");
                }
            }
        });

        Utilities.updateUserPageButton(this, btnUserPage);

        User uploader = Database.getUser(UploaderName);
        try {
            uploaderImage.setImageBitmap(ByteListToBitmap(uploader.getImage()));
        } catch (Exception e) {
            uploaderImage.setImageResource(R.drawable.default_user_image);
        }
        uploaderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Feedback(VideoPageActivity.this, "Failed to load uploader's image");
                openUserPage(VideoPageActivity.this, UploaderName);
            }
        });
    }

    public void refreshComments() {
        Database.refresh();
        currentVideo = Database.getVideo(currentVideo.getTitle());
        ArrayList<Comment> comments = Comments.getComments(currentVideo.getComments());
        commentAdapter.clear();
        commentAdapter.addAll(comments);
        commentAdapter.notifyDataSetChanged();
        Utilities.Feedback(this, "Comments refreshed");
    }
}

//        VideoView videoView = findViewById(R.id.Video_Page_VideoView_Video);
//
//        // Set the media controller buttons
//        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(videoView);
//        videoView.setMediaController(mediaController);
//
//        // Specify the URI of the video to play
//        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.default_video); // example for a video in raw resources
//
//        // or a video from the web
//        // Uri videoUri = Uri.parse("http://path/to/your/video");
//
//        videoView.setVideoURI(videoUri);
//

//    public void openUserPage(View view) {
//        Utilities.openUserPage(VideoPageActivity.this, currentVideo.getUploader());
//    }
//    public void makeComment(View view) {
//        if(etComment.getText().toString().isEmpty()){
//            Utilities.Feedback(this,"Comments cannot be empty");
//        } else if(GlobalVariables.loggedUser.isEmpty()) {
//            Utilities.Feedback(this,"You must be logged in to add comments");
//        }
//        else {
//            String commentStr=etComment.getText().toString();
//            Comment newComment=new Comment(commentStr,GlobalVariables.loggedUser.get().getName(), currentVideo.Context(),TimeNow()); //@MISSING-AUTHOR-makeComment, REMAKE THIS TO FIDN AUTHOR SOMEHOW TODO
//            Database.addComment(newComment, currentVideo); // logic does not work correct, TODO FIX
//            Utilities.Feedback(this,"Comment added");
//        }

//    public void playVideo(View view) {
//        openVideoPlayer(this, currentVideo.getTitle());
//    }