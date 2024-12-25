package school.videopirateapp;

import java.util.ArrayList;

public class User extends DatabaseAccesser {
    String name;
    Playlist Uploads;
    ArrayList<Comment> Comments;
    ArrayList<Playlist> Playlists;
    Byte[] image;

    private static User defaultUser=new User();

    // Integer totalViews =====> TODO
    // Integer totalUpvotes ===> TODO
    // Integer totalDownvotes =>TODO


    // Default constructor required for Firebase
    public User() {
        this.name = "@Default";
        this.Uploads = new Playlist("#Uploads",this);
        this.Uploads.addVideo(new Video("DefaultVideo",this));
        this.Comments=new ArrayList<Comment>();
        this.Comments.add(Comment.Default());
        this.Playlists =new ArrayList<Playlist>();
        this.Playlists.add(new Playlist("#Default",this));
        this.image=null; // TODO this later
    }
    public void addPlaylist(Playlist addedPlaylist) {
        if(!this.Playlists.contains(addedPlaylist)) {
            this.Playlists.add(addedPlaylist);
        }
    }
//    public void addComment


    // Constructor with parameters
    public User(String name) {
        if (!name.startsWith("@")) {
            name = "@" + name;
        }
        this.name = name;
        this.Uploads = new Playlist("#Uploads",this);  // If you want to initialize Playlist when a User is created
        this.Comments = new ArrayList<Comment>();
        this.Playlists =new ArrayList<Playlist>();
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
        // figure out how to make this function
        if (!defaultUser.Playlists.contains(Playlist.Default())) {
            defaultUser.Comments.add(Comment.Default());
        }
        if (!defaultUser.Comments.contains(Comment.Default())) {
            defaultUser.Comments.add(Comment.Default());
        }
        return defaultUser;
    }

//    public static DatabaseReference GetTreeRef() {
//        return Database.GetRef("users/");
//    }
//    public static String GetTreePath() {
//        return "users/";
//    }
    // REWRITE -> IMPLEMENT THIS IN DATABASE CLASS
//    public static User GetUser(String name) {
//        // oh what great stupid code
//        return (User)(Object)Database.GetRef("users/"+name+"/").toString();
//    }
//    public static String GetUserPath(String userName) {
////        Database.GetReference(User.GetTreePath()+userName);
//        return GetTreePath()+"@"+userName+"/";
//    }
    public boolean isOwningPlaylist(Playlist playlistInQuestion) {
//        return this.ownedPlaylists.contains(playlistInQuestion);
//        return Playlist.getPlayListPath(playlistInQuestion)
        return playlistInQuestion.owner==this;
    }
//    public void Watch(Video video) {
//        Intent intent=new Intent(,VideoPlayerActivity.class);
//
//    }
}