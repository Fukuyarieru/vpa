package school.videopirateapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

import java.sql.Ref;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("message");

    EditText test_Ed1;
    EditText test_Ed2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test_Ed1=findViewById(R.id.test_Ed1);
        test_Ed2=findViewById(R.id.test_Ed2);

        myRef = database.getReference("message");
        myRef.setValue("MESSAGE IS STILL WORKING!");

//        Do not run code of this kind
//        myRef=database.getReference("number");
//        for(Double i=0.0;i<10000;i+=0.1){
//            myRef.setValue(i.toString());
//        }
    }
    public void Test_AddTree(View view){
        myRef=database.getReference(test_Ed1.getText().toString());
        myRef.setValue(test_Ed2.getText().toString());
    }
}