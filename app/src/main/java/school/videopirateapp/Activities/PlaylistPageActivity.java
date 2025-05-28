package school.videopirateapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import school.videopirateapp.R;

public class PlaylistPageActivity extends AppCompatActivity {


    Button btnPlaylistOptions;
    Button btnUserPage;
    Button btnBack;
    Button btnUpvote;
    Button btnDownvote;
    TextView tvPlaylistTitle;
    TextView tvPlaylistDescription;
    TextView tvPlaylistSCore;
    ImageView playlistImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_page);

        btnPlaylistOptions=findViewById(R.id.Playlist_Page_Button_Options);
        btnUserPage=findViewById(R.id.Playlist_Page_Button_UserPage);
        btnBack=findViewById(R.id.Playlist_Page_Button_Back);
        btnUpvote=findViewById(R.id.Playlist_Page_Button_Upvote);
        btnDownvote=findViewById(R.id.Playlist_Page_Button_Downvote);
        tvPlaylistTitle=findViewById(R.id.Playlist_Page_TextView_Title);
        tvPlaylistDescription =findViewById(R.id.Playlist_Page_TextView_Description);
        tvPlaylistSCore=findViewById(R.id.Playlist_Page_TextView_Score);
        playlistImage=findViewById(R.id.Playlist_Page_ImageView_Image);



    }
}