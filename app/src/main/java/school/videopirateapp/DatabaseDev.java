package school.videopirateapp;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DatabaseDev extends AppCompatActivity {
//    FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");
//    DatabaseReference myRef = database.getReference();
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
//    public void Add(View view){
//        String treeStr=Tree.getText().toString();
//        String messageStr=Message.getText().toString();
//        Database.GetRef(treeStr).setValue(messageStr);
//        Toast.makeText(Database_Dev.this, "Added " + Message.getText().toString(), Toast.LENGTH_SHORT).show();
//    }
//    public void Get(View view) {
////        myRef=database.getReference(Tree.getText().toString());
//        String treeStr=Tree.getText().toString();
//        Database.GetRef(treeStr).get().addOnSuccessListener(dataSnapshot -> {
//            Message.setText(dataSnapshot.getValue(String.class));
//            Toast.makeText(this, Message.getText().toString(), Toast.LENGTH_SHORT).show();
//        }).addOnFailureListener(dataSnapshot -> {
//            Message.setText("ERROR");
//            Toast.makeText(Database_Dev.this, "Not Found", Toast.LENGTH_SHORT).show();
//        });
//    }
//    public void Remove(View view) {
//        String treeStr=Tree.getText().toString();
//        Database.GetRef(treeStr).get().addOnSuccessListener(dataSnapshot -> {
//            Message.setText("Removed: " + dataSnapshot.getValue(String.class));
//            Toast.makeText(Database_Dev.this, "Removed " + Message.getText().toString(), Toast.LENGTH_SHORT).show();
//            Database.Remove(treeStr);
//        });
//    }
}