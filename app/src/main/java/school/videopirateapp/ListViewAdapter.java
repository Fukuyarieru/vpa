package school.videopirateapp;

import android.content.Context;

import java.util.HashMap;

public class ListViewAdapter<String,T> {
    Context context;
    HashMap<String,T> contents;

    public ListViewAdapter(Context context, HashMap<String,T> contents){
        this.context=context;
        this.contents=contents;
        // TODO
    }
}
