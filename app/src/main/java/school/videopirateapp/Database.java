package school.videopirateapp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Database {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");
//    private static DatabaseReference databaseReference = database.getReference();

//    public static DatabaseReference GetRef(String ref) {
//        if (ref=="") {
//            return database.getReference("ERROR_REF_CANNOT_BE_EMPTY");
//        }
//        return database.getReference(ref);
//    }
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
    public static FirebaseDatabase getDatabase() {
        return database;
    }
    public static void addUser(User newUser) {
        DatabaseReference userRef=database.getReference("users").child(newUser.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                if (!userSnapshot.exists()) {
//                    database.getReference("users").child(newUser.getName()).setValue(newUser);
                    userRef.child("name").setValue(newUser.getName());
                    userRef.child("image").setValue(newUser.getImage().toString());
                } else {
                    // User already exists
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
//    public static boolean isExist(String path) {
//        database.getReference(path).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return false;
//        // what?
//    }
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

                    DatabaseReference userRef=database.getReference("users").child(newComment.getAuthorName());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            // user needs to exist
                            if(userSnapshot.exists()) {

                                video.addComment(newComment);
                                videoRef.child("comments").child(newComment.getId().toString()).setValue(newComment.getComment());
                                userRef.child("comments").child(targetVideo.getTitle()+":"+newComment.getId()).setValue(newComment.getComment());

//                                User user=userSnapshot.getValue(User.class);
//                                video.addComment(newComment);
//                                user.Comments.add(newComment);
//
//                                // CONTINUE TO UPDATE DATABASE FROM HERE, TODO, MAKE CODE FOR IT
//
//                                database.getReference("users").child(newComment.getAuthorName()).child("comments").child(targetVideo.getTitle()).child(newComment.getId().toString()).setValue(newComment);
//                                database.getReference(targetVideo.getPath()).child("comments").child(newComment.getId().toString()).setValue(newComment);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // user does not exist
                        }
                    });

                    video.addComment(newComment);
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
        })
    }

    public static void addVideo(Video newVideo) { // video already got a user ini it
        // change the unique key later to be some ID or something?
        database.getReference(newVideo.getPath()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                if(!videoSnapshot.exists())  {
                    // get the uploader name
                    String uploaderName=newVideo.getUploaderName();
                    database.getReference("users").child(uploaderName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            if(userSnapshot.exists()) {
                                DatabaseReference videoRef=database.getReference("videos").child(newVideo.getTitle());
                                videoRef.child("title").setValue(newVideo.getTitle());
                                videoRef.child("views").setValue(newVideo.getViews());
                                videoRef.child("uploader").setValue(newVideo.getUploaderName());
                                videoRef.child("upvotes").setValue(newVideo.getUpvotes());
                                videoRef.child("downvotes").setValue(newVideo.getDownvotes());
                                videoRef.child("commentCounter").setValue(newVideo.getCommentCounter());
                                DatabaseReference commentsRef=videoRef.child("comments");
                                ArrayList<Comment>comments=newVideo.getComments();
                                for(Integer i=0;i<comments.size();i++){
                                    commentsRef.child(i.toString()).setValue(comments.get(i));
                                }
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
        database.getReference(newPlaylist.getPath()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) {
                    database.getReference(newPlaylist.getPath()).setValue(newPlaylist);
                }
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
}
