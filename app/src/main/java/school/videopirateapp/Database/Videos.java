package school.videopirateapp.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import school.videopirateapp.DataStructures.Video;

public class Videos {

    private Videos() {
        throw new UnsupportedOperationException("This class is not instantiable.");
    }

    private static HashMap<String, Video> Videos = new HashMap<String,Video>();

    public static HashMap<String, Video> getVideos() {
        return Videos;
    }
    public static Video getVideo(String videoTitle) {
        // this function does not refresh the videos, videos get refreshed only when Refresh() is called
        return Videos.get(videoTitle);
    }

    public static void setVideos(HashMap<String, Video> newVideos) {
        Log.i("Videos, set videos", newVideos.toString());
        Videos = newVideos;
    }
    public static void Refresh() {
        DatabaseReference videosRef= Database.getRef("videos");
        videosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videosSnapshot) {
                if(videosSnapshot.exists()) {
                    Log.d("DatabaseDebug", "Raw data: " + videosSnapshot.getValue());
                    GenericTypeIndicator<HashMap<String, Video>> typeIndicator = new GenericTypeIndicator<HashMap<String, Video>>() {};
//                    for (DataSnapshot videoSnapshot : videosSnapshot.getChildren()) {
//                        Video video = videoSnapshot.getValue(Video.class);
//                        if (video != null) {
//                            videoMap.put(videoSnapshot.getKey(), video);
//                        }
//                    }
                    Videos = videosSnapshot.getValue(typeIndicator);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", "Failed to fetch videos: " + error.getMessage());
            }
        });
    }

//    public Videos(HashMap<String, Video> videos) {
//        this.Videos = videos;
//    }

//    public Videos() {
//        this(new HashMap<>());
//    }
}
