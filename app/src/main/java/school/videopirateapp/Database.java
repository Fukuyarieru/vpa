package school.videopirateapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class Database {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");
    private static FirebaseFirestore firestore = FirebaseFirestore.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");
//    private static DatabaseReference databaseReference = database.getReference();

    public static DatabaseReference GetRef(String ref) {
        if (ref=="") {
            return database.getReference("ERROR_REF_CANNOT_BE_EMPTY");
        }
        return database.getReference(ref);
    }
    public static String GetVal(String ref) {
        return GetRef(ref).toString();
    }
    public static FirebaseDatabase GetDatabase() {
        return database;
    }
    public static FirebaseFirestore GetFirestore() {
        return firestore;
    }
    public static String Remove(String ref) {
        String remove_val= GetVal(ref);
        GetRef(ref).removeValue();
        return remove_val.toString();
    }
    public static void Add(User user) {
        if (!Database.IsExist(User.GetUserPath(user.Name))) {
            Database.GetRef(User.GetTreePath() + user.Name + "/").setValue(user.ToHashMap());
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
        return !database.getReference(path).equals(null);
    }
    public static void Add(Comment newComment, Video targetVideo) {

    }
    public static void Add(Video newVideo, User targetUser) {

    }
    public static void Add(Video newVideo) {
        // change the unique key later to be some ID or something?
        if(!Database.IsExist(Video.GetVideoPath(newVideo.Title)))
        Database.GetRef(Video.GetTreePath()+newVideo.Title+"/").setValue(newVideo.ToHashMap());
    }
//    public static DatabaseReference Users() {
//        return Database.GetReference("users/");
//    }
}
