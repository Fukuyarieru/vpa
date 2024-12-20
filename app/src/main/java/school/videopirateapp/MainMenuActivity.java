package school.videopirateapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainMenuActivity extends AppCompatActivity {


    Button yourUserButton;
    Button yourPlaylistsButton;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_menu);

        yourPlaylistsButton=findViewById(R.id.MainMenu_Button_YourPlaylists);
        yourUserButton=findViewById(R.id.MainMenu_Button_YourUser);
        listView=findViewById(R.id.MainMenu_ListView);


        // TOAST: YOU MUST LOGIN FIRST
    }
    public void openUserPage() {
//        finish(); // TODO, find how to do this properly, fix this
        Intent intent=new Intent(this,UserPageActivity.class);
        startActivity(intent);
//        setContentView(R.layout.activity_user_page);
    }
}