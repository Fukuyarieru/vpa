package school.videopirateapp.Database;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import school.videopirateapp.DataStructures.User;

public abstract class Users {

    private static User savedUser = User.Default();

    private Users() {
        throw new UnsupportedOperationException("This class is not instantiable.");
    }


    //    public static User getSavedUser() {
//        return savedUser;
//    }
//    public static void setSavedUser(User savedUser) {
//        Users.savedUser = savedUser;
//    }
    public static User getUser(String userName) {
        // TODO, half ass solution for now
//        if (savedUser == null) {
//            savedUser = User.Default();
//        }
        if (!userName.startsWith("@")) {
            Log.e("Users: getUser", "Given username does not start with @, returning null to fix");
            return null;
        }
        if (!savedUser.getName().equals(userName)) {
            DatabaseReference userRef = Database.getRef("users").child(userName);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                    // check if user exists
                    if (userSnapshot.exists()) {
                        savedUser = userSnapshot.getValue(User.class);
                        assert savedUser != null;
                        Log.i("Users: getUser", "Fetched user from database: " + savedUser.getName());
                    } else {
                        savedUser = null;
                        Log.e("Users: getUser", "User does not exist");
                        // throw user not exist?
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Users: getUser", "Failed to add listener to userRef");
                }
            });
        }
        return savedUser;
    }

}
