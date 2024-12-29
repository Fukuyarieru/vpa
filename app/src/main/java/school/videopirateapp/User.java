package school.videopirateapp;


import java.util.ArrayList;

public class User{
    private static User defaultUser=new User();

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

    private String name;
    private Playlist Uploads;
    private ArrayList<Comment> Comments;
    private ArrayList<Playlist> ownedPlaylists;
    private Byte[] image;
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
        this.Uploads = new Playlist("Uploads",name);  // If you want to initialize Playlist when a User is created
        this.Comments = new ArrayList<Comment>();
        this.Comments.add(Comment.Default());
        this.ownedPlaylists=new ArrayList<Playlist>();
//        ownedPlaylists.add(Playlist.Default());
        this.image=null; // TODO , do this later
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
        return defaultUser;
    }
    public void addComment(Comment newComment) {
        // TODO, problem, dont put counter increment logic INSIDE the datastructures, this should be handled by Database functions/logic
    }
//    public void Watch(Video video) {
//        Intent intent=new Intent(,VideoPlayerActivity.class);
//
//    }
}