package school.videopirateapp.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import school.videopirateapp.datastructures.Comment;
import school.videopirateapp.datastructures.User;

public abstract class Users {

    // we dont want to have all users saved in app as it would be unsafe
    private static User savedUser = User.defaultUser();

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
                    Log.i("Users: updateUser", "Updated user in database: " + user.getName());
                } else {
                    Log.e("Users: updateUser", "User does not exist: " + user.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Users: updateUser", "Failed to check if user exists: " + error.getMessage());
            }
        });
    }

    public static User getUser(String userName) {
        if (!userName.startsWith("@")) {
            Log.w("Users: getUser", "Entered user name did not start with @, implicitly added it");
            userName = "@" + userName;
        }
        if (!savedUser.getName().equals(userName)) {
            DatabaseReference userRef = Database.getRef("users").child(userName);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                    // check if user exists
                    if (userSnapshot.exists()) {
                        savedUser = userSnapshot.getValue(User.class);
                        Log.i("Users: getUser", "Fetched user from database: " + savedUser.getName());
                    } else {
                        savedUser = User.defaultUser();
                        Log.e("Users: getUser", "User does not exist in database, returning default user");
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
                    Users.addUser(User.defaultUser());
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
                // check if user doesn't exist
                if (!userSnapshot.exists()) {
                    userRef.setValue(newUser);
                    Log.i("Database: addUser", "Added user to database: " + newUser.getName());
                } else {
                    Log.w("Database: addUser", "User already exists: " + newUser.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database: addUser", "Failed to add listener to userRef");
            }
        });
    }

    public static void addComment(Comment comment) {
        DatabaseReference userRef = Database.getRef("users").child(comment.getAuthor());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                // check if user exists
                if (userSnapshot.exists()) {
                    Log.i("Users: addComment", "Adding comment to user: " + comment.getAuthor());
                    User user = userSnapshot.getValue(User.class);
                    userRef.child("comments").child(comment.getContext()).setValue(comment);
                } else {
                    Log.e("Users: addComment", "User does not exist: " + comment.getAuthor());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Users: addComment", "Failed to add listener to userRef");
            }
        });
    }
}
