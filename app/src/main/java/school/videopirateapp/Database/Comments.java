package school.videopirateapp.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.GlobalVariables;

public abstract class Comments {
    private static final Map<String, Comment> Comments = new HashMap<>();

    private Comments() {
        throw new UnsupportedOperationException("This class is not instantiable.");
    }

    public static void Refresh() {
        Database.getRef("comments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Comments.clear();
                    for (DataSnapshot commentSnapshot : snapshot.getChildren()) {
                        Comment comment = commentSnapshot.getValue(Comment.class);
                        if (comment != null) {
                            Comments.put(comment.getContext(), comment);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static Comment getComment(String commentContext) {
        if (!Comments.containsKey(commentContext)) {
            Refresh();
        }
        return Comments.get(commentContext);
    }

    public static ArrayList<Comment> getCommentsFromVideo() {
        return null;
        // probably wrong to do
    }

    public static ArrayList<Comment> getCommentsFromComment() {
        return null;
        // probably wrong to do
    }

    public static ArrayList<Comment> getCommentsFromContexts(ArrayList<String> contexts) {
        ArrayList<Comment> comments = new ArrayList<>();
        for (String context : contexts) {
            comments.add(Database.getComment(context));
        }
        return comments;
    }

    public static void addComment(Comment comment, String targetContextSection) {

        DatabaseReference commentRef = Database.getRef(comment.getContext());
        commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot commentSnapshot) {
                if (!commentSnapshot.exists()) {
                    comment.setContext(targetContextSection + comment.getComment());


                    commentRef.setValue(comment);
                    Comments.put(comment.getContext(), comment);
                    Log.i("Comments: addComment", "Added comment: " + comment.getContext());
                } else {
                    Log.w("Comments: addComment", "Comment already exists: " + comment.getContext());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Comments: addComment", "Failed to add listener to commentRef: " + error.getMessage());
            }
        });
    }

    public static void downvoteComment(Comment comment) {
        if (!GlobalVariables.loggedUser.isPresent()) {
            Log.e("Database: downvoteComment", "No user logged in");
        }

        User user = GlobalVariables.loggedUser.get();
        DatabaseReference userRef = Database.getRef("users").child(user.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                // check if user exists
                if (userSnapshot.exists()) {
                    DatabaseReference videoRef = Database.getRef("videos").child(comment.getContext());
                    videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                            // check if video exists
                            if (videoSnapshot.exists()) {
                                Video video = videoSnapshot.getValue(Video.class);
                                if (video != null) {
                                    // Check if user previously upvoted
                                    if (user.getUpvotes().contains(comment.toString())) {
                                        comment.setUpvotes(comment.getUpvotes() - 1);
                                    }
                                    // Update user's downvotes
                                    user.getDownvotes().add(comment.toString());
                                    comment.setDownvotes(comment.getDownvotes() + 1);
                                    comment.setScore(comment.getUpvotes() - comment.getDownvotes());

                                    // Update database
                                    Database.updateComment(comment);
                                    userRef.setValue(user);
                                    Log.i("Database: downvoteComment", "Downvoted comment by: " + comment.getAuthor());
                                }
                            } else {
                                Log.e("Database: downvoteComment", "Video does not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Database: downvoteComment", "Failed to add listener to videoRef");
                        }
                    });
                } else {
                    Log.e("Database: downvoteComment", "User does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database: downvoteComment", "Failed to add listener to userRef");
            }
        });
    }

    public static boolean upvoteComment(Comment comment) {
        if (!GlobalVariables.loggedUser.isPresent()) {
            Log.e("Database: upvoteComment", "No user logged in");
            return false;
        }

        User user = GlobalVariables.loggedUser.get();
        DatabaseReference userRef = Database.getRef("users").child(user.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                // check if user exists
                if (userSnapshot.exists()) {
                    DatabaseReference videoRef = Database.getRef("videos").child(comment.getContext());
                    videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                            // check if video exists
                            if (videoSnapshot.exists()) {
                                Video video = videoSnapshot.getValue(Video.class);
                                if (video != null) {
                                    // Check if user previously downvoted
                                    if (user.getDownvotes().contains(comment.toString())) {
                                        comment.setDownvotes(comment.getDownvotes() - 1);
                                    }
                                    // Update user's upvotes
                                    user.getUpvotes().add(comment.toString());
                                    comment.setUpvotes(comment.getUpvotes() + 1);
                                    comment.setScore(comment.getUpvotes() - comment.getDownvotes());

                                    // Update database
                                    Database.updateComment(comment);
                                    userRef.setValue(user);
                                    Log.i("Database: upvoteComment", "Upvoted comment by: " + comment.getAuthor());
                                }
                            } else {
                                Log.e("Database: upvoteComment", "Video does not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Database: upvoteComment", "Failed to add listener to videoRef");
                        }
                    });
                } else {
                    Log.e("Database: upvoteComment", "User does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database: upvoteComment", "Failed to add listener to userRef");
            }
        });
        return true;
    }

    public static void updateComment(Comment comment) {
        DatabaseReference commentRef = Database.getRef(comment.getContext());
        commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    commentRef.setValue(comment);
                    Comments.put(comment.getContext(), comment);
                    Log.i("Comments: updateComment", "Updated comment: " + comment.getContext());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void deleteComment(Comment comment) {

    }

    public static void initialize() {
        DatabaseReference commentRef = Database.getRef("comments");
        commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    commentRef.setValue(new HashMap<String, Comment>().put("defaultComment", Comment.Default()));
                    Refresh();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
