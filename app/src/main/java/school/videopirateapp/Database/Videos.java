package school.videopirateapp.Database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import school.videopirateapp.DataStructures.Video;

public class Videos {

    // TODO, what the fuck happens here?
    private static HashMap<String, Video> staticVideos =new HashMap<String,Video>();
    private HashMap<String,Video>localVideos;

    public HashMap<String, Video> getLocalVideos() {
        return localVideos;
    }

    public void setLocalVideos(HashMap<String, Video> localVideos) {
        this.localVideos = localVideos;
    }

    public static HashMap<String, Video> getStaticVideos() {
        return staticVideos;
    }
    public static void Refresh() {
        DatabaseReference videosRef= Database.getRef("videos");
        videosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot videosSnapshot) {
                if(videosSnapshot.exists()) {
                    HashMap<String,Video>videos=videosSnapshot.getValue(HashMap.class);
                    Videos.setStaticVideos(videos);
//                    Videos v=videosSnapshot.getValue(Videos.class);
//                    Videos.setStaticVideos(v.staticVideos);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void setStaticVideos(HashMap<String, Video> newVideos) {
        staticVideos = newVideos;
    }

    public Videos(HashMap<String, Video> videos) {
        this.staticVideos = videos;
        this.localVideos=videos;
    }

    public Videos() {
        this(new HashMap<>());
    }
}
