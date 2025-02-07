package school.videopirateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import school.videopirateapp.Activities.MainMenuActivity;
import school.videopirateapp.Activities.VideoPageActivity;
import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.Playlist;
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;

public class MainActivity extends AppCompatActivity {
//    public static FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");
//    public static DatabaseReference databaseReference = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initializeDatabase();

//        Log.i() - information
//        Log.e() - error
//        Log.w() - warning
//        Log.d() - debug
//        Log.v() - verbose
//        Log.wtf() - wtf, never supposed to happen

        // TODO, make all return back to page return client to the MainMenu

        // TODO, I can use hashmaps for User and Video and Playlist

        // IDEAS
        // point system
        // watch history
        // add activity transition animation

        // TODO, learn about fragments, they will be useful

        // TODO, bug in adding comments, can create duplicate comments, not supposed to

//        User myUser=new User("fukuya");
//        Video myVideo=new Video("my video",myUser.getName());
//        Comment myComment=new Comment("fukuya","test");
//        Database.addUser(myUser);
//        Database.addVideo(myVideo);
//        Database.addComment(myComment,myVideo);

        // TODO, enable later
//        testDatabase();


        Intent intent=new Intent(this, MainMenuActivity.class);
        startActivity(intent);

//
        // Every class for use in the database will have its own Add, Remove, and Get functions to run directly staticly from the class
        // As like in, User.Add(User), Comment.Add(Comment), Video.Add(Video)
        // CHANGE OF MIND ON THE BELOW, why not just make so to have Database.Add(thing)
        // Alright, Every class that needs to be in database gets default fns, GetTree for its reference tree,initialize somethind idk and else


        // warning to me: adding an empty tree key is BAD, it destroys the database
        // figured: it accesses the "main folder" of the database, which is the database itself

        // NOTE FOR USER PICTURES, RECOMMEND SOME WHEN IN THE PROCESS THAT PFPs SHOULD BE 512x512, DO THIS SCALING ALSO DURING RUNTIME WHEN MAKING AND LOADING PICTURES

        // TO ADD OBJECTS INTO THE DATABASE, THE STRUCTURE IS TREE/2TREE/3TREE, IN WHICH, FOR EACH TREE YOU CAN STORE DATA SEPARATELY AND ACT ON IT AS A CLASS HERE

//        Intent intent=new Intent(this, MainMenuActivity.class);
//        startActivity(intent);
    }
    public void initializeDatabase() {
        DatabaseReference videosRef=Database.getRef("videos");
        videosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    Database.addVideo(Video.Default());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference playlistsRef=Database.getRef("playlists");
        playlistsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    Database.addPlaylist(Playlist.Default());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference usersRef=Database.getRef("users");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    Database.addUser(User.Default());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}