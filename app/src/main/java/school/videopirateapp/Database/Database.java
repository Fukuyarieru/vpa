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
import school.videopirateapp.Database.Videos;

public class Database {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");
    private static HashMap<String, User> users;
    private static HashMap<String, Video> videos;
    @Deprecated
    public static DatabaseReference getRef(String ref) {
        if (ref=="") {
            return database.getReference("ERROR_REF_CANNOT_BE_EMPTY");
        }
        return database.getReference(ref);
    }
//    public static User getUser(String userName) {
//        User user=new User();
//        database.getReference("users").child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User retUser=snapshot.getValue(User.class);
//                assert retUser != null;
//                // TODO, do this better
//                user.setComments(retUser.getComments());
//                user.setName(retUser.getName());
//                user.setImage(retUser.getImage());
//                user.setUploads(retUser.getUploads());
//                user.setOwnedPlaylists(retUser.getOwnedPlaylists());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return user;
//    }
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
                                newComment.setContext(targetVideo.getContext());
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
    public static HashMap<String,Video> getVideos() {
        // TODO, make these two functions replace the current database "videos" and "users", EVERYTHING GETS AN INDEX IN THIS APP
        // NOTE, the getCategory functions will use return static variables for efficiency
        DatabaseReference videosRef=database.getReference("videos");
        videosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videosShot) {
                if(videosShot.exists()) {
                    // TODO, NEED TO REDO THIS ENTIRE SECITON/LOOK OVER IT, the entire idea is wrong because Database needs to only access, and not Videos, implement this better
                    Videos videos=videosShot.getValue(Videos.class);
                    HashMap<String,Video>videosMap=new HashMap<>();
                    for (DataSnapshot videoSnapshot : videosShot.getChildren()) { // iterator
                        Video video = videoSnapshot.getValue(Video.class);
                        if (video != null) {
                            Videos.getVideos().put(video.getTitle(), video);
                        }
                    }
                    Videos.setVideos()
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return null;
    }
    public static HashMap<String,User> getUsers() {
        // TODO
        return null;
    }
//    public static Video getVideo(String videoTitle) {
    // TODO, this will maybe get use later, it will use the getVideos function

//        Video video;
//        DatabaseReference videoRef=database.getReference("videos").child(videoTitle);
//        videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
//                if(videoSnapshot.exists()){
////                    video=videoSnapshot.getValue(Video.class);
//                    // TODO, finish this?
////                    retVid.addComment();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return null;
//    }

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
