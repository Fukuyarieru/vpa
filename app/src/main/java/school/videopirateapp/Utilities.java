package school.videopirateapp;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Utilities {
    public static<T> ArrayList<T> HashMapToArrayList(@NonNull HashMap<String,T> hashMap) {
        // this function does not do any sorting
        ArrayList<T> arrayList = new ArrayList<>();
        for (T value : hashMap.values()) {
            arrayList.add(value);
        }
        return arrayList;
    }
}
