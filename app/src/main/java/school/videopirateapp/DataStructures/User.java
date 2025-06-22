package school.videopirateapp.DataStructures;


import static school.videopirateapp.Utilities.getDefaultUserImage;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    private static final User defaultUser = new User();
    private String name;
    // TODO, consider removing this field because upload management turns out to be a pain paired together with playlist management
    private Playlist uploads;
    private Map<String, ArrayList<String>> comments;
    private ArrayList<String> ownedPlaylists;
    private String password;
    private List<Byte> image;
    // Votes could include also non videos, like playlists or comments
    private ArrayList<String> upvotes;
    private ArrayList<String> downvotes;
    private Integer videosWatched;
    private String description;

    // Default constructor required for Firebase
    public User() {
        // Empty constructor for Firebase
        this("@Default", "123");
    }

    // Constructor with parameters
    public User(String Name, String password) {
        if (!Name.startsWith("@")) {
            Log.w("User: User(name,password)", "Entered name did not start with @, implicitly added it");
            Name = "@" + Name;
        }
        this.name = Name;
        this.uploads = new Playlist("&Uploads-" + Name, Name);
        this.comments = new HashMap<String, ArrayList<String>>();

        ArrayList<String>commentsList = new ArrayList<>();
        commentsList.add(Comment.Default().getContext());
        this.comments.put(Video.Default().getContext(), commentsList);

        this.ownedPlaylists = new ArrayList<>();
        this.getOwnedPlaylists().add(this.uploads.getTitle());
        this.image = getDefaultUserImage();
        this.password = password;
        this.upvotes = new ArrayList<>();
        this.downvotes = new ArrayList<>();
        this.videosWatched = 0;
        this.description = "(Empty description)";
    }

    public static User Default() {
        return defaultUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return uploads;
    }

    public void setUploads(Playlist uploads) {
        this.uploads = uploads;
    }

    public Map<String, ArrayList<String>> getComments() {
        return comments;
    }

    public void setComments(Map<String, ArrayList<String>> comments) {
        this.comments = comments;
    }

    public ArrayList<String> getOwnedPlaylists() {
        return ownedPlaylists;
    }

    public void setOwnedPlaylists(ArrayList<String> ownedPlaylists) {
        this.ownedPlaylists = ownedPlaylists;
    }

    public List<Byte> getImage() {
        return image;
    }

    public void setImage(List<Byte> image) {
        this.image = image;
    }

    public void addComment(Comment newComment) {
        String sourceContext=newComment.getSourceContext();
        if(!this.comments.containsKey(sourceContext)) {
            this.comments.put(sourceContext, new ArrayList<>());
        }
        if(this.comments.get(sourceContext).contains(newComment.getContext())) {
            return;
        }
         this.comments.get(sourceContext).add(newComment.getContext());
    }
    public void addVideo(Video newVideo) {
        this.uploads.addVideo(newVideo);
    }

    public ArrayList<String> getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(ArrayList<String> upvotes) {
        this.upvotes = upvotes;
    }

    public ArrayList<String> getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(ArrayList<String> downvotes) {
        this.downvotes = downvotes;
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

    public void upvoteComment(Comment comment) {
        if (!this.getUpvotes().contains(comment.getContext())) {
            this.getUpvotes().add(comment.getContext());
            Log.i("User: upvoteComment", "Added upvote to " + comment.getContext());
        }
        if (this.getDownvotes().contains(comment.getContext())) {
            this.getDownvotes().remove(comment.getContext());
            Log.i("User: upvoteComment", "Removed downvote to " + comment.getContext());
        }
    }

    public void downvoteComment(Comment comment) {
        if (!this.getDownvotes().contains(comment.getContext())) {
            this.getDownvotes().add(comment.getContext());
            Log.i("User: downvoteComment", "Added downvote to " + comment.getContext());
        }

        if (this.getUpvotes().contains(comment.getContext())) {
            this.getUpvotes().remove(comment.getContext());
            Log.i("User: downvoteComment", "Removed upvote to " + comment.getContext());
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Username: " + this.getName() + "\nPassword" + this.getPassword();
    }
}