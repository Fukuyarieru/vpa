package school.videopirateapp.DataStructures;

import static school.videopirateapp.Utilities.getDefaultPlaylistImage;

import android.util.Log;

import java.util.ArrayList;

public class Playlist {
   private static final Playlist defaultPlaylist = new Playlist();


   // TODO, will probably need to change this to <String,String> as i want to store the names, and fetch them from database locally to the related function
   private ArrayList<String> Videos;
   private String Title; // Playlist names will start with a '#', just because I wanted
   private String Owner;
   private String Description;
   private ArrayList<Byte> Image;
   private Integer Score;
   private Integer Upvotes;
   private Integer Downvotes;


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
      this.Title = playlistTitle;
      this.Videos = new ArrayList<>();
      this.Description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, Phasellus congue velit vel lacus blandit dignissim.";
      this.Owner = owner;
      this.Score = 0;
      this.Image = getDefaultPlaylistImage();
      Log.i("Playlist: Constructor", "Created Playlist with:\nTitle: " + Title + "\nDescription: " + Description + "\nOwner: " + owner);
   }

   public static Playlist Default() {
      return defaultPlaylist;
   }

   public ArrayList<Byte> getImage() {
      return Image;
   }

   public void setImage(ArrayList<Byte> image) {
      this.Image = image;
   }

   public ArrayList<String> getVideos() {
      return Videos;
   }

   public void setVideos(ArrayList<String> videos) {
      this.Videos = videos;
   }

   public String getTitle() {
      return Title;
   }

   public void setTitle(String title) {
      this.Title = title;
   }

   public String getOwner() {
      return Owner;
   }

   public void setOwner(String owner) {
      this.Owner = owner;
   }

   public String getDescription() {
      return Description;
   }

   public void setDescription(String description) {
      this.Description = description;
   }

   public Integer getScore() {
      return Score;
   }

   public void setScore(Integer score) {
      this.Score = score;
   }

   public Integer getUpvotes() {
      return Upvotes;
   }

   public void setUpvotes(Integer upvotes) {
      this.Upvotes = upvotes;
   }


   // TODO figure out how to work showing the videos in the database

   public Integer getDownvotes() {
      return Downvotes;
   }

   public void setDownvotes(Integer downvotes) {
      this.Downvotes = downvotes;
   }

   public void addVideo(Video newVideo) {
      String videoTitle = newVideo.getTitle();
      this.Videos.add(videoTitle); // todo, this looks ugly, check laters
   }

   @Override
   public String toString() {
      return "Playlist\n Title: " + this.Title + "\nDescription: " + this.Description + "\nOwner: " + this.Owner;
   }
}
