package school.videopirateapp.Dialogs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import school.videopirateapp.R;

public class LoginDialogActivity extends AppCompatActivity {

//    EditText etUsername;
//    EditText etPassword;
//    Button btnLogin;
//    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dialog);
    }
}

//        etUsername =findViewById(R.id.Login_Dialog_EditText_Username);
//        etPassword =findViewById(R.id.Login_Dialog_EditText_Password);
//        btnLogin=findViewById(R.id.Login_Dialog_Button_Login);
//        btnSignup=findViewById(R.id.Login_Dialog_Button_Signup);
//
//        Context context = LoginDialogActivity.this;
//
//
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username = etUsername.getText().toString();
//                String password = etPassword.getText().toString();
//                //
//                if (username.isEmpty() || password.isEmpty()) {
//                    // Display a message if the fields are empty
//                    Feedback(context, "Please enter both username and password");
//                }
//                else {
//                    User desiredUser = Database.getUser(username);
//                    if(desiredUser.getPassword().equals(password)){
////                        Button userPage = findViewById(R.id.MainMenu_Button_UserPage);
////                        userPage.setText(username);
//                        Feedback(context,"Logged in succesfully");
//                        finish();
//                    }
//                }
//            }
//        });
//        btnSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Feedback(context,"Signup button clicked");
//                Utilities.openSignupActivity(context);
//            }
//        });
//    }
//    public void confirmLogin(View view){
////        Intent intent=new Intent(this, UserPageActivity.class);
////        startActivity(intent);
//
////        // TODO, REMAKE THIS LATER
//        String username = etUsername.getText().toString();
//        String password = etPassword.getText().toString();
////
//        if (username.isEmpty() || password.isEmpty()) {
//            // Display a message if the fields are empty
//            Log.i("LoginDialogActivity: confirmLogin", "Login attempt with empty fields");
//            Feedback(this, "Please enter both username and password");
//        }
//        else {
//            User desiredUser=Database.getUser(username);
//            if(desiredUser.getPassword().equals(password)){
//                Button userPage = findViewById(R.id.MainMenu_Button_UserPage);
//                userPage.setText(username);
//                Log.i("LoginDialogActivity: confirmLogin", "User logged in successfully: " + username);
//                Feedback(this, "Logged in successfully");
//                finish();
//            }
//        }
////        }
//    }
//    public void openSignupActivity(View view){
//        // TODO
//        Intent openSignupActivity=new Intent(this, SignupActivity.class);
//        openSignupActivity.putExtra("username", etUsername.getText().toString());
//        openSignupActivity.putExtra("password", etPassword.getText().toString());
//        startActivity(openSignupActivity);
//    }