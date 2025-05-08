package school.videopirateapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import school.videopirateapp.Activities.CommentPageActivity;
import school.videopirateapp.Activities.PlaylistPageActivity;
import school.videopirateapp.Activities.UserPageActivity;
import school.videopirateapp.Activities.VideoPageActivity;
import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;

public class Utilities {
    private Utilities() {
        throw new UnsupportedOperationException("This class is not instantiable.");
    }

    public static <T> ArrayList<T> HashMapToArrayList(@NonNull HashMap<String, T> hashMap) {
        Log.i("Utilities", "Converting HashMap to ArrrayList");
        // this function does not do any sorting
        ArrayList<T> arrayList = new ArrayList<>();
        for (T value : hashMap.values()) {
            arrayList.add(value);
        }
        return arrayList;
    }

    public static <T> ArrayList<T> MapToArrayList(@NonNull Map<String, T> hashMap) {
        Log.i("Utilities", "Converting HashMap to ArrrayList");
        // this function does not do any sorting
        ArrayList<T> arrayList = new ArrayList<>();
        for (T value : hashMap.values()) {
            arrayList.add(value);
        }
        return arrayList;
    }

    public static String TimeNow() {
        Log.i("Utilities", "Getting the time now");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String datetime = dateformat.format(c.getTime());
        return datetime;
    }

    public static void openVideoPage(@NonNull Context currentActivityThis, String videoTitle) {
        Intent intent = new Intent(currentActivityThis, VideoPageActivity.class);
        intent.putExtra("videoTitle", videoTitle);
        currentActivityThis.startActivity(intent);
    }

    public static void openUserPage(@NonNull Context currentActivityThis, String userName) {
        Intent intent = new Intent(currentActivityThis, UserPageActivity.class);
        intent.putExtra("user", userName);
        currentActivityThis.startActivity(intent);
    }

    public static void openCommentPage(@NonNull Context currentActivityThis, String commentContext) {
        Intent intent = new Intent(currentActivityThis, CommentPageActivity.class);
        intent.putExtra("context", commentContext);
        currentActivityThis.startActivity(intent);
    }

    public static void openCommentOptionsDialog(@NonNull Context currentActivityThis, String commentContext) {
        Dialog dialog = new Dialog(currentActivityThis);
        dialog.setContentView(R.layout.activity_comment_options_dialog);
        dialog.show();
    }

    public static void openPlaylistPage(@NonNull Context currentActivityThis, String playlistTitle) {
        Intent intent = new Intent(currentActivityThis, PlaylistPageActivity.class);
        intent.putExtra("playlistTitle", playlistTitle);
        currentActivityThis.startActivity(intent);
    }

    public static void topOptionsMenu(View view) {

    }

    public static Bitmap BytyArrayToBitmap(ArrayList<Byte> byteArray) {
        if (byteArray == null || byteArray.isEmpty()) {
            byte[] arr = new byte[]{1};
            return BitmapFactory.decodeByteArray(arr, 0, arr.length);
        }

        byte[] bytes = new byte[byteArray.size()];
        for (int i = 0; i < byteArray.size(); i++) {
            bytes[i] = byteArray.get(i);
        }

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static void dialogCommentOptions(Context contextThis, Comment comment) {
        Dialog dialog = new Dialog(contextThis);
        dialog.setContentView(R.layout.activity_comment_options_dialog);
        dialog.show();
    }

    public static void openLoginDialog(Context thisContenxt) {
        Dialog loginDialog = new Dialog(thisContenxt); //this screen as context
        loginDialog.setContentView(R.layout.activity_login_dialog);
        EditText etUsername = loginDialog.findViewById(R.id.Login_Dialog_EditText_Username);
        EditText etPassword = loginDialog.findViewById(R.id.Login_Dialog_EditText_Password);
        Button btnLogin = loginDialog.findViewById(R.id.Login_Dialog_Button_Login);
        Button btnSignup = loginDialog.findViewById(R.id.Login_Dialog_Button_Signup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    // Display a message if the fields are empty
                    Toast.makeText(thisContenxt, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                } else if (!username.startsWith("@")) {
                    Toast.makeText(thisContenxt, "Usernames must start with @", Toast.LENGTH_SHORT).show();
                } else {
                    User desiredUser = Database.getUser(username);
//                        Toast.makeText(MainMenuActivity.this,"user: "+desiredUser.getName()+", pass: "+desiredUser.getPassword(),Toast.LENGTH_SHORT).show();
                    if (desiredUser == null) {
                        Toast.makeText(thisContenxt, "User was not found", Toast.LENGTH_SHORT).show();
                    } else if (!desiredUser.getPassword().equals(password)) {
                        Toast.makeText(thisContenxt, "Password does not match", Toast.LENGTH_SHORT).show();
                    } else {
                        GlobalVariables.loggedUser = Optional.of(desiredUser);
                        Toast.makeText(thisContenxt, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        loginDialog.dismiss();
                    }

                }
            }
        });
        loginDialog.show();
    }

    public static void openVideoUploadDialog(Context thisContext) {
        Dialog uploadDialog = new Dialog(thisContext);
        uploadDialog.setContentView(R.layout.activity_upload_video_dialog);
        uploadDialog.show();

        Button btnChooseVideo = uploadDialog.findViewById(R.id.UploadVideo_Dialog_Button_ChooseVideo);
        Button btnUploadVideo = uploadDialog.findViewById(R.id.UploadVideo_Dialog_Button_UploadVideo);
        ImageView thumbnail = uploadDialog.findViewById(R.id.UploadVideo_Dialog_ImageView_Thumbnail);
        EditText etVideoTitle = uploadDialog.findViewById(R.id.UploadVideo_Dialog_EditText_VideoTitle);

        btnUploadVideo.setText("Upload");
        btnChooseVideo.setText("Choose");

        btnUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO, check if etVideoTitle is empty?
                String chosenTitle = etVideoTitle.getText().toString();
//                if chosenTitle=""  // add a function named Video.IsProperName(videoName) which returns true if good and false if bad;
                if (Database.getVideo(chosenTitle) != null) {
                    Toast.makeText(thisContext, "Video with that title already exists", Toast.LENGTH_SHORT).show();
                } else {
                    if (GlobalVariables.loggedUser.isPresent()) {
                        Video newVideo = new Video(chosenTitle, GlobalVariables.loggedUser.get().getName());
                        Toast.makeText(thisContext, "Added video named: " + newVideo.getTitle(), Toast.LENGTH_SHORT).show();
                        Database.addVideo(newVideo);
                    } else {
                        Toast.makeText(thisContext, "Not logged in", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnChooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static void Feedback(Context contextThis, String message) {
        Toast.makeText(contextThis, message, Toast.LENGTH_SHORT).show();
    }
}
