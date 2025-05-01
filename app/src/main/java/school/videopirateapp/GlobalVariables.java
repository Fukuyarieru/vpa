package school.videopirateapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.Optional;

import school.videopirateapp.DataStructures.User;

public class GlobalVariables {

    public static Optional<User> loggedUser= Optional.empty();

    private static final String PREFERENCE_FILE_NAME = "VideoPiratingAppPreferences";
    private static SharedPreferences sharedPreferences;

    public GlobalVariables(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }
    private GlobalVariables() {
        throw new UnsupportedOperationException("This class is not instantiable.");
    }
//    public static String getUser() {
//        return sharedPreferences.getString("user","Not logged in");
//    }
//    public static void setUser(String userName) {
//        sharedPreferences.edit().putString("user", userName).apply();
//    }

//    public static Boolean getIsLoggedIn() {
//        return sharedPreferences.getBoolean("loggedIn",false);
//    }
//    public static void setIsLoggedIn(Boolean loggedStatus) {
//        sharedPreferences.edit().putBoolean("loggedIn",loggedStatus);
//    }
//    public static SharedPreferences Init() {
//        // TODO, WHAT
//        sharedPreferences=MainActivity.this.getSharedPreferences(PREFERENCE_FILE_NAME,MainActivity.MODE_PRIVATE);
//    }
}
