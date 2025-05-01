package school.videopirateapp.Activities;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import school.videopirateapp.R;

public class VideoPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video_player);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        VideoView videoView = findViewById(R.id.Video_Player_VideoView_Video);

        // videoView


        // Set the media controller buttons
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Specify the URI of the video to play (using a sample video URL)
        // Replace with your actual video URL
        Uri videoUri = Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");



        videoView.setVideoURI(videoUri);

        // Start the video
        videoView.setOnPreparedListener(mp -> videoView.start());
    }
}