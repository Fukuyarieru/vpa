package school.videopirateapp;

import java.util.HashMap;

public class Videos {

    HashMap<String,Video>videos;

    public HashMap<String, Video> getVideos() {
        return videos;
    }

    public void setVideos(HashMap<String, Video> videos) {
        this.videos = videos;
    }

    public Videos(HashMap<String, Video> videos) {
        this.videos = videos;
    }

    public Videos() {
        this(new HashMap<>());
    }
}
