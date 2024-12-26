package school.videopirateapp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance("https://videopiratingapp-default-rtdb.europe-west1.firebasedatabase.app/");
//    private static DatabaseReference databaseReference = database.getReference();

//    public static DatabaseReference GetRef(String ref) {
//        if (ref=="") {
//            return database.getReference("ERROR_REF_CANNOT_BE_EMPTY");
//        }
//        return database.getReference(ref);
//    }
    public static User getUser(String userName) {


    }
    public static FirebaseDatabase getDatabase() {
        return database;
    }
    public static void add(User newUser) {
        database.getReference(newUser.getPath()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    database.getReference(newUser.getPath()).setValue(newUser);
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
    public static void add(Comment newComment, Video targetVideo) {
        // two things are done
        // first is that the comment gets added to the user's comments
        // second is that the comment has to be added to the targeted video

        // first check if video exists at all
        database.getReference(targetVideo.getPath()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Video video=snapshot.getValue(Video.class);

                    database.getReference(newComment.getAuthor().getPath()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshotTWO) {
                            if(snapshotTWO.exists()) {

                                User user=snapshotTWO.getValue(User.class);
                                user.Comments.add(newComment);
                                video.addComment(newComment);

                                // CONTINUE TO UPDATE DATABASE FROM HERE, TODO, MAKE CODE FOR IT
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // user does not exist
                        }
                    })

                    video.addComment(newComment);
                }
                else {
                    // video does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })
    }
    public static void add(Video newVideo) { // video already got a user ini it
        // change the unique key later to be some ID or something?
        database.getReference(newVideo.getPath()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists())  {
                    database.getReference(newVideo.getPath()).setValue(newVideo);
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
    public static void add(Playlist newPlaylist) {
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
