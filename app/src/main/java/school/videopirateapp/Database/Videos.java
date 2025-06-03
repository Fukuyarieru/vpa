package school.videopirateapp.Database;

import static school.videopirateapp.Utilities.EvaluateVideo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;

public abstract class Videos {

    // TODO, check this interaction between the uninstantiable Map and the instantiable HashMap
    private static Map<String, Video> Videos = new HashMap<>();

    private Videos() {
        throw new UnsupportedOperationException("This class is not instantiable.");
    }

    public static Map<String, Video> getVideos() {
        return Videos;
    }

    @Deprecated
    public static void setVideos(HashMap<String, Video> newVideos) {
        Log.i("Videos, set videos", newVideos.toString());
        Videos = newVideos;
    }

    public static Video getVideo(String videoTitle) {
        // this function does not refresh the videos, videos get refreshed only when Refresh() is called
        Log.i("Videos: getVideo", "Fetched Video: FINISH THIS MESSAGE");
        if (!Videos.containsKey(videoTitle)) {
            return null;
        }
        return Videos.get(videoTitle);
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
                                // Check if user previously upvoted
                                if (user.getUpvotes().contains(targetVideo.getTitle())) {
                                    targetVideo.setUpvotes(targetVideo.getUpvotes() - 1);
                                }
                                // Use the User class's downvoteVideo method
                                user.downvoteVideo(targetVideo);
                                targetVideo.setDownvotes(targetVideo.getDownvotes() + 1);
                                EvaluateVideo(targetVideo);

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

    public static void updateVideo(@NonNull Video video) {
        DatabaseReference videoRef = Database.getRef("videos").child(video.getTitle());
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
                                // Check if user previously downvoted
                                if (user.getDownvotes().contains(targetVideo.getTitle())) {
                                    targetVideo.setDownvotes(targetVideo.getDownvotes() - 1);
                                }
                                // Use the User class's upvoteVideo method
                                user.upvoteVideo(targetVideo);
                                targetVideo.setUpvotes(targetVideo.getUpvotes() + 1);
                                EvaluateVideo(targetVideo);

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

    public static void Refresh() {
        Log.i("Videos: Refresh", "Refreshing Videos");
        DatabaseReference videosRef = Database.getRef("videos");
        videosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videosSnapshot) {
                if (videosSnapshot.exists()) {
                    Map<String, Video> VideoMap = new HashMap<>();
                    Log.i("Videos: Refresh", "Found videos from database");
                    for (DataSnapshot videoSnapshot : videosSnapshot.getChildren()) {
                        Video video = videoSnapshot.getValue(Video.class);
                        if (video != null) {
                            VideoMap.put(videoSnapshot.getKey(), video);
                            Log.i("Videos: Refresh", "Fetched video: " + video.getTitle());
                        } else {
                            Log.e("Videos: Refresh", "Fetched video is null");
                        }
                    }
                    Videos = VideoMap;
                } else {
                    Log.e("Videos: Refresh", "Videos do not exist?");
                }
                Log.i("Videos: Refresh,", "Finished getting videos");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Videos: Refresh", "DatabaseError Failed to fetch videos: " + error.getMessage());
            }
        });
    }

    public static void initialize() {
        Log.i("Videos: initialize", "Starting video initialization");
        DatabaseReference videosRef = Database.getRef("videos");

        // Check if videos tree exists and initialize if needed
        videosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Log.w("Videos: initialize", "Videos tree does not exist, creating it");
                    // Create empty tree without clearing existing data
                    videosRef.setValue(new HashMap<String, Video>()).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.i("Videos: initialize", "Videos tree created");
                            // Add default video to new tree
                            addVideo(Video.Default());
                            Refresh();
                        } else {
                            Log.e("Videos: initialize", "Failed to create videos tree: " + task.getException());
                        }
                    });
                } else if (!snapshot.hasChildren()) {
                    Log.w("Videos: initialize", "Videos tree is empty, adding default video");
                    addVideo(Video.Default());
                    Refresh();
                } else {
                    Log.i("Videos: initialize", "Videos tree exists with data, refreshing");
                    Refresh();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Videos: initialize", "Failed to initialize videos: " + error.getMessage());
            }
        });
    }

    public static void addVideo(Video newVideo) {
        DatabaseReference videoRef = Database.getRef("videos").child(newVideo.getTitle());
        videoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videoSnapshot) {
                // check if video doesn't exist
                if (!videoSnapshot.exists()) {
                    DatabaseReference userRef = Database.getRef("users").child(newVideo.getUploader());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            // check if user exists
                            if (userSnapshot.exists()) {
                                videoRef.setValue(newVideo);
                                User user = userSnapshot.getValue(User.class);
                                user.getUploads().addVideo(newVideo);
                                userRef.setValue(user);
                                Videos.put(newVideo.getTitle(), newVideo);
                                Log.i("Videos: addVideo", "Added video to database: " + newVideo.getTitle());
                            } else {
                                // user does not exist, do not add the video
                                Log.e("Videos: addVideo", "User does not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Videos: addVideo", "Failed to add listener to userRef");
                        }
                    });
                } else {
                    Log.w("Videos: addVideo", "Video already exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // some error
                Log.e("Videos: addVideo", "Failed to add listener to videoRef");
            }
        });
    }
}
