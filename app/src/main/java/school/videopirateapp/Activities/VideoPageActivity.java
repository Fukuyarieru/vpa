package school.videopirateapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;
import school.videopirateapp.ListViewComponents.CommentAdapter;
import school.videopirateapp.R;
import school.videopirateapp.Utilities;

public class VideoPageActivity extends AppCompatActivity {

    // activity_video_page.xml

    Video video;
    User loggedUser;

    TextView tvUploader;
    TextView tvVideoTitle;
    EditText etComment;
    Button btnMakeComment;
    ListView lvComments;
    ArrayList<Comment>comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_page);

        tvVideoTitle=findViewById(R.id.Video_Page_TextView_VideoTitle);
        tvUploader=findViewById(R.id.Video_Page_TextView_UserName);
        etComment=findViewById(R.id.Video_Page_EditText_Comment);
        btnMakeComment=findViewById(R.id.Video_Page_Button_AddComment);
        lvComments=findViewById(R.id.Video_Page_ListView_Comments);

        Intent intent=getIntent();
        String videoTitle=intent.getStringExtra("videoTitle");
        String loggedUserName=intent.getStringExtra("user");

        tvVideoTitle.setText(videoTitle);
        video= Database.getVideo(videoTitle);
        loggedUser=Database.getUser(loggedUserName);
        String Uploader=video.getUploader();
        tvUploader.setText(Uploader);

        comments=video.getComments();

        CommentAdapter adapter=new CommentAdapter(this,R.layout.activity_comment_listview_component,comments);
        lvComments.setAdapter(adapter);





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
        Intent intent=new Intent(this, UserPageActivity.class);
        intent.putExtra("user",tvUploader.getText().toString());
        startActivity(intent);
    }
    public void makeComment(View view) {
        if(etComment.getText().toString().isEmpty()){
            Utilities.Feedback(this,"Comments cannot be empty");
        } else {
            String commentStr=etComment.getText().toString();
            Comment comment=new Comment(commentStr,loggedUser.getName(),video.Context()); //@MISSING-AUTHOR-makeComment, REMAKE THIS TO FIDN AUTHOR SOMEHOW TODO
            Database.addComment(comment,video); // logic does not work correct, TODO FIX
            video.addComment(comment);
            Utilities.Feedback(this,"Comment added");
            // TODO, temporary lines below, SHOULD BE TEMPORARY
            CommentAdapter adapter=new CommentAdapter(this,R.layout.activity_comment_listview_component,comments);
            lvComments.setAdapter(adapter);
        }
    }
    public void CommentOptionsDialog(View view) {

    }

}