package school.videopirateapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;
import school.videopirateapp.R;

public class VideoPageActivity extends AppCompatActivity {

    // activity_video_page.xml

    Video video;

    TextView tvUploader;
    TextView tvVideoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_page);

        tvUploader=findViewById(R.id.Video_Page_TextView_UserName);
        tvVideoTitle=findViewById(R.id.Video_Page_TextView_VideoTitle);

        Intent intent=getIntent();
        String videoTitle=intent.getStringExtra("videoTitle");

        tvVideoTitle.setText(videoTitle);
        video= Database.getVideo(videoTitle);
        String Uploader=video.getUploader();
        tvUploader.setText(Uploader);



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
//        // Start the video
//        videoView.setOnPreparedListener(mp -> videoView.start());
        // TODO
    }
    public void Close(View view) {
        finish();
    }
    public void openUserPage(View view) {
        Intent intent=new Intent(this, UserPageActivity.class);

        startActivity(intent);
    }
}