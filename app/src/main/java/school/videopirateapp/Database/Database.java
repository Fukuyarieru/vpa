package school.videopirateapp.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.Playlist;
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.GlobalVariables;

public abstract class Database {
    private static final FirebaseDatabase database = FirebaseDatabase
            .getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");

    @Deprecated
    public static DatabaseReference getRef(String ref) {
        if (ref.isEmpty()) {
            Log.e("Database: getRef", "Empty string passed to database reference");
            return database.getReference("ERROR_REF_CANNOT_BE_EMPTY");
        }
        Log.i("Database: getRef", "Got database reference: " + ref);
        return database.getReference(ref);
    }

    @Deprecated
    public static FirebaseDatabase getDatabase() {
        return database;
    }

    public static void addUser(@NonNull User newUser) {
        Users.addUser(newUser);
    }

    public static void addComment(@NonNull Comment newComment, @NonNull String targetContextSection, User user) {
        Comments.addComment(newComment, targetContextSection, user);
    }

    public static void addComment(@NonNull Comment newComment, @NonNull String targetContextSection) {
        Comments.addComment(newComment, targetContextSection, GlobalVariables.loggedUser.get());
    }

    public static void addCommentToUser(@NonNull Comment newComment, @NonNull User user) {
        Users.addComment(newComment, user);
    }

    public static Comment getComment(String commentContext) {
        return Comments.getComment(commentContext);
    }
    public static ArrayList<Comment> getCommentsArray(ArrayList<String> contexts) {
        return Comments.getComments(contexts);
    }

    public static void upvoteVideo(Video targetVideo, User user) {
        Videos.upvoteVideo(targetVideo, user);
    }

    public static void downvoteVideo(Video targetVideo, User user) {
        Videos.downvoteVideo(targetVideo, user);
    }

    public static User getUser(String userName) {
        Log.i("Database: getUser", "Getting user from database: " + userName);
        User user = Users.getUser(userName);
        if (user == null) {
            Log.w("Database: getUser", "Fetched user does not exist");
        }
        return user;
    }

    public static Playlist getPlaylist(String playlistTitle) {
        Log.i("Database: getPlaylist", "Getting playlist from database:" + playlistTitle);
        return Playlists.getPlaylist(playlistTitle);
    }

    public static Map<String, Video> getVideos() {
        Log.i("Database: getVideos", "Getting videos from database");
        return Videos.getVideos();
    }

    public static Video getVideo(String videoTitle) {
        Log.i("Database: getVideo", "Getting video from database:" + videoTitle);
        return Videos.getVideo(videoTitle);
    }

    public static ArrayList<Video> getVideosArray(ArrayList<String> videoTitles) {
        ArrayList<Video> videosArr = new ArrayList<>();
        for (String videoTitle : videoTitles) {
            videosArr.add(Videos.getVideo(videoTitle));
        }
        return videosArr;
    }

    public static void addVideo(Video newVideo) {
        Videos.addVideo(newVideo);
    }

    public static void addPlaylist(Playlist newPlaylist) {
        Playlists.addPlaylist(newPlaylist);
    }

    public static void updateUser(User user) {
        Users.updateUser(user);
    }

    public static void addVideoToPlaylist(Video video, Playlist targetPlaylist) {
        DatabaseReference videoRef = Database.getRef("videos").child(video.getTitle());
        videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                // check if video exists
                if (videoSnapshot.exists()) {
                    DatabaseReference playlistRef = Database.getRef("playlists").child("&" + targetPlaylist.getTitle());
                    playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                            // check if playlist exists
                            if (playlistSnapshot.exists()) {
                                targetPlaylist.addVideo(video);
                                playlistRef.setValue(targetPlaylist);
                                Log.i("Database: addVideoToPlaylist", "Added video to playlist");
                            } else {
                                Log.e("Database: addVideoToPlaylist", "Playlist does not exist");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Database: addVideoToPlaylist", "Failed to add listener to playlistRef");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database: addVideoToPlaylist", "Failed to add listener to videoRef");
            }
        });
    }

    public static void upvotePlaylist(Playlist targetPlaylist, User user) {
        Playlists.upvotePlaylist(targetPlaylist, user);
    }

    public static void downvotePlaylist(Playlist targetPlaylist, User user) {
        Playlists.downvotePlaylist(targetPlaylist, user);
    }

    public static void updateVideo(@NonNull Video video) {
        Videos.updateVideo(video);
    }

    public static void updatePlaylist(@NonNull Playlist playlist) {
        Playlists.updatePlaylist(playlist);
    }

    public static void updateComment(@NonNull Comment comment) {
        Comments.updateComment(comment);
    }

    public static void initialize() {
        Log.i("Database: initialize", "Initializing database");
        Users.initialize();
        Videos.initialize();
        Playlists.initialize();
        Comments.initialize();

        // Wait for initialization to complete
        int attempts = 0;
        while (attempts < 10) {
            Log.i("Database: initialize", "Waiting for initialization to complete");
            if (Comments.getComments().isEmpty() || Playlists.getPlaylists().isEmpty()) {
                try {
                    Thread.sleep(100);
                    attempts++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                break;
            }
        }

        Refresh();
    }

    public static void Refresh() {
        Log.i("Database: Refresh", "Refreshing database");
        Log.i("Database: Refresh, Videos", Videos.getVideos().toString());
        Log.i("Database: Refresh, Comments", Comments.getComments().toString());
        Log.i("Database: Refresh, Playlists", Playlists.getPlaylists().toString());
        Videos.Refresh();
        Comments.Refresh();
        Playlists.Refresh();
    }

    public static void upvoteComment(Comment comment) {
        Comments.upvoteComment(comment);
    }

    public static void downvoteComment(Comment comment) {
        Comments.downvoteComment(comment);
    }

    public static void upvotePlaylist(Playlist playlist) {
        Playlists.upvotePlaylist(playlist);
    }

    public static void downvotePlaylist(Playlist playlist) {
        Playlists.downvotePlaylist(playlist);
    }
}
