package school.videopirateapp.datastructures;

import static school.videopirateapp.Utilities.TimeNow;
import static school.videopirateapp.Utilities.getDefaultVideoImage;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Video {

   private static final Video defaultVideo = new Video();

   private String title;
   private String uploader;
   private ArrayList<String> comments;
   private Integer views;
   private Integer upvotes;
   private Integer downvotes;
   private String uploadDate;
   private ArrayList<Byte> image;
   private String url;
   private Integer score;

   public Video(String title, String uploader) {
      // constructor for new videos
      this.title = title;
      this.uploader = uploader;
      this.comments = new ArrayList<>();
      this.views = 0;
      this.upvotes = 0;
      this.downvotes = 0;
      this.uploadDate = TimeNow();
      this.image = getDefaultVideoImage();
      this.url = "";
      this.score = 0;

      Log.i("Video: Constructor", "New video created");
   }

   public Video() {
      // constructor for default video, mainly firebase use
      this.title = "defaultVideo";
      this.uploader = User.defaultUser().getName();
      this.comments = new ArrayList<>();
      this.views = 0;
      this.upvotes = 0;
      this.downvotes = 0;
      this.uploadDate = "NO-DATE-SET";
      this.image = getDefaultVideoImage();
      this.url = "";
      this.score = 0;

      Log.i("Video: Constructor", "Default video created");
   }

   public static Video defaultVideo() {
      return defaultVideo;
   }

   public ArrayList<Byte> getImage() {
      return image;
   }

   public void setImage(ArrayList<Byte> image) {
      this.image = image;
   }

   public String getUploadDate() {
      return uploadDate;
   }

   public void setUploadDate(String uploadDate) {
      this.uploadDate = uploadDate;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getUploader() {
      return uploader;
   }

   public void setUploader(String uploader) {
      this.uploader = uploader;
   }

   public ArrayList<String> getComments() {
      return comments;
   }

   public void setComments(ArrayList<String> comments) {
      this.comments = comments;
   }

   public Integer getViews() {
      return views;
   }

   public void setViews(Integer views) {
      this.views = views;
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

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
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

   @Deprecated
   public void upvote() {
      Log.i("Video: upvote", "Upvoted video: " + this.getTitle());
      this.upvotes++;
      this.score = this.upvotes - this.downvotes;
   }

   @Deprecated
   public void downvote() {
      Log.i("Video: downvote", "Downvoted video: " + this.getTitle());
      this.downvotes++;
      this.score = this.upvotes - this.downvotes;
   }

   @Deprecated
   public void view() {
      this.views++;
   }

   public String getContext() {
      return "videos-" + this.title;
   }
   public String getCommentsContext() {
      return this.getContext()+"-comments";
   }

   public void addCommentContext(String commentContext) {
      if (!this.comments.contains(commentContext)) {
         this.comments.add(commentContext);
         Log.i("Video: addCommentContext", "Added comment context: " + commentContext);
      }
   }

   public void removeCommentContext(String commentContext) {
      if (this.comments.remove(commentContext)) {
         Log.i("Video: removeCommentContext", "Removed comment context: " + commentContext);
      }
   }

   @NonNull
   @Override
   public String toString() {
      return "Video\nTitle: " + this.getTitle() + "\nUploader: " + this.getUploader()+"\nComments"+this.getComments().toString();
   }
}
