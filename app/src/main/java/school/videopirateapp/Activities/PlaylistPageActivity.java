package school.videopirateapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import school.videopirateapp.DataStructures.Playlist;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;
import school.videopirateapp.ListViewComponents.VideoAdapter;
import school.videopirateapp.R;
import school.videopirateapp.Utilities;

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
    ListView listView;

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
        listView=findViewById(R.id.Playlist_Page_ListView);

        Intent intent=getIntent();
        String playlistTitle=intent.getStringExtra("playlistTitle");
        Playlist playlist= Database.getPlaylist(playlistTitle);


        btnPlaylistOptions.setText("Playlist Options");
        btnBack.setText("Back");
        btnUpvote.setText("Upvote");
        btnDownvote.setText("Downvote");
        tvPlaylistTitle.setText(playlist.getTitle());
        tvPlaylistDescription.setText(playlist.getDescription());
        tvPlaylistSCore.setText(playlist.getScore().toString());
        playlistImage.setImageResource(R.drawable.default_playlist_image);

        ArrayList<Video>videos=new ArrayList<>();
        for (String str:playlist.getVideos()) {
            videos.add(Database.getVideo(str));
        }

        VideoAdapter videoAdapter=new VideoAdapter(this,R.layout.activity_video_listview_component,videos);
        listView.setAdapter(videoAdapter);

        Utilities.updateUserPageButton(this,btnUserPage);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnPlaylistOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.openPlaylistOptionsDialog(PlaylistPageActivity.this,playlistTitle);
            }
        });


    }
}