package school.videopirateapp.Database;

import java.util.HashMap;

import school.videopirateapp.DataStructures.Video;

public class Videos {

    HashMap<String, Video>videos;

    public static HashMap<String, Video> getVideos() {
        return videos;
    }

    public static void setVideos(HashMap<String, Video> videos) {
        videos = videos;
    }

    public Videos(HashMap<String, Video> videos) {
        this.videos = videos;
    }

    public Videos() {
        this(new HashMap<>());
    }
}
