package school.videopirateapp;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

public class User{
    public ArrayList<Comment> getComments() {
        return Comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        Comments = comments;
    }

    public ArrayList<Playlist> getOwnedPlaylists() {
        return ownedPlaylists;
    }

    public void setOwnedPlaylists(ArrayList<Playlist> ownedPlaylists) {
        this.ownedPlaylists = ownedPlaylists;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

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
        // NOTE: NEVER AGAIN ADD DEFAULT DETAILS TO DEFAULT DATA, THAT IS NOT NECESSERY TO BE THERE

        // Empty constructor for Firebase
        this("@Default");
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
        this.image=null; // TODO , do this later
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
//    public HashMap<String, String> ToHashMap() {
//        HashMap<String, String> userHashMap = new HashMap<>();
//        userHashMap.put("name", name);
//        userHashMap.put("comments",Comments.toString()); // problem here because of arraylist
//        userHashMap.put("uploads",Uploads.toString());
//        userHashMap.put("playlists",this.ownedPlaylists.toString()); // problem here because of arraylist
//        // toString on problematic code is a temporary solution , TODO fix it
//        return userHashMap;
//    }
    public static User Default() {
        return new User();
    }
//    public void Watch(Video video) {
//        Intent intent=new Intent(,VideoPlayerActivity.class);
//
//    }
}