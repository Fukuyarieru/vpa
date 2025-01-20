package school.videopirateapp.Database;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import school.videopirateapp.DataStructures.User;

public class Users {

    private static User savedUser=User.Default();
    private Users() {
        throw new UnsupportedOperationException("This class is not instantiable.");
    }


    public static User getSavedUser() {
        return savedUser;
    }

    public static void setSavedUser(User savedUser) {
        Users.savedUser = savedUser;
    }
    public static User getUser(String userName) {
        if(savedUser.getName()!=userName) {
            DatabaseReference userRef= Database.getRef("users").child(userName);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                    if(userSnapshot.exists()) {
                        savedUser=userSnapshot.getValue(User.class);
                    }
                    else {
                        // throw user not exist?
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return savedUser;
    }

}
