package school.videopirateapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

public class Database_Dev extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference();
    EditText Tree;
    EditText Message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_database_dev);

//        DocumentReference alovelaceDocumentRef = database.collection("users").document("alovelace");
//        CollectionReference usersCollectionRef = database.collection("users");
//        DocumentReference alovelaceDocumentRef = database.document("users/alovelace");


        Tree=findViewById(R.id.Database_Dev_EditText_Tree);
        Message=findViewById(R.id.Database_Dev_EditText_Message);

    }
    public void Add(View view){
        myRef=database.getReference(Tree.getText().toString());
        myRef.setValue(Message.getText().toString());
    }
    public void Get(View view) {
        myRef=database.getReference(Tree.getText().toString());
        myRef.get().addOnSuccessListener(dataSnapshot -> {
            Message.setText(dataSnapshot.getValue(String.class));
        });
    }
    public void Remove(View view) {
        myRef=database.getReference(Tree.getText().toString());
        myRef.get().addOnSuccessListener(dataSnapshot -> {
            Message.setText("Removed: " + dataSnapshot.getValue(String.class));
            myRef.removeValue();
        });
    }
}