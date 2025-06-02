package school.videopirateapp.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

                        Log.i("Database: addComment", "Added comment" + newComment.getComment() + " to video " + targetVideo.getTitle());
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

   public static void evaluateVideo(@NonNull Video video) {
      // Calculate score based on upvotes minus downvotes
      int score = video.getUpvotes() - video.getDownvotes();
      video.setScore(score);
      updateVideo(video);
      Log.i("Database: evaluateVideo", "Updated score for video " + video.getTitle() + " to " + score);
   }

   public static void upvoteVideo(Video targetVideo, User user) {
      DatabaseReference userRef = Database.getRef("users").child(user.getName());
      userRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot userSnapshot) {
            // check if user exists
            if (userSnapshot.exists()) {
               DatabaseReference videoRef = Database.getRef("videos").child(targetVideo.getTitle());
               videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                     // check if video exists
                     if (videoSnapshot.exists()) {
                        // Use the User class's upvoteVideo method
                        user.upvoteVideo(targetVideo);
                        targetVideo.setUpvotes(targetVideo.getUpvotes() + 1);
                        evaluateVideo(targetVideo);
                        
                        videoRef.setValue(targetVideo);
                        userRef.setValue(user);
                        Log.i("Database: upvoteVideo", "Upvoted video " + targetVideo.getTitle());
                     } else {
                        Log.e("Database: upvoteVideo", "Video does not exist");
                     }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {
                     Log.e("Database: upvoteVideo", "Failed to add listener to videoRef");
                  }
               });
            } else {
               Log.e("Database: upvoteVideo", "User does not exist");
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {
            Log.e("Database: upvoteVideo", "Failed to add listener to userRef");
         }
      });
   }

   public static void downvoteVideo(Video targetVideo, User user) {
      DatabaseReference userRef = Database.getRef("users").child(user.getName());
      userRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot userSnapshot) {
            // check if user exists
            if (userSnapshot.exists()) {
               DatabaseReference videoRef = Database.getRef("videos").child(targetVideo.getTitle());
               videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                     // check if video exists
                     if (videoSnapshot.exists()) {
                        // Use the User class's downvoteVideo method
                        user.downvoteVideo(targetVideo);
                        targetVideo.setDownvotes(targetVideo.getDownvotes() + 1);
                        evaluateVideo(targetVideo);
                        
                        videoRef.setValue(targetVideo);
                        userRef.setValue(user);
                        Log.i("Database: downvoteVideo", "Downvoted video " + targetVideo.getTitle());
                     } else {
                        Log.e("Database: downvoteVideo", "Video does not exist");
                     }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {
                     Log.e("Database: downvoteVideo", "Failed to add listener to videoRef");
                  }
               });
            } else {
               Log.e("Database: downvoteVideo", "User does not exist");
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {
            Log.e("Database: downvoteVideo", "Failed to add listener to userRef");
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
      Log.i("Database: getPlaylist", "Getting playlist from database:" + playlistTitle);
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
            } else {
               Log.w("Database: addVideo", "Video already exists");
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

      DatabaseReference playlistRef = Database.getRef("playlists").child("&" + newPlaylist.getTitle());
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
      DatabaseReference videoRef = Database.getRef("videos").child(video.getTitle());
      videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
            // check if video exists
            if (videoSnapshot.exists()) {
               DatabaseReference playlistRef = Database.getRef("playlists").child("&" + targetPlaylist.getTitle());
               playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                     // check if playlist exists
                     if (playlistSnapshot.exists()) {
                        targetPlaylist.addVideo(video);
                        playlistRef.setValue(targetPlaylist);
                        Log.i("Database: addVideoToPlaylist", "Added video to playlist");
                     } else {
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

   public static void upvotePlaylist(Playlist targetPlaylist, User user) {
      DatabaseReference userRef = Database.getRef("users").child(user.getName());
      userRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot userSnapshot) {
            // check if user exists
            if (!userSnapshot.exists()) {
               DatabaseReference playlistRef = Database.getRef("playlists").child(targetPlaylist.getTitle());
               playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                     // check if playlist exists
                     if (playlistSnapshot.exists()) {
                        targetPlaylist.setUpvotes(targetPlaylist.getUpvotes() + 1);
                        user.getUpvotes().add(targetPlaylist.getTitle());
                        if (user.getDownvotes().contains(targetPlaylist.getTitle())) {
                           user.getDownvotes().remove(targetPlaylist.getTitle());
                           targetPlaylist.setDownvotes(targetPlaylist.getDownvotes() - 1);
                        }
                        playlistRef.setValue(targetPlaylist);
                        userRef.setValue(user);
                        Log.i("Database: upvotePlaylist", "Upvoted playlist " + targetPlaylist.getTitle());
                     } else {
                        Log.e("Database: upvotePlaylist", "Playlist does not exist");
                     }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {
                     Log.e("Database: upvotePlaylist", "Failed to add listener to playlistRef");
                  }
               });
            } else {
               Log.e("Database: upvotePlaylist", "User does not exist");
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {
            Log.e("Database: upvotePlaylist", "Failed to add listener to userRef");
         }
      });
   }

   public static void downvotePlaylist(Playlist targetPlaylist, User user) {
      DatabaseReference userRef = Database.getRef("users").child(user.getName());
      userRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot userSnapshot) {
            // check if user exists
            if (!userSnapshot.exists()) {
               DatabaseReference playlistRef = Database.getRef("playlists").child(targetPlaylist.getTitle());
               playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                     // check if playlist exists
                     if (playlistSnapshot.exists()) {
                        targetPlaylist.setDownvotes(targetPlaylist.getDownvotes() + 1);
                        user.getDownvotes().add(targetPlaylist.getTitle());
                        if (user.getUpvotes().contains(targetPlaylist.getTitle())) {
                           user.getUpvotes().remove(targetPlaylist.getTitle());
                           targetPlaylist.setUpvotes(targetPlaylist.getUpvotes() - 1);
                        }
                        playlistRef.setValue(targetPlaylist);
                        userRef.setValue(user);
                        Log.i("Database: downvotePlaylist", "Downvoted playlist " + targetPlaylist.getTitle());
                     } else {
                        Log.e("Database: downvotePlaylist", "Playlist does not exist");
                     }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {
                     Log.e("Database: downvotePlaylist", "Failed to add listener to playlistRef");
                  }
               });
            } else {
               Log.e("Database: downvotePlaylist", "User does not exist");
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {
            Log.e("Database: downvotePlaylist", "Failed to add listener to userRef");
         }
      });
   }

   public static void updateUser(@NonNull User user) {
      DatabaseReference userRef = database.getReference("users").child(user.getName());
      userRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot userSnapshot) {
            if (userSnapshot.exists()) {
               userRef.setValue(user);
               Log.i("Database: updateUser", "Updated user in database: " + user.getName());
            } else {
               Log.e("Database: updateUser", "User does not exist: " + user.getName());
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {
            Log.e("Database: updateUser", "Failed to check if user exists: " + error.getMessage());
         }
      });
   }

   public static void updateVideo(@NonNull Video video) {
      DatabaseReference videoRef = database.getReference("videos").child(video.getTitle());
      videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
            if (videoSnapshot.exists()) {
               videoRef.setValue(video);
               Log.i("Database: updateVideo", "Updated video in database: " + video.getTitle());
            } else {
               Log.e("Database: updateVideo", "Video does not exist: " + video.getTitle());
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {
            Log.e("Database: updateVideo", "Failed to check if video exists: " + error.getMessage());
         }
      });
   }

   public static void updatePlaylist(@NonNull Playlist playlist) {
      DatabaseReference playlistRef = database.getReference("playlists").child(playlist.getTitle());
      playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
            if (playlistSnapshot.exists()) {
               playlistRef.setValue(playlist);
               Log.i("Database: updatePlaylist", "Updated playlist in database: " + playlist.getTitle());
            } else {
               Log.e("Database: updatePlaylist", "Playlist does not exist: " + playlist.getTitle());
            }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {
            Log.e("Database: updatePlaylist", "Failed to check if playlist exists: " + error.getMessage());
         }
      });
   }

   public static void updateComment(@NonNull Comment comment, @NonNull Video video) {
      if (video == null) {
         Log.e("Database: updateComment", "Video is null");
         return;
      }
      
      if (comment == null) {
         Log.e("Database: updateComment", "Comment is null");
         return;
      }

      try {
         // Get the video from the database to ensure we have the latest version
         Video currentVideo = getVideo(video.getTitle());
         if (currentVideo == null) {
            Log.e("Database: updateComment", "Video not found in database: " + video.getTitle());
            return;
         }

         // Find and update the comment in the video's comments list
         ArrayList<Comment> comments = currentVideo.getComments();
         boolean commentFound = false;
         for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getContext().equals(comment.getContext())) {
               comments.set(i, comment);
               commentFound = true;
               break;
            }
         }

         if (!commentFound) {
            Log.e("Database: updateComment", "Comment not found in video: " + video.getTitle());
            return;
         }

         // Update the video in the database
         updateVideo(currentVideo);
         Log.i("Database: updateComment", "Comment updated successfully");
      } catch (Exception e) {
         Log.e("Database: updateComment", "Error updating comment: " + e.getMessage());
      }
   }

   public static void initialize() {
      Log.i("Database: initialize", "Initializing database");
      Users.initialize();
      Videos.initialize();
      Playlists.initialize();
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
