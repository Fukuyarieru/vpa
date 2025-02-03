package school.videopirateapp;

import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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
    public static String TimeNow() {
        Log.i("Utilities","Getting the time now");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String datetime = dateformat.format(c.getTime());
        return datetime;
    }
}
