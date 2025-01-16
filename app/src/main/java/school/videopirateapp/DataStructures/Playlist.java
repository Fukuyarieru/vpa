package school.videopirateapp.DataStructures;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Playlist {

    private HashMap<String, Video> videos;
    private String title; // Playlist names will start with a '#', just because I wanted
    private String owner;
    private String playlistDescription;

    private static final Playlist defaultPlaylist=new Playlist();

    public HashMap<String, Video> getVideos() {
        return videos;
    }

    public void setVideos(HashMap<String, Video> videos) {
        this.videos = videos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPlaylistDescription() {
        return playlistDescription;
    }

    public void setPlaylistDescription(String playlistDescription) {
        this.playlistDescription = playlistDescription;
    }
    public void addVideo(Video newVideo) {
        this.videos.put(newVideo.getTitle(),newVideo);
    }


    // TODO figure out how to work showing the videos in the database

    public Playlist() {
        // TODO, here is a bug, @Default is assosiated as a video
        this("&defaultPlaylist","@Default");
    }
    public Playlist(String playlistTitle, String owner){
        // no need to check database here, because database checks itself
        if(!playlistTitle.startsWith("&")) {
            playlistTitle ="&"+playlistTitle;
            Log.e("Playlist Contructor","Playlist Title did not start with &");
        }
        if(!owner.startsWith("@")) {
            owner="@"+owner;
            Log.e("Playlist Contructor","Playlist Owner(name) did not start with @");
        }
        this.title =playlistTitle;
        this.videos =new HashMap<String,Video>();
        this.playlistDescription="Lorem ipsum dolor sit amet, consectetur adipiscing elit, Phasellus congue velit vel lacus blandit dignissim.";
        this.owner=owner;
        Log.i("Playlist: Contructor", "Created Playlist with:\nTitle: "+title+"\nDescription: "+playlistDescription+"\nOwner: "+owner);
    }
    public static Playlist Default() {
        return defaultPlaylist;
    }
}
