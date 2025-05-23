package school.videopirateapp.Database;

import static school.videopirateapp.Utilities.Feedback;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.Playlist;
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;

public abstract class Database {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");

    @Deprecated
    public static DatabaseReference getRef(String ref) {
        if (ref == "") {
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
        DatabaseReference userRef = database.getReference("users").child(newUser.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                if (!userSnapshot.exists()) {
                    userRef.setValue(newUser);
                    Log.i("Database: addUser", "Added user to database: " + newUser.getName());
                } else {
                    // User already exists
                    Log.w("Database: addUser", "Tried to add user to database, but it already existed, so nothing happened");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // some kind of error, ?
                Log.e("Database: addUser", "Listener error?");
            }
        });
    }

    public static void addComment(@NonNull Comment newComment, @NonNull Video targetVideo) {

        // i cannot do this kind of code here because Videos works like a local copy of the database, local scope. in here the Database i am supposed to check gracefully whats happening
//        if(Videos.getVideo(targetVideo.getTitle())!=null) {
//            targetVideo=Videos.
//        }

        // first check if video exists at all
        DatabaseReference videoRef = Database.getRef("videos").child(targetVideo.getTitle());
        videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                // video needs to exist
                if (videoSnapshot.exists()) {
//                    Video video=videoSnapshot.getValue(Video.class);

                    DatabaseReference userRef = Database.getRef("users").child(newComment.getAuthor());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            // user needs to exist
                            if (userSnapshot.exists()) {
                                User user = userSnapshot.getValue(User.class);
                                newComment.setContext(targetVideo.Context());
                                targetVideo.addComment(newComment); // sets the comment context to this video
                                assert user != null; // TODO, Remove or Replace later, this is here because of android studio nagging
                                user.addComment(newComment);
                                userRef.setValue(user);
                                videoRef.setValue(targetVideo);
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
            }
        });
    }

    public static void upvoteVideo(Video targetVideo) {

        DatabaseReference videoRef = Database.getRef(targetVideo.getTitle());
        videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                if (videoSnapshot.exists()) {
                    Video video = videoSnapshot.getValue(Video.class);
                    if (video != null) {
                        targetVideo.upvote();
                        videoRef.setValue(targetVideo);
                    }
                }
                else {
                    Log.e("Database: upvoteVideo","Video does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        targetVideo.upvote();
    }

    public static void downvoteVideo(Video targetVideo) {
        DatabaseReference videoRef = Database.getRef(targetVideo.getTitle());
        videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                if (videoSnapshot.exists()) {
                    Video video = videoSnapshot.getValue(Video.class);
                    if (video != null) {
                        targetVideo.downvote();
                        videoRef.setValue(targetVideo);
                    }
                } else {
                    Log.e("Database: upvoteVideo","Video does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        targetVideo.downvote();
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
        Log.i("Database: getPlaylist", "Getting playlist from database");
        return Playlists.getPlaylist(playlistTitle);
    }

    public static HashMap<String, Video> getVideos() {
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

    public static void addVideo(Video newVideo) { // video already got a user ini it
        // change the unique key later to be some ID or something?
        DatabaseReference videoRef = database.getReference("videos").child(newVideo.getTitle()); // is the .child(newVideo) behavior alright?
        videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                if (!videoSnapshot.exists()) {
                    // get the uploader name
                    DatabaseReference userRef = database.getReference("users").child(newVideo.getUploader());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            if (userSnapshot.exists()) {
//                                DatabaseReference videoRef=database.getReference("videos").child(newVideo.getTitle());
                                videoRef.setValue(newVideo);
                                User user = userSnapshot.getValue(User.class);
                                user.getUploads().addVideo(newVideo);
                                userRef.setValue(user);
                            } else {
                                // user does not exist, do not add the video
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else {
                    Log.w("Database: addVideo","Video already exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // some error
            }
        });
    }

    public static void addPlaylist(Playlist newPlaylist) {

        // TODO, redo the logic here, playlists shouldnt be a standalone object, as they need an owner, therefore this functions need to have something that the playlist will be connected to, P.S user most likely
        // TODO 2, or not

        DatabaseReference playlistRef = database.getReference("playlists").child(newPlaylist.getTitle());
        playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                if (!playlistSnapshot.exists()) {
                    DatabaseReference userRef = database.getReference("users").child(newPlaylist.getOwner());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            if (userSnapshot.exists()) {
                                User user = userSnapshot.getValue(User.class);
                                user.addPlaylist(newPlaylist);
                                playlistRef.setValue(newPlaylist);
                                userRef.setValue(user);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Log.w("Database: addPlaylist", "Playlist already exists");
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
        // TODO 2, I REALLY SHOULD START USING THIS CAUSE THERE IS A BUG IN WHICH THE PLAYLISTS TREE DOES NOT UPDATE CORRECTLY
    }

//    @Deprecated
//    public static <T extends Object implements> T getObject(String Path) {
//        T val = null;
//        Database.getRef(Path).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    val=snapshot.getValue(T.class);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return val;
//    }
}
