package school.videopirateapp.Activities;

import static school.videopirateapp.Utilities.HashMapToArrayList;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;
import school.videopirateapp.Database.Videos;
import school.videopirateapp.Dialogs.Login_Dialog_Activity;
import school.videopirateapp.ListViewComponents.VideoAdapter;
import school.videopirateapp.R;
import school.videopirateapp.Dialogs.Login_Dialog_Activity.*;

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
//        ArrayList<Video>videos= HashMapToArrayList(Database.getVideos());

        ArrayList<Video>videos=new ArrayList<>();
        videos.add(Video.Default());
        videos.add(new Video("Letplay","Frogger"));
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
        if (!btnUserPage.getText().toString().equals("Login")) {
            Intent openUserPage = new Intent(MainMenuActivity.this, UserPageActivity.class);
            openUserPage.putExtra("user", btnUserPage.getText().toString()); // sends the current signed in user's name to the new screen (userPage), that screen would search it from database from that point
            startActivity(openUserPage);
        } else {
            Dialog loginDialog = new Dialog(MainMenuActivity.this); //this screen as context
            loginDialog.setContentView(R.layout.activity_login_dialog);
            loginDialog.show();
        }
    }
    public void openVideo(View view) {
        Intent intent=new Intent(this, VideoPageActivity.class);
//        intent.putExtra("videoTitle",videoTitle.getText());
        startActivity(intent);
    }
    public void confirmLogin(View view){
//        EditText etPassword=view.findViewById(R.id.Login_Dialog_EditText_Password);
//        EditText etUsername=view.findViewById(R.id.Login_Dialog_EditText_Username);
//        User user=Database.getUser(etUsername.getText().toString());
//        if(user.getPassword()==etPassword.getText().toString()){
            Intent intent=new Intent(this, UserPageActivity.class);
            startActivity(intent);
//        }
//        else {
//            Toast.makeText(this,"Wrong password",Toast.LENGTH_SHORT);
//        }
    }
    public void openSignupActivity(View view){
        // TODO
        Intent openSignupActivity=new Intent(this, SignupActivity.class);
//        openSignupActivity.putExtra("username", etUsername.getText().toString());
//        openSignupActivity.putExtra("password", etPassword.getText().toString());
        startActivity(openSignupActivity);
    }
}