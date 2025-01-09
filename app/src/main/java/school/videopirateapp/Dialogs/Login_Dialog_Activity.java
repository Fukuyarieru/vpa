package school.videopirateapp.Dialogs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import school.videopirateapp.DataStructures.User;
import school.videopirateapp.Database.Database;
import school.videopirateapp.R;
import school.videopirateapp.Activities.SignupActivity;

public class Login_Dialog_Activity extends AppCompatActivity {

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
        // TODO, REMAKE THIS LATER
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            // Display a message if the fields are empty
            Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference userRef= Database.getRef("users").child(username);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                if(userSnapshot.exists()) {
                    User user=userSnapshot.getValue(User.class);
                    if(user.getPassword()==password) {
                        Button userPage=findViewById(R.id.MainMenu_Button_UserPage);
                        userPage.setText(username);
                        finish();
                    }
                    else {
                        // Handle incorrect password
                        Toast.makeText(Login_Dialog_Activity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    // Handle case where user does not exist
                    Toast.makeText(Login_Dialog_Activity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void openSignupActivity(View view){
        // TODO
        Intent openSignupActivity=new Intent(Login_Dialog_Activity.this, SignupActivity.class);
        openSignupActivity.putExtra("username", etUsername.getText().toString());
        openSignupActivity.putExtra("password", etPassword.getText().toString());
        startActivity(openSignupActivity);
    }
}