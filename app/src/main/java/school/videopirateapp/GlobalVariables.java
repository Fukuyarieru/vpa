package school.videopirateapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Optional;

import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;

public abstract class GlobalVariables {


    public static Optional<User> loggedUser= Optional.empty();

//    private static final String PREFERENCE_FILE_NAME = "VideoPiratingAppPreferences";
//    private static SharedPreferences sharedPreferences;
//
//    public GlobalVariables(@NonNull Context context) {
//        sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
//    }
    private GlobalVariables() {
        throw new UnsupportedOperationException("This class is not instantiable.");
    }
}
