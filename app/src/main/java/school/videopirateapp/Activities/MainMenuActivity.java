package school.videopirateapp.Activities;

import static school.videopirateapp.Utilities.HashMapToArrayList;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;
import school.videopirateapp.Database.Videos;
import school.videopirateapp.ListViewComponents.VideoAdapter;
import school.videopirateapp.R;

public class MainMenuActivity extends AppCompatActivity {


    Button btnUserPage;
    Button btnUploadVideo;
    Button btnSearchVideo;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);

        Videos.Refresh();

//        finishActivity();

//        startActivityfrom();
        ArrayList<Video>videos= HashMapToArrayList(Database.getVideos());
//        ArrayList<Video>videos=new ArrayList<>();
//        videos.add(new Video());
//        videos.add(new Video("German Unity Day, eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee","Menachem2007"));
        VideoAdapter videosAdaptar=new VideoAdapter(this,R.layout.activity_video_listview_component,videos);

        btnUploadVideo =findViewById(R.id.MainMenu_Button_UploadVideo);
        btnUserPage =findViewById(R.id.MainMenu_Button_UserPage);
        listView=findViewById(R.id.MainMenu_ListView);
        btnSearchVideo=findViewById(R.id.MainMenu_Button_SearchVideo);


        listView.setAdapter(videosAdaptar);

        btnUserPage.setText("Login");
        btnUploadVideo.setText("Upload Video");
        btnSearchVideo.setText("Search");


        // TOAST: YOU MUST LOGIN FIRST
    }
    public void SearchVideo(View view) {
        // dont do a dialog, change to a deticated screen that has an edittext and updates live
        Dialog seachDialog=new Dialog(MainMenuActivity.this);
        seachDialog.setContentView(R.layout.activity_main_menu_search_video_dialog);
        seachDialog.show();
    }
    public void UploadVideo(View view) {
        Dialog uploadDialog=new Dialog(MainMenuActivity.this);
        uploadDialog.setContentView(R.layout.activity_main_menu_upload_video_dialog);
        uploadDialog.show();
    }
    public void UserPage(View view) {
        if(!btnUserPage.getText().toString().equals("Login")) {
            Intent openUserPage = new Intent(MainMenuActivity.this, UserPageActivity.class);
            openUserPage.putExtra("user", btnUserPage.getText().toString()); // sends the current signed in user's name to the new screen (userPage), that screen would search it from database from that point
            startActivity(openUserPage);
        }
        else {
            Dialog loginDialog=new Dialog(MainMenuActivity.this); //this screen as context
            loginDialog.setContentView(R.layout.activity_login_dialog);
            loginDialog.show();
        }
    }
}