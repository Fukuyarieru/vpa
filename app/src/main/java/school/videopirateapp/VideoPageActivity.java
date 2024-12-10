package school.videopirateapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class VideoPageActivity extends AppCompatActivity {

    // activity_video_page.xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video_page);


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
}