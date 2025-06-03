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

public abstract class Database {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");

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

    public static void addComment(@NonNull Comment newComment, @NonNull Video targetVideo) {
        // first check if video exists at all

        Comments.addComment(newComment);

        DatabaseReference videoRef = Database.getRef("videos").child(targetVideo.getTitle());
        videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                // video needs to exist
                if (videoSnapshot.exists()) {
                    DatabaseReference userRef = Database.getRef("users").child(newComment.getAuthor());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            // user needs to exist
                            if (userSnapshot.exists()) {
                                User user = userSnapshot.getValue(User.class);
                                assert user != null; // android studio nagging

                                // update locally
                                targetVideo.addComment(newComment.getContext());
                                user.addComment(newComment);

                                // update database
                                userRef.setValue(user);
                                videoRef.setValue(targetVideo);

                                Log.i("Database: addComment", "Added comment" + newComment.getComment() + " to video " + targetVideo.getTitle());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Database: addComment", "Failed to add listener to userRef");
                        }
                    });
                } else {
                    // video does not exist
                    Log.e("Database: addComment", "Video does not exist");
                    // TODO
//                    throw new RuntimeException("video does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // some error idk
                Log.e("Database: addComment", "Failed to add listener to videoRef");
            }
        });
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
            videosArr.add(Database.getVideo(videoTitle));
        }
        return videosArr;
    }

    public static void addVideo(Video newVideo) {
        Videos.addVideo(newVideo);
    }

    public static void addPlaylist(Playlist newPlaylist) {

        // TODO, redo the logic here, playlists shouldnt be a standalone object, as they need an owner, therefore this functions need to have something that the playlist will be connected to, P.S user most likely
        // TODO 2, or not

        Playlists.addPlaylist(newPlaylist);
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

    public static void updateUser(@NonNull User user) {
        Users.updateUser(user);
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
