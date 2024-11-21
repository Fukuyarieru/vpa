package school.videopirateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database=FirebaseDatabase.getInstance().getReference();
    }

    public void TESTBUTTON(View view) {
        TestWriteToDatabase();
    }

    public void TestWriteToDatabase() {
//        User user=new User("Test","123");
//        database.child("users").child("user1").setValue(user)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(MainActivity.this, "DATA WRITTEN SUCCESFULLY", Toast.LENGTH_LONG).show();
//                    }
//                })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                // Show failure message on failed write
//                                Toast.makeText(MainActivity.this, "FAILED TO WRITE DATA", Toast.LENGTH_LONG).show();
//                            }
//                        });
//    }
        // Create a new User object
        User user = new User("Test", "123");

        // Optionally, add some items to the user's Playlist
        Playlist playlist = user.getUploads();
        playlist.addItem("Item1");
        playlist.addItem("Item2");

        // Set the user object in the Firebase database under the "users" node
        database.child("users").child(user.getId()).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Show success message on successful write
                        Toast.makeText(MainActivity.this, "DATA WRITTEN SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Show failure message on failed write
                        Toast.makeText(MainActivity.this, "FAILED TO WRITE DATA", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}