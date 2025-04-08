package school.videopirateapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import school.videopirateapp.Activities.CommentPage_Activity;
import school.videopirateapp.Activities.PlaylistPageActivity;
import school.videopirateapp.Activities.UserPageActivity;
import school.videopirateapp.Activities.VideoPageActivity;
import school.videopirateapp.DataStructures.Comment;

public class Utilities {
    public static<T> ArrayList<T> HashMapToArrayList(@NonNull HashMap<String,T> hashMap) {
        Log.i("Utilities", "Converting HashMap to ArrrayList");
        // this function does not do any sorting
        ArrayList<T> arrayList = new ArrayList<>();
        for (T value : hashMap.values()) {
            arrayList.add(value);
        }
        return arrayList;
    }
    public static void Feedback(Context contextThis , String message) {
        Toast.makeText(contextThis,message,Toast.LENGTH_SHORT).show();
        Log.i("Utilities: Feedback",message);
    }
    public static<T> ArrayList<T> MapToArrayList(@NonNull Map<String,T> hashMap) {
        Log.i("Utilities","Converting HashMap to ArrrayList");
        // this function does not do any sorting
        ArrayList<T> arrayList = new ArrayList<>();
        for (T value : hashMap.values()) {
            arrayList.add(value);
        }
        return arrayList;
    }
    public static String TimeNow() {
        Log.i("Utilities","Getting the time now");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String datetime = dateformat.format(c.getTime());
        return datetime;
    }
    public static void openVideoPage(@NonNull Context thisCurrentActivity, String videoTitle) {
        Intent intent=new Intent(thisCurrentActivity, VideoPageActivity.class);
        intent.putExtra("videoTitle",videoTitle);
        thisCurrentActivity.startActivity(intent);
    }
    public static void openUserPage(@NonNull Context currentActivityThis, String userName) {
        Intent intent=new Intent(currentActivityThis, UserPageActivity.class);
        intent.putExtra("user",userName);
        currentActivityThis.startActivity(intent);
    }
    public static void openCommentPage(@NonNull Context thisCurrentActivity, String commentContext, String loggedUsername) {
        Intent intent=new Intent(thisCurrentActivity, CommentPage_Activity.class);
        intent.putExtra("context",commentContext);
        intent.putExtra("user",loggedUsername);
        thisCurrentActivity.startActivity(intent);
    }
    public static void openPlaylistPage(@NonNull Context thisCurrentActivity, String playlistTitle) {
        Intent intent=new Intent(thisCurrentActivity, PlaylistPageActivity.class);
        intent.putExtra("playlistTitle",playlistTitle);
        thisCurrentActivity.startActivity(intent);
    }
    public static void topOptionsMenu(View view) {

    }
    public static Bitmap BytyArrayToBitmap(ArrayList<Byte> byteArray) {
        // TODO, done
        if (byteArray == null || byteArray.isEmpty()) {
            byte[]arr=new byte[]{1};
            return BitmapFactory.decodeByteArray(arr,0,arr.length);
        }

        byte[] bytes = new byte[byteArray.size()];
        for (int i = 0; i < byteArray.size(); i++) {
            bytes[i] = byteArray.get(i);
        }

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    public static void bindLoginDialog() {}
    public static void bindSearchDialog() {}
    public static void dialogCommentOptions(Context contextThis, Comment comment) {
        Dialog dialog=new Dialog(contextThis);
        dialog.setContentView(R.layout.activity_comment_options_dialog);
        dialog.show();
    }
    private Utilities() {
        throw new UnsupportedOperationException("This class is not instantiable.");
    }
}
