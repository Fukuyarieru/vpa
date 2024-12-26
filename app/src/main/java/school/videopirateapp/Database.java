package school.videopirateapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Database {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");
//    private static DatabaseReference databaseReference = database.getReference();

//    public static DatabaseReference GetRef(String ref) {
//        if (ref=="") {
//            return database.getReference("ERROR_REF_CANNOT_BE_EMPTY");
//        }
//        return database.getReference(ref);
//    }
    public static User getUser(String userName) {

        return database.getReference("users").child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=snapshot.getValue(User.class)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static FirebaseDatabase getDatabase() {
        return database;
    }
    public static void Add(User user) {
        if (!Database.IsExist(User.GetUserPath(user.name))) {
            Database.GetRef(User.GetTreePath() + user.name + "/").setValue(user.ToHashMap());
        }

//        Database.GetReference(User.GetUserPath(user.Name)).setValue(user.ToHashMap());

//        if ( User.GetTree()
//        HashMap<String,Object> databaseUser=new HashMap<>();
//        databaseUser.put("name",user.Name);
//        databaseUser.put("comments",user.Comments);
//        databaseUser.put("uploads",user.Uploads);

//        Database.GetFirestore().collection("users").document(user.Name).set(user.ToHashMap());

//        User.GetTree().setValue(user.Name,user);
    }
    public static boolean IsExist(String path) {
        database.getReference(path).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return false;
        // what?
    }
    public static void Add(Comment newComment, Video targetVideo) {

    }
    public static void Add(Video newVideo) { // video already got a user ini it
        // change the unique key later to be some ID or something?
        if(!Database.IsExist(Video.GetVideoPath(newVideo.title))) {
            Database.GetRef(Video.GetTreePath() + newVideo.title + "/").setValue(newVideo.ToHashMap());
        }
    }
    public static void Add(Playlist newPlaylist) {
        if(!Database.IsExist(newPlaylist.getPath())) {
            Database.GetRef(newPlaylist.getPath()).setValue(newPlaylist.ToHashMap());
        }
    }
//    public static DatabaseReference Users() {
//        return Database.GetReference("users/");
//    }
}
