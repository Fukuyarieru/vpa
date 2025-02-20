package school.videopirateapp.DataStructures;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private String name;
    private Playlist Uploads;
    private Map<String, ArrayList<Comment>> Comments;
    private ArrayList<String> ownedPlaylists;
    private String Password;
    private ArrayList<Byte> image; //TODO, change this type later

    // Integer totalViews =====> TODO
    // Integer totalUpvotes ===> TODO
    // Integer totalDownvotes => TODO
    // Integer watched ========> TODO

    private static final User defaultUser=new User();


    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addPlaylist(Playlist newPlaylist) {
        newPlaylist.setOwner(this.name);
        String newPlaylistTitle=newPlaylist.getTitle();
        if(!this.ownedPlaylists.contains(newPlaylistTitle)) {
            this.ownedPlaylists.add(newPlaylistTitle);
        }
    }
    public Playlist getUploads() {
        return Uploads;
    }

    public void setUploads(Playlist uploads) {
        this.Uploads = uploads;
    }
    public Map<String, ArrayList<Comment>> getComments() {
        return Comments;
    }

    public void setComments(Map<String, ArrayList<Comment>> comments) {
        Comments = comments;
    }

    public ArrayList<String> getOwnedPlaylists() {
        return ownedPlaylists;
    }

    public void setOwnedPlaylists(ArrayList<String> ownedPlaylists) {
        this.ownedPlaylists = ownedPlaylists;
    }

    public ArrayList<Byte> getImage() {
        return image;
    }

    public void setImage(ArrayList<Byte> image) {
        this.image = image;
    }
    public void addComment(Comment newComment) {
        // TODO, CHECK THIS CODE LATER AS I DID NOT
        if(!this.Comments.containsKey(newComment.getContext())) {
            ArrayList<Comment> arrComments=new ArrayList<>();
            arrComments.add(newComment);
            this.Comments.put(newComment.getContext(),arrComments);
        } else {
            ArrayList<Comment>arrComments=this.Comments.get(newComment.getContext());
            arrComments.add(newComment);
            this.Comments.put(newComment.getContext(),arrComments);
        }
    }
    public void addVideo(Video newVideo) {
        this.Uploads.addVideo(newVideo);
    }

    // Default constructor required for Firebase
    public User() {
        // NOTE: NEVER AGAIN ADD DEFAULT DETAILS TO DEFAULT DATA, THAT IS NOT NECESSERY TO BE THERE

        // Empty constructor for Firebase
        this("@Default","123");
    }

    // Constructor with parameters
    public User(String name, String password) {
        if (!name.startsWith("@")) {
            name = "@" + name;
        }
        this.name = name;
        this.Uploads = new Playlist("Uploads",name);  // If you want to initialize Playlist when a User is created
        this.Comments = new HashMap<String,ArrayList<Comment>>();
        ArrayList<Comment>arrComments=new ArrayList<>();
        arrComments.add(Comment.Default());
        this.Comments.put(Comment.Default().getContext(),arrComments);
        this.ownedPlaylists=new ArrayList<>();
        // the User which we create is not initialized yet, so we cannot use some custom function we made and instead will have to put defaul playlist manually
        this.getOwnedPlaylists().add(Playlist.Default().getTitle());
        this.image=new ArrayList<>(); // TODO , do this later
        this.Password=password;
    }
    public static User Default() {
        return defaultUser;
    }
//    public void Watch(Video video) {
//        Intent intent=new Intent(,VideoPlayerActivity.class);
//
//    }
}