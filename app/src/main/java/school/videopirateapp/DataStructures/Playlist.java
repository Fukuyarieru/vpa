package school.videopirateapp.DataStructures;

import android.util.Log;

import java.util.ArrayList;

public class Playlist {
    private static final Playlist defaultPlaylist = new Playlist();


    // TODO, will probably need to change this to <String,String> as i want to store the names, and fetch them from database locally to the related function
    private ArrayList<String> videos;
    private String title; // Playlist names will start with a '#', just because I wanted
    private String owner;
    private String playlistDescription;
    private ArrayList<Byte> picture;
    private Integer score;
    private Integer upvotes;
    private Integer downvotes;


    public ArrayList<Byte> getPicture() {
        return picture;
    }

    public void setPicture(ArrayList<Byte> picture) {
        this.picture = picture;
    }

    public ArrayList<String> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<String> videos) {
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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        this.downvotes = downvotes;
    }

    public void addVideo(Video newVideo) {
        String videoTitle = newVideo.getTitle();
        this.videos.add(videoTitle); // todo, this looks ugly, check laters
    }


    // TODO figure out how to work showing the videos in the database

    public Playlist() {
        // TODO, here is a bug, @Default is assosiated as a video
        this("&defaultPlaylist", "@Default");
    }

    public Playlist(String playlistTitle, String owner) {
        // no need to check database here, because database checks itself
        if (!playlistTitle.startsWith("&")) {
            playlistTitle = "&" + playlistTitle;
            Log.w("Playlist Contructor", "Playlist Title did not start with &, automatically fixed");
        }
        if (!owner.startsWith("@")) {
            owner = "@" + owner;
            Log.w("Playlist Contructor", "Playlist Owner(name) did not start with @, automatically fixed");
        }
        this.title = playlistTitle;
        this.videos = new ArrayList<>();
        this.playlistDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, Phasellus congue velit vel lacus blandit dignissim.";
        this.owner = owner;
        this.score=0;
        Log.i("Playlist: Contructor", "Created Playlist with:\nTitle: " + title + "\nDescription: " + playlistDescription + "\nOwner: " + owner);
    }

    public static Playlist Default() {
        return defaultPlaylist;
    }

    @Override
    public String toString() {
        return "Playlist\n Title: " + this.title + "\nDescription: " + this.playlistDescription + "\nOwner: " + this.owner;
    }
}
