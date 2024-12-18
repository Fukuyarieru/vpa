package school.videopirateapp;

import android.content.Intent;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.internal.InternalTokenProvider;

import java.util.ArrayList;
import java.util.HashMap;

public class User extends DatabaseAccesser {
    String name;
    Playlist Uploads;
    ArrayList<Comment> Comments;
    ArrayList<Playlist> ownedPlaylists;
    Byte[] image;
    // Integer totalViews =====> TODO
    // Integer totalUpvotes ===> TODO
    // Integer totalDownvotes =>TODO


    // Default constructor required for Firebase
    public User() {
        // Empty constructor for Firebase
        this.name = "@Default";
        this.Uploads = new Playlist("#Uploads",this);
        this.Comments=new ArrayList<Comment>();
        this.ownedPlaylists=new ArrayList<Playlist>();
        this.image=null; // TODO this later
    }

    // Constructor with parameters
    public User(String name) {
        if (!name.startsWith("@")) {
            name = "@" + name;
        }
        this.name = name;
        this.Uploads = new Playlist("#Uploads",this);  // If you want to initialize Playlist when a User is created
        this.Comments = new ArrayList<Comment>();
        this.ownedPlaylists=new ArrayList<Playlist>();
    }

    // Getters and setters for Firebase to access the fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        HashMap<String, String> userHashMap = new HashMap<>();
        userHashMap.put("name", name);
        userHashMap.put("comments",Comments.toString()); // problem here because of arraylist
        userHashMap.put("uploads",Uploads.toString());
        userHashMap.put("playlists",this.ownedPlaylists.toString()); // problem here because of arraylist
        // toString on problematic code is a temporary solution , TODO fix it
        return userHashMap;
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

    public static DatabaseReference GetTreeRef() {
        return Database.GetRef("users/");
    }
    public static String GetTreePath() {
        return "users/";
    }
    public static User GetUser(String name) {
        // oh what great stupid code
        return (User)(Object)Database.GetRef("users/"+name+"/").toString();
    }
    public static String GetUserPath(String userName) {
//        Database.GetReference(User.GetTreePath()+userName);
        return GetTreePath()+"@"+userName+"/";
    }
    public void addPlayListOwnership(Playlist newOwnedPlaylist) {
        this.ownedPlaylists.add(newOwnedPlaylist);
    }
    public boolean isOwningPlaylist(Playlist playlistInQuestion) {
        return this.ownedPlaylists.contains(playlistInQuestion);
    }
//    public void Watch(Video video) {
//        Intent intent=new Intent(,VideoPlayerActivity.class);
//
//    }
}