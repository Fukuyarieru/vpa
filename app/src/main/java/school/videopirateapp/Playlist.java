package school.videopirateapp;

import java.util.ArrayList;

public class Playlist {

    private ArrayList<Video> videos;
    private String title; // Playlist names will start with a '#', just because I wanted
    private String owner;
    private String playlistDescription;

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


    // TODO figure out how to work showing the videos in the database

    public Playlist() {
        // TODO, here is a bug, @Default is assosiated as a video
        this("&defaultPlaylist","@Default");
    }
    public Playlist(String playlistTitle, String owner){
        // no need to check database here, because database checks itself
        if(!playlistTitle.startsWith("&")) {
            playlistTitle ="&"+playlistTitle;
        }
        this.title =playlistTitle;
        this.videos =new ArrayList<Video>();
        this.playlistDescription="Lorem ipsum dolor sit amet, consectetur adipiscing elit, Phasellus congue velit vel lacus blandit dignissim.";
        this.owner=owner;
    }
    public static Playlist Default() {
        return defaultPlaylist;
    }
}
