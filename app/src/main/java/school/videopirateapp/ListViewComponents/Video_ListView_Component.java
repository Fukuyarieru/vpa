package school.videopirateapp.ListViewComponents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import school.videopirateapp.Activities.VideoPageActivity;
import school.videopirateapp.R;

public class Video_ListView_Component extends AppCompatActivity {

    TextView uploader;
    TextView videoTitle;
    TextView upvotes;
    TextView downvotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_listview_component);


    }
    public void openVideo(View view) {
        Intent intent=new Intent(this, VideoPageActivity.class);
        intent.putExtra("videoTitle",videoTitle.getText());
        startActivity(intent);
    }
}