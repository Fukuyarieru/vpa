package school.videopirateapp.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

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
                            Database.addVideo(Video.Default());
                            Refresh();
                        } else {
                            Log.e("Videos: initialize", "Failed to create videos tree: " + task.getException());
                        }
                    });
                } else if (!snapshot.hasChildren()) {
                    Log.w("Videos: initialize", "Videos tree is empty, adding default video");
                    Database.addVideo(Video.Default());
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
}
