package school.videopirateapp.DataStructures;


import static school.videopirateapp.Utilities.getDefaultUserImage;

import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class User {

    private static final User defaultUser = new User();
    private String name;
    // TODO, consider removing this field because upload management turns out to be a pain paired together with playlist management
    private Playlist Uploads;
    private Map<String, ArrayList<String>> Comments;
    private ArrayList<String> ownedPlaylists;
    private String Password;
    private ArrayList<Byte> Image;
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
    public User(String Name, String password) {
        if (!Name.startsWith("@")) {
            Log.w("User: User(name,password)", "Entered name did not start with @, implicitly added it");
            Name = "@" + Name;
        }
        this.name = Name;
        this.Uploads = new Playlist("&Uploads-" + Name, Name);
        this.Comments = new HashMap<String, ArrayList<String>>();

        ArrayList<String>commentsList = new ArrayList<>();
        commentsList.add(Comment.Default().getContext());
        this.Comments.put(Video.Default().getContext(), commentsList);

        this.ownedPlaylists = new ArrayList<>();
        this.getOwnedPlaylists().add(this.Uploads.getTitle());
        this.Image = getDefaultUserImage();
        this.Password = password;
        this.Upvotes = new ArrayList<>();
        this.Downvotes = new ArrayList<>();
        this.videosWatched = 0;
        this.Description = "(Empty description)";
    }

    public static User Default() {
        return defaultUser;
    }

    public String getDescription() {
        return Description;
    }

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

    public Map<String, ArrayList<String>> getComments() {
        return Comments;
    }

    public void setComments(Map<String, ArrayList<String>> comments) {
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
        // String context = newComment.getContext();  // Commented out

//        ArrayList<Comment> a=this.Comments.get(newComment.getContext());
//
//        if() {
//            this.Comments.put(newComment.getContext(), new ArrayList<>());
//        }
//        if()


        String context = "videos";
        if (!this.Comments.containsKey(context)) {
            ArrayList<Comment> arrComments = new ArrayList<>();
            arrComments.add(newComment);
            this.Comments.put(context, arrComments);
            Log.i("User: addComment", "Added first comment to context: " + context);
        } else {
            ArrayList<Comment> arrComments = this.Comments.get(context);
            // Check for duplicate comments
            boolean isDuplicate = false;
            for (Comment existingComment : arrComments) {
                if (existingComment.getAuthor().equals(newComment.getAuthor()) && existingComment.getComment().equals(newComment.getComment())) {
                    isDuplicate = true;
                    Log.w("User: addComment", "Duplicate comment detected and prevented");
                    break;
                }
            }
            if (!isDuplicate) {
                arrComments.add(newComment);
                this.Comments.put(context, arrComments);
                Log.i("User: addComment", "Added comment to existing context: " + context);
            }
        }
    }

   /* Commented out to disable comment-to-comment functionality
   public void addReplyToComment(Comment parentComment, Comment reply) {
      String context = parentComment.getContext();
      if (this.Comments.containsKey(context)) {
         ArrayList<Comment> arrComments = this.Comments.get(context);
         // Find the parent comment and add the reply
         for (Comment comment : arrComments) {
            if (comment.equals(parentComment)) {
               if (comment.getReplies() == null) {
                  comment.setReplies(new ArrayList<>());
               }
               comment.getReplies().add(reply);
               Log.i("User: addReplyToComment", "Added reply to comment in context: " + context);
               break;
            }
         }
      }
   }
   */

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