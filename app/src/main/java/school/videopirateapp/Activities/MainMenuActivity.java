package school.videopirateapp.Activities;

import static school.videopirateapp.Utilities.HashMapToArrayList;
import static school.videopirateapp.Utilities.openLoginDialog;
import static school.videopirateapp.Utilities.openVideoPage;
import static school.videopirateapp.Utilities.openVideoUploadDialog;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;
import school.videopirateapp.Database.Videos;
import school.videopirateapp.GlobalVariables;
import school.videopirateapp.ListViewComponents.VideoAdapter;
import school.videopirateapp.R;
import school.videopirateapp.Utilities;

public class MainMenuActivity extends AppCompatActivity {


    Button btnUserPage;
    Button btnUploadVideo;
    Button btnRefreshVideos;
    EditText etUsername;
    EditText etPassword;
    ListView listView;
    ArrayList<Video> videos;
    VideoAdapter videosAdapter;
    String chosenTitle;
    Boolean loggedIn;


    // TODO, DOES NOT WORK, TODO, FIX
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        btnUploadVideo =findViewById(R.id.MainMenu_Button_UploadVideo);
        btnUserPage =findViewById(R.id.MainMenu_Button_UserPage);
        listView=findViewById(R.id.MainMenu_ListView);
        btnRefreshVideos =findViewById(R.id.MainMenu_Button_RefreshVideos);

        // A bug happens here, Videos.Refresh relies on getting a reference from the database, ~which is an async operation~ TAKES A WHILE, so it gets delayed and completed ONLY AFTER the adapter is set, therefore, empty listview
        // The fix to that for now is just adding a manual refresh button
        // DO NOT USE INFINITE WHILE LOOPS
        videoListViewInit();

        updateUserPageButton();

//        btnUserPage.setText("Login");
        btnUploadVideo.setText("Upload Video");
        btnRefreshVideos.setText("Refresh Videos");

        // TOAST: YOU MUST LOGIN FIRST

    }
    public void videoListViewInit() {
        Videos.Refresh(); // more strange behavior here, i think
        videos=HashMapToArrayList(Database.getVideos());
        videosAdapter =new VideoAdapter(this,R.layout.activity_video_listview_component,videos);
        listView.setAdapter(videosAdapter);
        Log.i("MainMenuActivity","Updating videos listview");
    }
    // todo, later, add the button to the menu to manually refresh the videos list
//    public void RefreshVideosButton(View view) {
//        VideosListViewInit();
//    }
    public void refreshVideosButton(View view) {
        refreshVideos();
    }
    public void refreshVideos() {
        videoListViewInit(); // TODO, TEMPORARY SOLUTION FOR THE MENU WHICH DOES NOT WORK
        Utilities.Feedback(this,"Videos refreshed");
    }

    public void uploadVideo(View view) {
        openVideoUploadDialog(this);
    }
    public void userPage(View view) {
        if (btnUserPage.getText().toString().equals("Login")) {
            openLoginDialog(this);
            updateUserPageButton();
        } else {
            Intent openUserPage = new Intent(MainMenuActivity.this, UserPageActivity.class);
            openUserPage.putExtra("user", btnUserPage.getText().toString()); // sends the current signed in user's name to the new screen (userPage), that screen would search it from database from that point
            startActivity(openUserPage);
        }
    }
    public void openVideo(View view) {
        TextView tvVideoTitle=view.findViewById(R.id.Video_ListView_Component_TextView_VideoTitle);
        openVideoPage(this,tvVideoTitle.getText().toString());
    }

    public void openSignupActivity(View view){
        // TODO
        Intent openSignupActivity=new Intent(this, SignupActivity.class);
        openSignupActivity.putExtra("username", etUsername.getText().toString());
        openSignupActivity.putExtra("password", etPassword.getText().toString());
        startActivity(openSignupActivity);
    }
    public void ConfirmUploadVideo(View view) {
        // TODO
    }
    public void ChooseVideo(View view){
        // TODO
    }
    public void updateUserPageButton() {
        if(btnUserPage != null) {
        if (GlobalVariables.loggedUser.isPresent()) {
            btnUserPage.setText(GlobalVariables.loggedUser.get().getName());
        } else {
            btnUserPage.setText("Login");
        } }
    }
}