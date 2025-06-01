package school.videopirateapp.DataStructures;


import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private static final User defaultUser = new User();
    private String name;
    // TODO, consider removing this field because upload management turns out to be a pain paired together with playlist management
    private Playlist Uploads;
    private Map<String, ArrayList<Comment>> Comments;
    private ArrayList<String> ownedPlaylists;
    private String Password;
    private ArrayList<Byte> Image;
    // String = Context
    // Votes could include also non videos, like playlists or comments
    private ArrayList<String> Upvotes;
    private ArrayList<String> Downvotes;
    private Integer videosWatched;
    private String Description;

    // Default constructor required for Firebase
    public User() {
        // Empty constructor for Firebase
        this("@Default", "123");
    }

    // Constructor with parameters
    public User(String name, String password) {
        if (!name.startsWith("@")) {
            Log.w("User: User(name,password)", "Entered name did not start with @, implicitly added it");
            name = "@" + name;
        }
        this.name = name;
        this.Uploads = new Playlist(name.substring(1) + "-Uploads", name);  // If you want to initialize Playlist when a User is created
        this.Comments = new HashMap<String, ArrayList<Comment>>();
        ArrayList<Comment> arrComments = new ArrayList<>();
        arrComments.add(Comment.Default());
        this.Comments.put(Comment.Default().getContext(), arrComments);
        this.ownedPlaylists = new ArrayList<>();
        // the User which we create is not initialized yet, so we cannot use some custom function we made and instead will have to put defaul playlist manually
        this.getOwnedPlaylists().add(new Playlist("Uploads-"+name,name).getTitle());
        this.Image = new ArrayList<>(); // TODO , do this later
        this.Password = password;
        this.Upvotes = new ArrayList<>();
        this.Downvotes = new ArrayList<>();
        this.videosWatched = 0;
        this.Description = "(Empty description)";
    }

    // Integer totalViews =====> TODO

    public static User Default() {
        return defaultUser;
    }

    public String getDescription() {
        return Description;
    }

    // wont use this constructor and instead just modify after make
//   // Constructor that takes every parameter
//   public User(String name, Playlist uploads, Map<String, ArrayList<Comment>> comments, ArrayList<String> ownedPlaylists, String password, ArrayList<Byte> image, ArrayList<String> upvotes, ArrayList<String> downvotes, Integer videosWatched, String description) {
//      if (!name.startsWith("@")) {
//         Log.w("User: User(name,password)", "Entered name did not start with @, implicitly added it");
//         name = "@" + name;
//      }
//      this.name = name;
//      this.Uploads = uploads;
//      this.Comments = comments;
//      this.ownedPlaylists = ownedPlaylists;
//      this.Password = password;
//      this.Image = image;
//      this.Upvotes = upvotes;
//      this.Downvotes = downvotes;
//      this.videosWatched = videosWatched;
//      this.Description = description;
//   }

    public void setDescription(String description) {
        Description = description;
    }

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
        String newPlaylistTitle = newPlaylist.getTitle();
        if (!this.ownedPlaylists.contains(newPlaylistTitle)) {
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
        return Image;
    }

    public void setImage(ArrayList<Byte> image) {
        this.Image = image;
    }

    public void addComment(Comment newComment) {
        // TODO, CHECK THIS CODE LATER AS I DID NOT
        if (!this.Comments.containsKey(newComment.getContext())) {
            ArrayList<Comment> arrComments = new ArrayList<>();
            arrComments.add(newComment);
            this.Comments.put(newComment.getContext(), arrComments);
        } else {
            ArrayList<Comment> arrComments = this.Comments.get(newComment.getContext());
            arrComments.add(newComment);
            this.Comments.put(newComment.getContext(), arrComments);
        }
    }

    public void addVideo(Video newVideo) {
        this.Uploads.addVideo(newVideo);
    }

    public ArrayList<String> getUpvotes() {
        return Upvotes;
    }

    public void setUpvotes(ArrayList<String> upvotes) {
        this.Upvotes = upvotes;
    }

    public ArrayList<String> getDownvotes() {
        return Downvotes;
    }

    public void setDownvotes(ArrayList<String> downvotes) {
        this.Downvotes = downvotes;
    }

    public Integer getVideosWatched() {
        return videosWatched;
    }

    public void setVideosWatched(Integer videosWatched) {
        this.videosWatched = videosWatched;
    }

    public void upvoteVideo(Video video) {
        if (!this.getUpvotes().contains(video.getTitle())) {
            this.getUpvotes().add(video.getTitle());
            Log.i("User: upvoteVideo", "Added upvote to " + video.getTitle());
        }
        if (this.getDownvotes().contains(video.getTitle())) {
            this.getDownvotes().remove(video.getTitle());
            Log.i("User: upvoteVideo", "Removed downvote to " + video.getTitle());
        }
    }

    public void downvoteVideo(Video video) {
        if (!this.getDownvotes().contains(video.getTitle())) {
            this.getDownvotes().add(video.getTitle());
            Log.i("User: downvoteVideo", "Added downvote to " + video.getTitle());
        }
        if (this.getUpvotes().contains(video.getTitle())) {
            this.getUpvotes().remove(video.getTitle());
            Log.i("User: downvoteVideo", "Removed upvote to " + video.getTitle());
        }
    }

    //    public void Watch(Video video) {
//        Intent intent=new Intent(,VideoPlayerActivity.class);
//
//    }
    @NonNull
    @Override
    public String toString() {
        return "Username: " + this.getName() + "\nPassword" + this.getPassword();
    }
}