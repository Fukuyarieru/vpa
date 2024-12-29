package school.videopirateapp;

import java.util.ArrayList;

public class Playlist {
    private static Playlist defaultPlaylist=new Playlist();

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public String getName() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwnerName() {
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

    private ArrayList<Video> videos;
    private String title; // Playlist names will start with a '#', just because I wanted
    private String owner;
    private String playlistDescription;


    // TODO figure out how to work showing the videos in the database

    public Playlist() {
        // TODO, here is a bug, @Default is assosiated as a video
        this("defaultPlaylist","@Default"); // User.Default().getName()
        // gonna check with database
//        this.videos.add(Video.Default()); // recursive here, TODO FIX
        this.playlistDescription="Lorem ipsum dolor sit amet, consectetur adipiscing elit, Phasellus congue velit vel lacus blandit dignissim.";
    }
    public Playlist(String playListName, String owner){
        // no need to check database here, because database checks itself
        if(!playListName.startsWith("&")) {
            playListName ="&"+playListName;
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
        return defaultPlaylist;
    }
}
