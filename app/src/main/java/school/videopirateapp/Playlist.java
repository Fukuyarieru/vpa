package school.videopirateapp;

import java.util.ArrayList;
import java.util.HashMap;

public class Playlist {
    ArrayList<Video> videos;
    String title; // Playlist names will start with a '#', just because I wanted
    User owner;
    String playlistDescription;


    public Playlist() {
        this.videos =new ArrayList<Video>();
        this.title ="#Default";
        this.owner =User.Default();

        // gonna check with database

//        // TODO, fix the below
        // TODO 2, FIX THE BELOW
        if(!User.Default().isOwningPlaylist(this)) { // strange piece of code, maybe impossible or bug inducing // this code is bad because "this" is contextual and since its a pointer and doesnt really check by the name there will be multilpe instances of #Default, this will cause bugs
            User.Default().addPlayListOwnership(this);
        }
        this.addVideo(Video.Default());
        this.playlistDescription="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus congue velit vel lacus blandit dignissim.";
    }
    public Playlist(String playListName, User owner){
        // no need to check database here, because database checks itself
        if(!playListName.startsWith("#")) {
            title ="#"+playListName;
        }
        this.title =playListName;
        this.videos =new ArrayList<Video>();
        this.playlistDescription="";
        this.owner=owner;
//        if( !owner.isOwningPlaylist(this)) { // TODO, another bug of the same type as before, "this" indacates a pointer to a new playlist that hasnt been checked yet
//            owner.addPlayListOwnership(this);
//        }
    }
    public static String GetTreePath() {
        return "playlists/";
    }
    public void addVideo(Video addedVideo) {
        this.videos.add(addedVideo);
    }
    public String getPath() {
        return Playlist.GetTreePath()+this.title +"/";
    }
    public HashMap<String,String> ToHashMap() {
        HashMap<String,String>playListHashMap=new HashMap<String,String>();
        playListHashMap.put("title",this.title);
        playListHashMap.put("owner",this.owner.name);
        playListHashMap.put("description",this.playlistDescription);
        playListHashMap.put("videos",videos.toString());
        return playListHashMap;
    }
    public static Playlist Default() {
        return new Playlist();
    }
    @Override
    public String toString() {
        return "title:" + this.title +"/"+this.videos.toString();
    }
}
