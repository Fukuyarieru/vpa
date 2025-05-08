package school.videopirateapp.Activities;

import static school.videopirateapp.Utilities.TimeNow;
//import static school.videopirateapp.Utilities.openVideoPlayer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;
import school.videopirateapp.GlobalVariables;
import school.videopirateapp.ListViewComponents.CommentAdapter;
import school.videopirateapp.R;
import school.videopirateapp.Utilities;

public class VideoPageActivity extends AppCompatActivity {

    // activity_video_page.xml

    Video currentVideo;

    TextView tvUploader;
    TextView tvVideoTitle;
    EditText etComment;
    Button btnMakeComment;
    Button btnBack;
    ListView lvComments;
    ArrayList<Comment>comments;
    CommentAdapter commentAdapter;

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_page);

        tvVideoTitle=findViewById(R.id.Video_Page_TextView_VideoTitle);
        tvUploader=findViewById(R.id.Video_Page_TextView_Uploader);
        etComment=findViewById(R.id.Video_Page_EditText_Comment);
        btnMakeComment=findViewById(R.id.Video_Page_Button_AddComment);
        lvComments=findViewById(R.id.Video_Page_ListView_Comments);
        btnBack=findViewById(R.id.Video_Page_Button_Back);

        videoView=findViewById(R.id.Video_Page_VideoView_Video);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Intent intent=getIntent();
        String videoTitle=intent.getStringExtra("videoTitle");

        tvVideoTitle.setText(videoTitle);
        currentVideo = Database.getVideo(videoTitle);
        String Uploader= currentVideo.getUploader();
        tvUploader.setText(Uploader);

        comments= currentVideo.getComments();

        commentAdapter=new CommentAdapter(this,R.layout.activity_comment_listview_component,comments);
        lvComments.setAdapter(commentAdapter);

        MediaController mediaController=new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        if(currentVideo.getUrl().isEmpty()) {
            Log.e("VideoPageActivit: currentVideo.getUrl()","Video URL is empty");
            currentVideo.setUrl("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4");
        }

        videoView.setVideoPath(currentVideo.getUrl());



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
    }
    public void Close(View view) {
        finish();
    }
    public void openUserPage(View view) {
        Utilities.openUserPage(this, currentVideo.getUploader());
    }
    public void makeComment(View view) {
        if(etComment.getText().toString().isEmpty()){
            Utilities.Feedback(this,"Comments cannot be empty");
        } else if(GlobalVariables.loggedUser.isEmpty()) {
            Utilities.Feedback(this,"You must be logged in to add comments");
        }
        else {
            String commentStr=etComment.getText().toString();
            Comment newComment=new Comment(commentStr,GlobalVariables.loggedUser.get().getName(), currentVideo.Context(),TimeNow()); //@MISSING-AUTHOR-makeComment, REMAKE THIS TO FIDN AUTHOR SOMEHOW TODO
            Database.addComment(newComment, currentVideo); // logic does not work correct, TODO FIX
            Utilities.Feedback(this,"Comment added");
            commentAdapter.add(newComment);
        }
    }
//    public void playVideo(View view) {
//        openVideoPlayer(this, currentVideo.getTitle());
//    }
}