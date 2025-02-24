package school.videopirateapp.Activities;

import static school.videopirateapp.Utilities.HashMapToArrayList;
import static school.videopirateapp.Utilities.openVideoPage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;
import school.videopirateapp.Database.Videos;
import school.videopirateapp.GlobalVariables;
import school.videopirateapp.ListViewComponents.VideoAdapter;
import school.videopirateapp.MainActivity;
import school.videopirateapp.R;

public class MainMenuActivity extends AppCompatActivity {



    Button btnUserPage;
    Button btnUploadVideo;
    Button btnSearchVideo;
    Button btnLogin;
    Button btnSignup;
    EditText etUsername;
    EditText etPassword;
    ListView listView;
    ArrayList<Video> videos;
    VideoAdapter videosAdapter;
    String chosenTitle;
    String loggedUser;
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
        btnSearchVideo=findViewById(R.id.MainMenu_Button_SearchVideo);

        loggedUser=GlobalVariables.getUser();
        btnUserPage.setText(loggedUser); // "Not looged in";

        loggedIn=GlobalVariables.getIsLoggedIn();

        // A bug happens here, Videos.Refresh relies on getting a reference from the database, ~which is an async operation~ TAKES A WHILE, so it gets delayed and completed ONLY AFTER the adapter is set, therefore, empty listview
        // The fix to that for now is just adding a manual refresh button
        // DO NOT USE INFINITE WHILE LOOPS
        VideosListViewInit();

        btnUserPage.setText("Login");
        btnUploadVideo.setText("Upload Video");
        btnSearchVideo.setText("Search");

        // TOAST: YOU MUST LOGIN FIRST

    }
    public void VideosListViewInit() {
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
    public void SearchVideo(View view) {
        VideosListViewInit(); // TODO, TEMPORARY SOLUTION FOR THE MENU WHICH DOES NOT WORK
        // dont do a dialog, change to a deticated screen that has an edittext and updates live
        Dialog seachDialog=new Dialog(MainMenuActivity.this);
        seachDialog.setContentView(R.layout.activity_main_menu_search_video_dialog);
        seachDialog.show();
    }
    public void UploadVideo(View view) {
        VideosListViewInit();
        Dialog uploadDialog=new Dialog(MainMenuActivity.this);
        uploadDialog.setContentView(R.layout.activity_upload_video_dialog);
        uploadDialog.show();

        Button btnChooseVideo=uploadDialog.findViewById(R.id.UploadVideo_Dialog_Button_ChooseVideo);
        Button btnUploadVideo=uploadDialog.findViewById(R.id.UploadVideo_Dialog_Button_UploadVideo);
        ImageView thumbnail=uploadDialog.findViewById(R.id.UploadVideo_Dialog_ImageView_Thumbnail);
        EditText etVideoTitle=uploadDialog.findViewById(R.id.UploadVideo_Dialog_EditText_VideoTitle);

        btnUploadVideo.setText("Upload");
        btnChooseVideo.setText("Choose");

        btnUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO, check if etVideoTitle is empty?
                chosenTitle=etVideoTitle.getText().toString();
//                if chosenTitle=""  // add a function named Video.IsProperName(videoName) which returns true if good and false if bad;
                if(Database.getVideo(chosenTitle)!=null) {
                    Toast.makeText(MainMenuActivity.this, "Video with that title already exists", Toast.LENGTH_SHORT).show();
                } else {
                    if (loggedIn) {
                        Video newVideo=new Video(chosenTitle,loggedUser);
                        Toast.makeText(MainMenuActivity.this, "Added video named: "+newVideo.getTitle(), Toast.LENGTH_SHORT).show();
                        Database.addVideo(newVideo);
                    } else {
                        Toast.makeText(MainMenuActivity.this, "Not logged in", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnChooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//
//        btnChooseVideo=findViewById(R.id.UploadVideo_Dialog_Button_ChooseVideo);
//        btnUploadVideo=findViewById(R.id.UploadVideo_Dialog_Button_UploadVideo);
//        thumbnail=findViewById(R.id.UploadVideo_Dialog_ImageView_Thumbnail);
//        etVideoTitle=findViewById(R.id.UploadVideo_Dialog_EditText_VideoTitle);
//
//        btnUploadVideo.setText("Upload");
//        btnChooseVideo.setText("Choose");
//        Toast.makeText(this, "THIS IS WORKING", Toast.LENGTH_SHORT).show()
    }
    public void UserPage(View view) {
        if (btnUserPage.getText().toString().startsWith("@")) {
            Intent openUserPage = new Intent(MainMenuActivity.this, UserPageActivity.class);
            openUserPage.putExtra("user", btnUserPage.getText().toString()); // sends the current signed in user's name to the new screen (userPage), that screen would search it from database from that point
            startActivity(openUserPage);
        } else {
            Dialog loginDialog = new Dialog(MainMenuActivity.this); //this screen as context
            loginDialog.setContentView(R.layout.activity_login_dialog);
            etUsername=loginDialog.findViewById(R.id.Login_Dialog_EditText_Username);
            etPassword=loginDialog.findViewById(R.id.Login_Dialog_EditText_Password);
            btnLogin=loginDialog.findViewById(R.id.Login_Dialog_Button_Login);
            btnSignup=loginDialog.findViewById(R.id.Login_Dialog_Button_Signup);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();

                    if (username.isEmpty() || password.isEmpty()) {
                        // Display a message if the fields are empty
                        Toast.makeText(MainMenuActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    } else if(!username.startsWith("@")) {
                        Toast.makeText(MainMenuActivity.this,"Usernames must start with @",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        User desiredUser = Database.getUser(username);
//                        Toast.makeText(MainMenuActivity.this,"user: "+desiredUser.getName()+", pass: "+desiredUser.getPassword(),Toast.LENGTH_SHORT).show();
                        if(desiredUser==null) {
                            Toast.makeText(MainMenuActivity.this,"User was not found",Toast.LENGTH_SHORT).show();
                        }
                        else if (!desiredUser.getPassword().equals(password)) {
                            Toast.makeText(MainMenuActivity.this,"Password does not match",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            btnUserPage.setText(username);
                            loggedIn=true;
                            loggedUser=username;
                            GlobalVariables.loggedUser=Database.getUser(loggedUser);
                            Toast.makeText(MainMenuActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            loginDialog.dismiss();
                        }

                    }
                }
            });
            loginDialog.show();
        }
    }
    public void openVideo(View view) {
        TextView tvVideoTitle=view.findViewById(R.id.Video_ListView_Component_TextView_VideoTitle);

        openVideoPage(this,tvVideoTitle.getText().toString());
//        Intent intent=new Intent(this, VideoPageActivity.class);
////        intent.putExtra("videoTitle",videoTitle.getText());
//        startActivity(intent);
    }
    public void confirmLogin(View view) {
//        etUsername=findViewById(R.id.Login_Dialog_EditText_Username);
//        etPassword=findViewById(R.id.Login_Dialog_EditText_Password);
//        btnLogin=findViewById(R.id.Login_Dialog_Button_Login);
//        btnSignup=findViewById(R.id.Login_Dialog_Button_Signup);
//
////        // TODO, REMAKE THIS LATER
//        String username = etUsername.getText().toString();
//        String password = etPassword.getText().toString();
////
//        if (username.isEmpty() || password.isEmpty()) {
//            // Display a message if the fields are empty
//            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
//        } else if(!username.startsWith("@")) {
//            Toast.makeText(this,"Usernames must start with @",Toast.LENGTH_SHORT).show();
//        } else {
//            User desiredUser = Database.getUser(username);
//            if(desiredUser==null) {
////                Log.w("User action")
//                Toast.makeText(this,"User does not exist",Toast.LENGTH_SHORT).show();
//            } else if (desiredUser.getPassword().equals(password)) {
//                Button userPage = findViewById(R.id.MainMenu_Button_UserPage);
//                userPage.setText(username);
//                Toast.makeText(MainMenuActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
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
}