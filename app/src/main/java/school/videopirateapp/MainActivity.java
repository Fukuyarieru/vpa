package school.videopirateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");
    public static DatabaseReference databaseReference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // warning to me: adding an empty tree key is BAD, it destroys the database
        // figured: it accesses the "main folder" of the database, which is the database itself

        // NOTE FOR USER PICTURES, RECOMMEND SOME WHEN IN THE PROCESS THAT PFPs SHOULD BE 512x512, DO THIS SCALING ALSO DURING RUNTIME WHEN MAKING AND LOADING PICTURES

        // TO ADD OBJECTS INTO THE DATABASE, THE STRUCTURE IS TREE/2TREE/3TREE, IN WHICH, FOR EACH TREE YOU CAN STORE DATA SEPARATELY AND ACT ON IT AS A CLASS HERE

        Intent intent=new Intent(this,Database_Dev.class);
        startActivity(intent);
    }
}