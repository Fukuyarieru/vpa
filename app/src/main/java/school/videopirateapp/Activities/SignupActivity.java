package school.videopirateapp.Activities;

import static school.videopirateapp.Utilities.Feedback;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import school.videopirateapp.datastructures.User;
import school.videopirateapp.database.Database;
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
            Feedback(this, "Please fill in both username and password");
        } else {
            User newUser=new User(name,password);
            Database.addUser(newUser);
            Feedback(this, "User created successfully");
            finish();
        }
    }
}