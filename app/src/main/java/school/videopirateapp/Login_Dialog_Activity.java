package school.videopirateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login_Dialog_Activity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button btnLogin;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dialog);

        username=findViewById(R.id.Login_Dialog_EditText_Username);
        password=findViewById(R.id.Login_Dialog_EditText_Password);
        btnLogin=findViewById(R.id.Login_Dialog_Button_Login);
        btnSignup=findViewById(R.id.Login_Dialog_Button_Signup);
    }

    public void confirmLogin(View view){
        // TODO
    }
    public void openSignupActivity(View view){
        // TODO
        Intent openSignupActivity=new Intent(Login_Dialog_Activity.this, SignupActivity.class);
        openSignupActivity.putExtra("username",username.getText().toString());
        openSignupActivity.putExtra("password",password.getText().toString());
        startActivity(openSignupActivity);
    }
}