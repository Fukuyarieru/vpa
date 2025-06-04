package school.videopirateapp.Database;

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

    public static void updateUser(@NonNull User user) {
        DatabaseReference userRef = Database.getRef("users").child(user.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                if (userSnapshot.exists()) {
                    userRef.setValue(user);
                    Log.i("Database: updateUser", "Updated user in database: " + user.getName());
                } else {
                    Log.e("Database: updateUser", "User does not exist in database: " + user.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database: updateUser", "Failed to check if user exists: " + error.getMessage());
            }
        });
    }

    public static User getUser(String userName) {
        if (!userName.startsWith("@")) {
            Log.e("Users: getUser", "Given username does not start with @, returning null to fix");
            return null;
        }
        if (savedUser == null || !savedUser.getName().equals(userName)) {
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

    public static void initialize() {
        DatabaseReference usersRef = Database.getRef("users");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Log.w("Users: initialize", "Creating default user");
                    addUser(User.Default());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Users: initialize", "Failed to initialize users: " + error.getMessage());
            }
        });
    }

    public static void addUser(@NonNull User newUser) {
        DatabaseReference userRef = Database.getRef("users").child(newUser.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                if (!userSnapshot.exists()) {
                    userRef.setValue(newUser);
                    savedUser = newUser;
                    Log.i("Users: addUser", "Added user to database: " + newUser.getName());
                } else {
                    // User already exists
                    Log.w("Users: addUser", "Tried to add user to database, but it already existed, so nothing happened");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Users: addUser", "Listener error?");
            }
        });
    }
}
