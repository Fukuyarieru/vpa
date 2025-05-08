package school.videopirateapp.Dialogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import school.videopirateapp.DataStructures.User;
import school.videopirateapp.Database.Database;
import school.videopirateapp.R;
import school.videopirateapp.Activities.SignupActivity;

public class LoginDialogActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dialog);

        etUsername =findViewById(R.id.Login_Dialog_EditText_Username);
        etPassword =findViewById(R.id.Login_Dialog_EditText_Password);
        btnLogin=findViewById(R.id.Login_Dialog_Button_Login);
        btnSignup=findViewById(R.id.Login_Dialog_Button_Signup);
    }
    public void confirmLogin(View view){
//        Intent intent=new Intent(this, UserPageActivity.class);
//        startActivity(intent);

//        // TODO, REMAKE THIS LATER
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
//
        if (username.isEmpty() || password.isEmpty()) {
            // Display a message if the fields are empty
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
        }
        else {
            User desiredUser=Database.getUser(username);
            if(desiredUser.getPassword().equals(password)){
                Button userPage = findViewById(R.id.MainMenu_Button_UserPage);
                userPage.setText(username);
                Toast.makeText(this,"Logged in succesfully",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
//        }
    }
    public void openSignupActivity(View view){
        // TODO
        Intent openSignupActivity=new Intent(this, SignupActivity.class);
        openSignupActivity.putExtra("username", etUsername.getText().toString());
        openSignupActivity.putExtra("password", etPassword.getText().toString());
        startActivity(openSignupActivity);
    }
}