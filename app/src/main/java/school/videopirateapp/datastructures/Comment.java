package school.videopirateapp.datastructures;

import static school.videopirateapp.Utilities.TimeNow;

import android.util.Log;

import androidx.annotation.OptIn;
import androidx.annotation.RequiresPermission;

import java.util.ArrayList;

public class Comment {
   private static final Comment defaultComment = new Comment();
   private String text;
   private String author;
   private ArrayList<String> comments; // reply contexts
   private String date;
   private Integer upvotes;
   private Integer downvotes;
   private String source;

   public Comment() {
      this("Default Comment", "@Default", "videos-defaultVideo-comments");
   }

   public Comment(String text, String author, String source) {
      this.text = text;
      this.author = author;
      this.comments = new ArrayList<>();
      this.date = TimeNow();
      this.upvotes = 0;
      this.downvotes = 0;
      this.source = source;
   }

   public static Comment defaultComment() {
      return defaultComment;
   }

   public String getContext() {
      return this.getSource() + "-" + this.getText();
   }

   public String getSource() {
      return source;
   }

   public void setSource(String source) {
      this.source = source;
   }

   public String getCommentsContext() {
      return this.getContext() + "-comments";
   }

   public String getText() {
      return text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public String getAuthor() {
      return author;
   }

   public void setAuthor(String author) {
      this.author = author;
   }

   public ArrayList<String> getComments() {
      return comments;
   }

   public void setComments(ArrayList<String> comments) {
      this.comments = comments;
   }

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public Integer getUpvotes() {
      return upvotes;
   }

   public void setUpvotes(Integer upvotes) {
      this.upvotes = upvotes;
   }

   public Integer getScore() {
      return this.upvotes - this.downvotes;
   }

   public Integer getDownvotes() {
      return downvotes;
   }

   public void setDownvotes(Integer downvotes) {
      this.downvotes = downvotes;
   }

   public void addReply(String replyContext) {
      // reply.setParentCommentId(this.toString());  // Commented out
      this.comments.add(replyContext);
      Log.i("Comment: addReply", "Added reply to comment: " + this.text);
   }


   @Deprecated
   public void upvote() {
      Log.i("Comment: upvote", "Upvoted comment by: " + this.author);
      this.upvotes++;
   }

   @Deprecated
   public void downvote() {
      Log.i("Comment: downvote", "Downvoted comment by: " + this.author);
      this.downvotes++;
   }

   @Override
   public String toString() {
      return "Comment{" + "text='" + text + '\'' + ", author='" + author + '\'' +
              // ", context='" + Context + '\'' +  // Commented out
              ", timestamp=" + date + ", upvotes=" + upvotes + ", downvotes=" + downvotes + '}';
   }
}
