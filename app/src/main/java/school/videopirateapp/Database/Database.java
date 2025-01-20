package school.videopirateapp.Database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.Playlist;
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;

public class Database {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");
    @Deprecated
    public static DatabaseReference getRef(String ref) {
        if (ref=="") {
            return database.getReference("ERROR_REF_CANNOT_BE_EMPTY");
        }
        return database.getReference(ref);
    }
    @Deprecated
    public static FirebaseDatabase getDatabase() {
        return database;
    }
    public static void addUser(User newUser) {
        DatabaseReference userRef=database.getReference("users").child(newUser.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                if (!userSnapshot.exists()) {
                    userRef.setValue(newUser);
                } else {
                    // User already exists
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // some kind of error, ?
            }
        });
    }
    public static void addComment(Comment newComment, Video targetVideo) {
        // first check if video exists at all
        DatabaseReference videoRef=database.getReference("videos").child(targetVideo.getTitle());
        videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                // video needs to exist
                if(videoSnapshot.exists()) {
//                    Video video=videoSnapshot.getValue(Video.class);

                    DatabaseReference userRef=database.getReference("users").child(newComment.getAuthor());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            // user needs to exist
                            if(userSnapshot.exists()) {
                                User user=userSnapshot.getValue(User.class);
                                newComment.setContext(targetVideo.Context());
                                targetVideo.addComment(newComment); // sets the comment context to this video
                                assert user != null;
                                user.addComment(newComment);
                                userRef.setValue(user);
                                videoRef.setValue(targetVideo);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    // video does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // some error idk
            }
        });
    }
    public static User getUser(String userName) {
        return Users.getUser(userName);
    }
    public static Playlist getPlaylist(String playlistTitle) {
        return Playlists.getPlaylist(playlistTitle);
    }
    public static HashMap<String,Video> getVideos() {
        return Videos.getVideos();
    }
//    public static HashMap<String, User> getUsers() {
//        // TODO
//        return // Disabled this function, as i think its probably useless
//    }
    public static Video getVideo(String videoTitle) {
        return Videos.getVideo(videoTitle);
    }

    public static void addVideo(Video newVideo) { // video already got a user ini it
        // change the unique key later to be some ID or something?
        DatabaseReference videoRef= database.getReference("videos").child(newVideo.getTitle()); // is the .child(newVideo) behavior alright?
        videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                if(!videoSnapshot.exists())  {
                    // get the uploader name
                    DatabaseReference userRef=database.getReference("users").child(newVideo.getUploader());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            if(userSnapshot.exists()) {
//                                DatabaseReference videoRef=database.getReference("videos").child(newVideo.getTitle());
                                videoRef.setValue(newVideo);
                                User user=userSnapshot.getValue(User.class);
                                user.getUploads().addVideo(newVideo);
                                userRef.setValue(user);
                            }
                            else {
                                // user does not exist, do not add the video
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else  {
                    // video exists
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // some error
            }
        });
    }
    public static void addPlaylist(Playlist newPlaylist) {

        // TODO, redo the logic here, playlists shouldnt be a standalone object, as they need an owner, therefore this functions need to have someting that the playlist will be connected to, P.S user most likely
        // TODO 2, or not

        DatabaseReference playlistRef=database.getReference("playlists").child(newPlaylist.getTitle());
        playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                if(!playlistSnapshot.exists()) {
                    DatabaseReference userRef=database.getReference("users").child(newPlaylist.getOwner());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            if(userSnapshot.exists()) {
                                User user=userSnapshot.getValue(User.class);
                                user.addPlaylist(newPlaylist);
                                playlistRef.setValue(newPlaylist);
                                userRef.setValue(user);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    // playlist exists
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // some error
            }
        });
    }
    public static void addVideoToPlaylist(Video video, Playlist targetPlaylist) {
        // TODO
    }
}
