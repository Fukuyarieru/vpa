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

    public static Comment getComment(String commentContext) {
        if (!Comments.containsKey(commentContext)) {
            Refresh();
        }
        return Comments.get(commentContext);
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

    public static ArrayList<Comment> getCommentsFromContexts(ArrayList<String> contexts) {
        ArrayList<Comment> comments = new ArrayList<>();
        Log.i("Comments: getCommentsFromContexts", "Getting comments from " + contexts.size() + " contexts");

        for (String context : contexts) {
            Log.d("Comments: getCommentsFromContexts", "Processing context: " + context);
            Comment comment = getComment(context);
            if (comment != null) {
                comments.add(comment);
                Log.d("Comments: getCommentsFromContexts", "Added comment: " + comment.getComment());
            } else {
                Log.w("Comments: getCommentsFromContexts", "Comment not found for context: " + context);
                // Try to refresh the comments cache and try again
                Refresh();
                comment = getComment(context);
                if (comment != null) {
                    comments.add(comment);
                    Log.d("Comments: getCommentsFromContexts", "Added comment after refresh: " + comment.getComment());
                }
            }
        }
        Log.i("Comments: getCommentsFromContexts", "Returning " + comments.size() + " comments");
        return comments;
    }

    public void addComment(Comment comment, String targetContextSection) {
        DatabaseReference commentRef = Database.getRef("comments").child(comment.getContext());
        commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot commentSnapshot) {
                if (!commentSnapshot.exists()) {
                    String targetContextSectionAsPath = targetContextSection.replace("-", "/");
                    DatabaseReference targetContextSectionRef = Database.getRef(targetContextSectionAsPath);
                    targetContextSectionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Store the comment in the main comments collection
                            commentRef.setValue(comment);

                            // Get existing comments array or create new one
                            ArrayList<String> commentsList;
                            if (snapshot.exists() && snapshot.hasChild("commentContextes")) {
                                commentsList = (ArrayList<String>) snapshot.child("commentContextes").getValue();
                            } else {
                                commentsList = new ArrayList<>();
                            }

                            // Add new comment context to the list
                            commentsList.add(comment.getContext());

                            // Update the comments list in the target context
                            targetContextSectionRef.child("comments").setValue(commentsList);
                            Comments.put(comment.getContext(), comment);
                            Log.i("Comments: addComment", "Added comment: " + comment.getContext());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Comments: addComment", "Failed to update comments: " + error.getMessage());
                        }
                    });
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

    public void downvoteComment(Comment comment) {
        if (GlobalVariables.loggedUser.isEmpty()) {
            Log.e("Comments: downvoteComment", "No user logged in");
        }

        User user = GlobalVariables.loggedUser.get();
        DatabaseReference userRef = Database.getRef("users").child(user.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {

                // check if user exists in database
                if (userSnapshot.exists()) {

                    DatabaseReference commentRef = Database.getRef(comment.getContext());
                    commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot commentSnapshot) {
                            if (commentSnapshot.exists()) {
                                comment.downvote();
                                commentRef.setValue(comment);
                                user.downvoteComment(comment);
                                userRef.setValue(user);
                                Log.i("Comments: downvoteComment", "Downvoted comment by: " + comment.getAuthor());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Log.e("Comments: downvoteComment", "User does not exist in database");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database: downvoteComment", "Failed to add listener to userRef");
            }
        });
    }

    public void upvoteComment(Comment comment) {
        if (GlobalVariables.loggedUser.isEmpty()) {
            Log.e("Comments: upvoteComment", "No user logged in");
        }

        User user = GlobalVariables.loggedUser.get();
        DatabaseReference userRef = Database.getRef("users").child(user.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {

                // check if user exists in database
                if (userSnapshot.exists()) {

                    DatabaseReference commentRef = Database.getRef(comment.getContext());
                    commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot commentSnapshot) {
                            if (commentSnapshot.exists()) {
                                comment.upvote();
                                commentRef.setValue(comment);
                                user.upvoteComment(comment);
                                userRef.setValue(user);
                                Log.i("Comments: downvoteComment", "Downvoted comment by: " + comment.getAuthor());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Log.e("Comments: downvoteComment", "User does not exist in database");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Comments: upvoteComment", "Failed to add listener to userRef");
            }
        });
    }

    public void updateComment(Comment comment) {
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

    public void deleteComment(Comment comment) {

    }

    public void initialize() {
        DatabaseReference commentRef = Database.getRef("comments");
        commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    // First ensure the default video exists
                    DatabaseReference videoRef = Database.getRef("videos/defaultVideo");
                    videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                            if (!videoSnapshot.exists()) {
                                // Create default video first
                                Video defaultVideo = Video.Default();
                                videoRef.setValue(defaultVideo);
                            }
                            // Now add the default comment
                            addComment(Comment.Default(), "videos/defaultVideo");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Comments: initialize", "Failed to check/create default video: " + error.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Comments: initialize", "Failed to check comments collection: " + error.getMessage());
            }
        });
    }
}
