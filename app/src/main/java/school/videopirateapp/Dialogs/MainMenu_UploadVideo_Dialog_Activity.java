package school.videopirateapp.Dialogs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import school.videopirateapp.R;

public class MainMenu_UploadVideo_Dialog_Activity extends AppCompatActivity {

    ImageView thumbnail;
    Button btnChooseVideo;
    Button btnUploadVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_upload_video_dialog);

        btnChooseVideo=findViewById(R.id.MainMenu_UploadVideo_Dialog_Button_ChooseVideo);
        btnUploadVideo=findViewById(R.id.MainMenu_UploadVideo_Dialog_Button_UploadVideo);
        thumbnail=findViewById(R.id.MainMenu_UploadVideo_Dialog_ImageView_Thumbnail);


        btnUploadVideo.setText("Upload");
        btnChooseVideo.setText("Choose");

        // TODO, finish this
    }
    public void ChooseVideo(View view) {
        // TODO
    }
    public void ConfirmUploadVideo(View view) {
        // TODO
    }
}