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
        // first check if video exists at all
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
                                newComment.setContext(targetVideo.Context());
                                targetVideo.addComment(newComment); // sets the comment context to this video
                                user.addComment(newComment);

                                // update database
                                userRef.setValue(user);
                                videoRef.setValue(targetVideo);

                                Log.i("Database: addComment", "Added comment"+ newComment.getComment()+" to video "+targetVideo.getTitle());
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
                        Log.i("Database: upvoteVideo", "Upvoted video "+targetVideo.getTitle());
                    }
                }
                else {
                    Log.e("Database: upvoteVideo","Video does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // some error
                Log.e("Database: upvoteVideo", "Failed to add listener to videoRef");
            }
        });
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
                        Log.i("Database: downvoteVideo", "Downvoted video "+targetVideo.getTitle());
                    }
                } else {
                    Log.e("Database: upvoteVideo","Video does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // some error
                Log.e("Database: downvoteVideo", "Failed to add listener to videoRef");
            }
        });
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

    public static void addVideo(Video newVideo) { // video already got a user ini it
        // change the unique key later to be some ID or something?

        DatabaseReference videoRef = database.getReference("videos").child(newVideo.getTitle());
        videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                // check if video doesn't exist
                if (!videoSnapshot.exists()) {
                    DatabaseReference userRef = database.getReference("users").child(newVideo.getUploader());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            // check if user exists
                            if (userSnapshot.exists()) {
//                                DatabaseReference videoRef=database.getReference("videos").child(newVideo.getTitle());
                                videoRef.setValue(newVideo);
                                User user = userSnapshot.getValue(User.class);
                                user.getUploads().addVideo(newVideo);
                                userRef.setValue(user);
                                Log.i("Database: addVideo", "Added video to database: " + newVideo.getTitle());
                            } else {
                                // user does not exist, do not add the video
                                Log.e("Database: addVideo", "User does not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Database: addVideo", "Failed to add listener to userRef");
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
                Log.e("Database: addVideo", "Failed to add listener to videoRef");
            }
        });
    }

    public static void addPlaylist(Playlist newPlaylist) {

        // TODO, redo the logic here, playlists shouldnt be a standalone object, as they need an owner, therefore this functions need to have something that the playlist will be connected to, P.S user most likely
        // TODO 2, or not

        DatabaseReference playlistRef =Database.getRef("playlists").child("&"+newPlaylist.getTitle());
        playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                // check if playlist doesn't exist
                if (!playlistSnapshot.exists()) {
                    DatabaseReference userRef = database.getReference("users").child(newPlaylist.getOwner());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            // check if associated user exists
                            if (userSnapshot.exists()) {
                                User user = userSnapshot.getValue(User.class);
                                assert user != null;
                                user.addPlaylist(newPlaylist);
                                playlistRef.setValue(newPlaylist);
                                userRef.setValue(user);
                                Log.i("Database: addPlaylist", "Added playlist to database: " + newPlaylist.getTitle());
                            } else {
                                // user does not exist, do not add the playlist
                                Log.e("Database: addPlaylist", "User does not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Database: addPlaylist", "Failed to add listener to userRef");
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
                Log.e("Database: addPlaylist", "Failed to add listener to playlistRef");
            }
        });
    }

    public static void addVideoToPlaylist(Video video, Playlist targetPlaylist) {
        DatabaseReference videoRef=Database.getRef("videos").child(video.getTitle());
        videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                // check if video exists
                if(videoSnapshot.exists()) {
                    DatabaseReference playlistRef=Database.getRef("playlists").child("&"+targetPlaylist.getTitle());
                    playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                            // check if playlist exists
                            if(playlistSnapshot.exists()) {
                                targetPlaylist.addVideo(video);
                                playlistRef.setValue(targetPlaylist);
                                Log.i("Database: addVideoToPlaylist", "Added video to playlist");
                            }
                            else {
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

//    @Deprecated
//    public static <T extends Object> T getObject(String Path) {
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
