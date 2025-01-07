package school.videopirateapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {


    Button btnUserPage;
    Button btnUploadPage;
    Button btnSearchVideo;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);

        btnUploadPage =findViewById(R.id.MainMenu_Button_UploadPage);
        btnUserPage =findViewById(R.id.MainMenu_Button_UserPage);
        listView=findViewById(R.id.MainMenu_ListView);
        btnSearchVideo=findViewById(R.id.MainMenu_Button_SearchVideo);


        // TOAST: YOU MUST LOGIN FIRST
    }
    public void SearchVideo(View view) {
        // dont do a dialog, change to a deticated screen that has an edittext and updates live
//        Dialog searvhDialog=new Dialog(MainMenuActivity.this);
//        searvhDialog.setContentView(R.layout.activity_main_menu_search_video_dialog);
//        searvhDialog.show();
    }
    public void UploadPage(View view) {

    }
    public void UserPage(View view) {

    }
}