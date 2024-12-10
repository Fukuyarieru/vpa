package school.videopirateapp;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

public class User extends DatabaseAccesser {
    String Name;
    Playlist Uploads;
    ArrayList<Comment> Comments;


    // Default constructor required for Firebase
    public User() {
        // Empty constructor for Firebase
        this.Name = "@Default";
        this.Uploads = new Playlist();
        this.Comments=new ArrayList<Comment>();
    }

    // Constructor with parameters
    public User(String name) {
        if (!name.startsWith("@")) {
            name = "@" + name;
        }
        this.Name = name;
        this.Uploads = new Playlist();  // If you want to initialize Playlist when a User is created
        this.Comments = new ArrayList<Comment>();
    }
//    public void Watch(Video video) {
//        Intent intent=new Intent(this,Video);
//        intent
//    }

    // Getters and setters for Firebase to access the fields
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Playlist getUploads() {
        return Uploads;
    }

    public void setUploads(Playlist uploads) {
        this.Uploads = uploads;
    }

    // test
    // idk about this, probably remove later
    public HashMap<String, String> ToHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", this.Name);
        return hashMap;
    }

    //    public static void Add(User newUser) {
//        DatabaseReference ref=Database.GetReference("users"+"/"+newUser.Name);
//    }
//    public static User Get(User getUser) {
//        return null; // TODO
//    }
//    public static void Remove(User removeUser) {
//
//    }
    public static User Default() {
        return new User();
    }

    public static DatabaseReference GetTree() {
        return Database.GetReference("users");
    }

    public static User Get(String name) {
        User.GetTree().getRef()
    }
}