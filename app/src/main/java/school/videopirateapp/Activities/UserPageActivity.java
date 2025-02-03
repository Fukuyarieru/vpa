package school.videopirateapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import school.videopirateapp.DataStructures.User;
import school.videopirateapp.Database.Database;
import school.videopirateapp.ListViewComponents.CommentAdapter;
import school.videopirateapp.ListViewComponents.PlaylistAdapter;
import school.videopirateapp.ListViewComponents.VideoAdapter;
import school.videopirateapp.R;

public class UserPageActivity extends AppCompatActivity {


    User user;

    ImageView UserImage;
    TextView UserDescription;
    TextView UserName;
    ListView listView;
    Button btnVideos;
    Button btnPlaylists;
    Button btnComments;

    VideoAdapter videoAdapter;
    CommentAdapter commentAdapter;
    PlaylistAdapter playlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);


        UserImage=findViewById(R.id.User_Page_ImageView_UserImage);
        UserDescription=findViewById(R.id.User_Page_TextView_UserDescription);
        UserName=findViewById(R.id.User_Page_TextView_UserName);
        listView=findViewById(R.id.User_Page_ListView_List);
        btnVideos=findViewById(R.id.User_Page_Button_Videos);
        btnPlaylists=findViewById(R.id.User_Page_Button_Playlists);
        btnComments=findViewById(R.id.User_Page_Button_Comments);

        Intent intent=getIntent();
        user=Database.getUser(intent.getStringExtra("user"));

        PageInit();

        btnVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void PageInit() {
        // assuming "user" cannot be null
        UserName.setText(user.getName());
        UserDescription.setText("NEEDS TO BE IMPLEMENETED, IN DATABASE");
    }
    public void Close(View view) {
        finish();
    }
}