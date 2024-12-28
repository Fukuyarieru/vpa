package school.videopirateapp;

import java.util.ArrayList;
import java.util.HashMap;

public class Playlist {
    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getPlaylistDescription() {
        return playlistDescription;
    }

    public void setPlaylistDescription(String playlistDescription) {
        this.playlistDescription = playlistDescription;
    }

    ArrayList<Video> videos;
    String title; // Playlist names will start with a '#', just because I wanted
    User owner;
    String playlistDescription;


    // TODO figure out how to work showing the videos in the database

    public Playlist() {
        this.videos =new ArrayList<Video>();
        this.title ="#Default";
        this.owner =User.Default(); // recursive here, TODO FIX

        // gonna check with database
        this.videos.add(Video.Default()); // recursive here, TODO FIX
        this.playlistDescription="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus congue velit vel lacus blandit dignissim.";
    }
    public Playlist(String playListName, User owner){
        // no need to check database here, because database checks itself
        if(!playListName.startsWith("#")) {
            playListName ="#"+playListName;
        }
        this.title =playListName;
        this.videos =new ArrayList<Video>();
        this.playlistDescription="";
        this.owner=owner;
    }
//    public HashMap<String,String> ToHashMap() {
//        HashMap<String,String>playListHashMap=new HashMap<String,String>();
//        playListHashMap.put("title",this.title);
//        playListHashMap.put("owner",this.owner.name);
//        playListHashMap.put("description",this.playlistDescription);
//        playListHashMap.put("videos",videos.toString());
//        return playListHashMap;
//    }
    public static Playlist Default() {
        return new Playlist();
    }
    public String getPath() {
        return "playlists/" + this.title + "/";
    }
}
