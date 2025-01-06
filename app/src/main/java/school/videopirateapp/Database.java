package school.videopirateapp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");

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
        // two things are done
        // first is that the comment gets added to the user's comments
        // second is that the comment has to be added to the targeted video

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
                                targetVideo.addComment(newComment); // sets the comment context to this video
                                user.addComment(newComment);
                                userRef.setValue(user);
                                videoRef.setValue(targetVideo);
//                               // TODO, DELETED TO REDO THIS PART OF CODE

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // user does not exist
                        }
                    });
                }
                else {
                    // video does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static Video getVideo(String videoTitle) {
        DatabaseReference videoRef=database.getReference("videos").child(videoTitle);
        videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                if(videoSnapshot.exists()){
                    Video retVid=Video.Default();
                    // TODO, finish this?
//                    retVid.addComment();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return null;
    }
    public DatabaseReference getReference(String ref) {
        if(ref!="") {
        return database.getReference(ref);}
        else
            return database.getReference("error");
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

//                                videoRef.child("title").setValue(newVideo.getTitle());
//                                videoRef.child("views").setValue(newVideo.getViews());
//                                videoRef.child("uploader").setValue(newVideo.getUploaderName());
//                                videoRef.child("upvotes").setValue(newVideo.getUpvotes());
//                                videoRef.child("downvotes").setValue(newVideo.getDownvotes());
//                                videoRef.child("commentCounter").setValue(newVideo.getCommentCounter());
//                                DatabaseReference commentsRef=videoRef.child("comments");
//                                ArrayList<Comment>comments=newVideo.getComments();
//                                for(Integer i=0;i<comments.size();i++){
//                                    commentsRef.child(i.toString()).setValue(comments.get(i));
//                                }
                                // done?
                            }
                            else {
                                // user does not exist, do not add the video
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
//                    database.getReference(newVideo.getPath()).setValue(newVideo);
                }
                else  {
                    // video exists
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // error
            }
        });
    }
    public static void addPlaylist(Playlist newPlaylist) {

        // TODO, redo the logic here, playlists shouldnt be a standalone object, as they need an owner, therefore this functions need to have someting that the playlist will be connected to, P.S user most likely
        // TODO 2, or not

        DatabaseReference playlistRef=database.getReference("playlists").child(newPlaylist.getName());
        playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                if(!playlistSnapshot.exists()) {
                    DatabaseReference userRef=database.getReference("users").child(newPlaylist.getOwnerName());
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
                            // playlist already exists
                        }
                    });}
                else {
                    // playlist exists
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // error
            }
        });
    }
    public static void addVideoToPlaylist(Video video, Playlist targetPlaylist) {
        // TODO
    }
}
