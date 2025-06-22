package school.videopirateapp.datastructures;

import static school.videopirateapp.Utilities.getDefaultPlaylistImage;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Playlist {
   private static final Playlist defaultPlaylist = new Playlist();

   // TODO, will probably need to change this to <String,String> as i want to store the names, and fetch them from database locally to the related function
   private HashMap<String, String> videos;
   private String title; // Playlist names will start with a '#', just because I wanted
   private String owner;
   private String description;
   private ArrayList<Byte> image;
   private Integer score;
   private Integer upvotes;
   private Integer downvotes;
   private Integer views;


   public Playlist() {
      // TODO, here is a bug, @Default is associated as a video
      this("&defaultPlaylist", "@Default");
   }

   public Playlist(String playlistTitle, String owner) {
      // no need to check database here, because database checks itself
      if (!playlistTitle.startsWith("&")) {
         playlistTitle = "&" + playlistTitle;
         Log.w("Playlist Constructor", "Playlist Title did not start with &, automatically fixed");
      }
      if (!owner.startsWith("@")) {
         owner = "@" + owner;
         Log.w("Playlist Constructor", "Playlist Owner(name) did not start with @, automatically fixed");
      }
      this.title = playlistTitle;
      this.videos = new ArrayList<>();
      this.description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, Phasellus congue velit vel lacus blandit dignissim.";
      this.owner = owner;
      this.score = 0;
      this.upvotes = 0;
      this.downvotes = 0;
      this.views = 0;
      this.image = getDefaultPlaylistImage();
//      Log.i("Playlist: Constructor", "Created Playlist with:\nTitle: " + Title + "\nDescription: " + Description + "\nOwner: " + owner);
   }

   public static Playlist defaultPlaylist() {
      return defaultPlaylist;
   }

   public ArrayList<Byte> getImage() {
      return image;
   }

   public void setImage(ArrayList<Byte> image) {
      this.image = image;
   }

   public Map<String,String> getVideos() {
      return videos;
   }

   public void setVideos(Map<String,String> videos) {
      this.videos = (HashMap<String, String>) videos;
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

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Integer getScore() {
      return score;
   }

   public void setScore(Integer score) {
      this.score = score;
   }

   public Integer getVoteScore() {
      return this.upvotes - this.downvotes;
   }

   public Integer getUpvotes() {
      return upvotes;
   }

   public void setUpvotes(Integer upvotes) {
      this.upvotes = upvotes;
   }


   // TODO figure out how to work showing the videos in the database

   public Integer getDownvotes() {
      return downvotes;
   }

   public void setDownvotes(Integer downvotes) {
      this.downvotes = downvotes;
   }

   public Integer getViews() {
      return views;
   }

   public void setViews(Integer views) {
      this.views = views;
   }

   public void addVideo(String videoTitle) {
      this.videos.put(videoTitle,videoTitle);
   }

   public void removeVideo(String videoTitle) {
      this.videos.remove(videoTitle);
   }

   @Override
   public String toString() {
      return "Playlist\n Title: " + this.title + "\nDescription: " + this.description + "\nOwner: " + this.owner;
   }
}
