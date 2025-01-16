package school.videopirateapp.ListViewComponents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.core.View;

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

    }
}