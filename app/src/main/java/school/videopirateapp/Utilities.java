package school.videopirateapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import school.videopirateapp.Activities.VideoPageActivity;

public class Utilities {
    public static<T> ArrayList<T> HashMapToArrayList(@NonNull HashMap<String,T> hashMap) {
        Log.i("Utilities","Converting HashMap to ArrrayList");
        // this function does not do any sorting
        ArrayList<T> arrayList = new ArrayList<>();
        for (T value : hashMap.values()) {
            arrayList.add(value);
        }
        return arrayList;
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
    public static void openVideoPage(@NonNull Context currentActivityThis, String videoTitle) {
        Intent intent=new Intent(currentActivityThis, VideoPageActivity.class);
        intent.putExtra("videoTitle",videoTitle);
        currentActivityThis.startActivity(intent);
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
}
