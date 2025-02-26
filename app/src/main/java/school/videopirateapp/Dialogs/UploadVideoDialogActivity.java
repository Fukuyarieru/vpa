package school.videopirateapp.Dialogs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import school.videopirateapp.R;

public class UploadVideoDialogActivity extends AppCompatActivity {

    ImageView thumbnail;
    Button btnChooseVideo;
    Button btnUploadVideo;
    EditText etVideoTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video_dialog);

        // this is transfered into the function inside the MainMenu Activity
//        btnChooseVideo=findViewById(R.id.UploadVideo_Dialog_Button_ChooseVideo);
//        btnUploadVideo=findViewById(R.id.UploadVideo_Dialog_Button_UploadVideo);
//        thumbnail=findViewById(R.id.UploadVideo_Dialog_ImageView_Thumbnail);
//        etVideoTitle=findViewById(R.id.UploadVideo_Dialog_EditText_VideoTitle);
//
//        btnUploadVideo.setText("Upload");
//        btnChooseVideo.setText("Choose");
//        Toast.makeText(this, "THIS IS WORKING", Toast.LENGTH_SHORT).show();

        // TODO, finish this
    }
    public void ChooseVideo(View view) {
        // TODO
    }
    public void ConfirmUploadVideo(View view) {
        // TODO
    }
}