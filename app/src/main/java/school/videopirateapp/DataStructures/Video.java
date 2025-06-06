package school.videopirateapp.DataStructures;

import static school.videopirateapp.Utilities.TimeNow;
import static school.videopirateapp.Utilities.getDefaultVideoImage;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import school.videopirateapp.Database.Database;
import school.videopirateapp.GlobalVariables;

public class Video {

   private static final Video defaultVideo = new Video();

   private String Title;
   private String Uploader;
   private ArrayList<String> commentContextes;
   private Integer Views;
   private Integer Upvotes;
   private Integer Downvotes;
   private String UploadDate;
   private ArrayList<Byte> Image;
   private String Url;
   private Integer Score;

   public Video(String Title, String Uploader) {
      // constructor for new videos
      this.Title = Title;
      this.Uploader = Uploader;
      this.commentContextes = new ArrayList<>();
      this.Views = 0;
      this.Upvotes = 0;
      this.Downvotes = 0;
      this.UploadDate = TimeNow();
      this.Image = getDefaultVideoImage();
      this.Url = "";
      this.Score = 0;

//        this(Title,Uploader,new ArrayList<Comment>(),0,0,0,TimeNow(),new ArrayList<Byte>(),"");
      Log.i("Video: Constructor", "New video created");
   }

   public Video() {
      // constructor for default video, mainly firebase use
      this.Title = "defaultVideo";
      this.Uploader = User.Default().getName();
      this.commentContextes = new ArrayList<>();
      this.Views = 0;
      this.Upvotes = 0;
      this.Downvotes = 0;
      this.UploadDate = "NO-DATE-SET";
      this.Image = getDefaultVideoImage();
      this.Url = "";
      this.Score = 0;

//        this("defaultVideo", User.Default().getName(),new ArrayList<Comment>(),0,0,0,"NO-DATE-SET",new ArrayList<Byte>(),"");
      Log.i("Video: Constructor", "Default video created");
   }

   public static Video Default() {
      return defaultVideo;
   }

   public ArrayList<Byte> getImage() {
      return Image;
   }

   public void setImage(ArrayList<Byte> image) {
      Image = image;
   }

   public String getUploadDate() {
      return UploadDate;
   }

   public void setUploadDate(String uploadDate) {
      UploadDate = uploadDate;
   }

   public String getTitle() {
      return Title;
   }

   public void setTitle(String title) {
      this.Title = title;
   }

   public String getUploader() {
      return Uploader;
   }

   public void setUploader(String uploader) {
      Uploader = uploader;
   }

   public ArrayList<String> getCommentContextes() {
      return commentContextes;
   }

   public void setCommentContextes(ArrayList<String> commentContextes) {
      this.commentContextes = commentContextes;
   }

   public Integer getViews() {
      return Views;
   }

   public void setViews(Integer views) {
      Views = views;
   }

   public Integer getUpvotes() {
      return Upvotes;
   }

   public void setUpvotes(Integer upvotes) {
      Upvotes = upvotes;
   }

   public Integer getDownvotes() {
      return Downvotes;
   }

   public void setDownvotes(Integer downvotes) {
      Downvotes = downvotes;
   }

   public String getUrl() {
      return Url;
   }

   public void setUrl(String url) {
      Url = url;
   }

   public Integer getScore() {
      return Score;
   }

   public void setScore(Integer score) {
      Score = score;
   }

   public Integer getVoteScore() {
      return this.Upvotes - this.Downvotes;
   }

   @Deprecated
   public void Upvote() {
      Log.i("Video: upvote", "Upvoted video: " + this.getTitle());
      this.Upvotes++;
      this.Score = this.Upvotes - this.Downvotes;
   }

   @Deprecated
   public void Downvote() {
      Log.i("Video: downvote", "Downvoted video: " + this.getTitle());
      this.Downvotes++;
      this.Score = this.Upvotes - this.Downvotes;
   }

   @Deprecated
   public void View() {
      this.Views++;
   }

   public String getContext() {
      return "videos-" + this.Title;
   }
   public String getCommentsContext() {
      return this.getContext()+"-comments";
   }

   public void addCommentContext(String commentContext) {
      if (!this.commentContextes.contains(commentContext)) {
         this.commentContextes.add(commentContext);
         Log.i("Video: addCommentContext", "Added comment context: " + commentContext);
      }
   }

   public void removeCommentContext(String commentContext) {
      if (this.commentContextes.remove(commentContext)) {
         Log.i("Video: removeCommentContext", "Removed comment context: " + commentContext);
      }
   }

   @NonNull
   @Override
   public String toString() {
      return "Video\nTitle: " + this.getTitle() + "\nUploader: " + this.getUploader();
   }
}
