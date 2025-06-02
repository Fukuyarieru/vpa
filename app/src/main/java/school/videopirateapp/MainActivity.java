package school.videopirateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Optional;

import school.videopirateapp.Activities.MainMenuActivity;
import school.videopirateapp.DataStructures.Playlist;
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Database.initialize();

        Optional<Integer> a= Optional.of(1);
        int b=a.get();

//        Log.i() - information
//        Log.e() - error
//        Log.w() - warning
//        Log.d() - debug
//        Log.v() - verbose
//        Log.wtf() - wtf, never supposed to happen

        // TODO, i noticed an inconsistency, adding a video to a playlist adds it locally to a user but not the main playlists

        // TODO, make all return back to page return client to the MainMenu

        // TODO, I can use hashmaps for User and Video and Playlist
        // TODO 2, DO NOT USE HASHMAPS AS THEY ARE BROKEN, USE MAPS

        // TODO, All Database database needs to ONLY be stored in its main directory, and "references" (unique key which is name/title) should be used to fetch locally

        // IDEAS
        // point system
        // watch history
        // add activity transition animation

        // TODO, all images are supposed to be saved as Strings as they will be represented by a byte array "afasnfsaova", so it better be a string in database

        // TODO, learn about fragments, they will be useful

        // TODO, bug in adding comments, can create duplicate comments, not supposed to

        Intent intent=new Intent(this, MainMenuActivity.class);
        startActivity(intent);

        // Every class for use in the database will have its own Add, Remove, and Get functions to run directly staticly from the class
        // As like in, User.Add(User), Comment.Add(Comment), Video.Add(Video)
        // CHANGE OF MIND ON THE BELOW, why not just make so to have Database.Add(thing)
        // Alright, Every class that needs to be in database gets default fns, GetTree for its reference tree,initialize somethind idk and else

        // warning to me: adding an empty tree key is BAD, it destroys the database
        // figured: it accesses the "main folder" of the database, which is the database itself

        // NOTE FOR USER PICTURES, RECOMMEND SOME WHEN IN THE PROCESS THAT PFPs SHOULD BE 512x512, DO THIS SCALING ALSO DURING RUNTIME WHEN MAKING AND LOADING PICTURES

        // TO ADD OBJECTS INTO THE DATABASE, THE STRUCTURE IS TREE/2TREE/3TREE, IN WHICH, FOR EACH TREE YOU CAN STORE DATA SEPARATELY AND ACT ON IT AS A CLASS HERE
    }
}