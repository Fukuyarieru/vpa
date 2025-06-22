package school.videopirateapp.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import school.videopirateapp.datastructures.Comment;
import school.videopirateapp.datastructures.User;
import school.videopirateapp.datastructures.Video;
import school.videopirateapp.GlobalVariables;


public abstract class Comments {
    private static final Map<String, Comment> Comments = new HashMap<>();

    private Comments() {
        throw new UnsupportedOperationException("This class is not instantiable.");
    }

    public static Map<String,Comment> getComments() {
        return Comments;
    }

    public static Comment getComment(String commentContext) {
        Log.i("Comments: getComment", "Getting comment for context: " + commentContext);
        if(!Comments.containsKey(commentContext)) {
            return null;
        }
        return Comments.get(commentContext);
    }

    public static void Refresh() {
        Log.i("Comments: Refresh", "Refreshing comments");
        Database.getRef("comments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap<String, Comment> comments = new HashMap<>();
                    for (DataSnapshot commentSnapshot : snapshot.getChildren()) {
                        Comment comment = commentSnapshot.getValue(Comment.class);
                        if (comment != null) {
                            comments.put(comment.getContext(), comment);
                            Log.i("Comments: Refresh", "Fetched comment: " + comment.getContext());
                        }
                    }
                    Comments.clear();
                    Comments.putAll(comments);
                    Log.i("Comments: Refresh", "Refreshed " + comments.size() + " comments");
                } else {
                    Log.w("Comments: Refresh", "No comments found in database");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Comments: Refresh", "Failed to refresh comments: " + error.getMessage());
            }
        });
    }

    public static ArrayList<Comment> getComments(ArrayList<String> contexts) {
         Log.i("Comments: getComments", "Getting comments from contexts: " + contexts);
        ArrayList<Comment>comments= new ArrayList<>();
        for(String context: contexts) {
            comments.add(Database.getComment(context));
        }
        return comments;
    }

    public static void addComment(Comment comment) {
        User user=Database.getUser(comment.getAuthor());
        String targetContextSection=comment.getSource();

        DatabaseReference userRef=Database.getRef(user.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                if(userSnapshot.exists()){
                    DatabaseReference commentRef = Database.getRef("comments").child(comment.getContext());
                    commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot commentSnapshot) {
                            if (!commentSnapshot.exists()) {
                                String targetContextSectionAsPath = targetContextSection.replace("-", "/");
                                DatabaseReference targetContextSectionRef = Database.getRef(targetContextSectionAsPath);
                                targetContextSectionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot sectionSnapshot) {
                                        // Store the comment in the main comments collection
                                        commentRef.setValue(comment);

                                        // get the right key number in section
                                        ArrayList<String> existingCommentsContexts = new ArrayList<>();
                                        for (DataSnapshot childSnapshot : sectionSnapshot.getChildren()) {
                                            String stringValue = childSnapshot.getValue(String.class);
                                            if (stringValue != null) {
                                                existingCommentsContexts.add(stringValue);
                                            }
                                        }
                                        String size=String.valueOf(existingCommentsContexts.size());

                                        targetContextSectionRef.child(size).setValue(comment.getContext());
                                        user.addComment(comment);
                                        Users.addComment(comment);

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static void downvoteComment(Comment comment) {
        if (GlobalVariables.loggedUser.isEmpty()) {
            Log.e("Comments: downvoteComment", "No user logged in");
            return;
        }
        User user = GlobalVariables.loggedUser.get();
        String commentContext=comment.getContext();

        DatabaseReference userRef = Database.getRef("users").child(user.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                if(!userSnapshot.exists()) {
                    Log.w("Comments: downvoteComment", "User does not exist: " + user.getName());
                    return;
                }
                DatabaseReference commentRef = Database.getRef("comments").child(commentContext);
                commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot commentSnapshot) {
                        if (!commentSnapshot.exists()) {
                            Log.w("Comments: downvoteComment", "Comment does not exist: " + commentContext);
                            return;
                        }
                        GlobalVariables.loggedUser.get().downvoteComment(comment);
                        commentRef.setValue(comment);
                        Comments.put(commentContext, comment);
                        Database.updateUser(user);
                        Log.i("Comments: downvoteComment", "Downvoted comment: " + commentContext);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Comments: downvoteComment", "Failed to downvote comment: " + error.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Comments: downvoteComment", "Failed to check if user exists: " + error.getMessage());
            }
        });
    }


    public static void upvoteComment(Comment comment) {
        if (GlobalVariables.loggedUser.isEmpty()) {
            Log.e("Comments: upvoteComment", "No user logged in");
            return;
        }
        User user = GlobalVariables.loggedUser.get();
        String commentContext=comment.getContext();

        DatabaseReference userRef = Database.getRef("users").child(user.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                if(!userSnapshot.exists()) {
                    Log.w("Comments: upvoteComment", "User does not exist: " + user.getName());
                    return;
                }
                DatabaseReference commentRef = Database.getRef("comments").child(commentContext);
                commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot commentSnapshot) {
                        if (!commentSnapshot.exists()) {
                            Log.w("Comments: upvoteComment", "Comment does not exist: " + commentContext);
                            return;
                        }
                        GlobalVariables.loggedUser.get().upvoteComment(comment);
                        commentRef.setValue(comment);
                        Comments.put(commentContext, comment);
                        Database.updateUser(user);
                        Log.i("Comments: upvoteComment", "Upvoted comment: " + commentContext);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Comments: upvoteComment", "Failed to upvote comment: " + error.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Comments: upvoteComment", "Failed to check if user exists: " + error.getMessage());
            }
        });
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
                Log.e("Comments: updateComment", "Failed to update comment: " + error.getMessage());
            }
        });
    }

    public static void initialize() {
        Log.i("Comments: initialize", "Starting comments initialization");
        DatabaseReference commentRef = Database.getRef("comments");
        commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Log.i("Comments: initialize", "No comments found, creating default comment");
                    DatabaseReference videoRef = Database.getRef("videos").child("defaultVideo");
                    videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                            if (!videoSnapshot.exists()) {
                                Log.i("Comments: initialize", "Creating default video");
                                Video defaultVideo = Video.defaultVideo();
                                videoRef.setValue(defaultVideo);
                            }
                            // Add default comment and wait for it to complete
                            Comment defaultComment = Comment.defaultComment();
                            String targetContext = Video.defaultVideo().getCommentsContext();
                            User defaultUser = User.defaultUser();
                            
                            DatabaseReference commentRef = Database.getRef("comments").child(defaultComment.getContext());
                            commentRef.setValue(defaultComment).addOnSuccessListener(aVoid -> {
                                Log.i("Comments: initialize", "Default comment added successfully");
                                Comments.put(defaultComment.getContext(), defaultComment);
                                Refresh();
                            }).addOnFailureListener(e -> {
                                Log.e("Comments: initialize", "Failed to add default comment: " + e.getMessage());
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Comments: initialize", "Failed to check/create default video: " + error.getMessage());
                        }
                    });
                } else {
                    Log.i("Comments: initialize", "Comments exist, refreshing");
                    Refresh();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Comments: initialize", "Failed to check comments collection: " + error.getMessage());
            }
        });
    }

    public void deleteComment(Comment comment) {

    }
}
