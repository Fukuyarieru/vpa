package school.videopirateapp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import school.videopirateapp.DataStructures.User;
import school.videopirateapp.Database.Database;
import school.videopirateapp.R;

public class SignupActivity extends AppCompatActivity {


    EditText etName;
    EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        etName=findViewById(R.id.Signup_EditText_Username);
        etPassword=findViewById(R.id.Signup_EditText_Password);
    }
    public void Close(View view) {
        finish();
    }
    public void CreateUser(View view) {
        String name=etName.getText().toString();
        String password=etPassword.getText().toString();
        if(name.isEmpty()||password.isEmpty()) {
            Toast.makeText(this,"Please fill in both username and password",Toast.LENGTH_SHORT).show();
        } else {
            User newUser=new User(name,password);
            Database.addUser(newUser);
            Toast.makeText(this,"Created user successfully:\n"+newUser.toString(),Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}