package school.videopirateapp;


import android.graphics.Bitmap;

import java.util.ArrayList;

public class User{

    private String name;
    private Playlist Uploads;
    private ArrayList<Comment> Comments;
    private ArrayList<Playlist> ownedPlaylists;
    private byte[] image;

// Integer totalViews =====> TODO
    // Integer totalUpvotes ===> TODO
    // Integer totalDownvotes => TODO
    // Integer watched ========> TODO

    private static User defaultUser=new User();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addPlaylist(Playlist newPlaylist) {
        newPlaylist.setOwner(this.name);
        if(!this.ownedPlaylists.contains(newPlaylist)) {
            this.ownedPlaylists.add(newPlaylist);
        }
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    public void addComment(Comment newComment) {
        if(!this.Comments.contains(newComment)) {
            this.Comments.add(newComment);
        }
    }
    public void addVideo(Video newVideo) {
        this.Uploads.addVideo(newVideo);
    }

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
        this.ownedPlaylists.add(Playlist.Default());
//        ownedPlaylists.add(Playlist.Default());
        this.image=null; // TODO , do this later
    }
    public static User Default() {
        return defaultUser;
    }
//    public void Watch(Video video) {
//        Intent intent=new Intent(,VideoPlayerActivity.class);
//
//    }
}