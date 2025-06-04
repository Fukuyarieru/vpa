package school.videopirateapp.DataStructures;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class Comment {
    private static final Comment defaultComment = new Comment();
    private String Comment;
    private String Author;
    private ArrayList<String> replyContexts;
    private String Timestamp;
    private Integer Upvotes;
    private Integer Downvotes;
    private String Context;

    public Comment() {
        this("Default Comment", "@Default", "defaultVideo");
    }
    public Comment(String comment, String author, String context) {
        this.Comment = comment;
        this.Author = author;
        this.replyContexts = new ArrayList<>();
        this.Timestamp = "NO-DATE-SET";
        this.Upvotes = 0;
        this.Downvotes = 0;
        this.Context =context;
    }

    public static Comment Default() {
        return defaultComment;
    }

//    public String getVideoTitle() {
//        return this.getComment().split("-")[1];
//    }

    public String getContext() {
        return Context+this.Comment;
    }

    public void setContext(String context) {
        this.Context = context;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public ArrayList<String> getReplyContexts() {
        return replyContexts;
    }

    public void setReplyContexts(ArrayList<String> replyContexts) {
        this.replyContexts = replyContexts;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public Integer getUpvotes() {
        return Upvotes;
    }

    public Integer getScore() {
        return this.Upvotes - this.Downvotes;
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

    public void addReply(String replyContext) {
        // reply.setParentCommentId(this.toString());  // Commented out
        this.replyContexts.add(replyContext);
        Log.i("Comment: addReply", "Added reply to comment: " + this.Comment);
    }


    public void upvote() {
        Log.i("Comment: upvote", "Upvoted comment by: " + this.Author);
        this.Upvotes++;
    }

    public void downvote() {
        Log.i("Comment: downvote", "Downvoted comment by: " + this.Author);
        this.Downvotes++;
    }

    @Override
    public String toString() {
        return "Comment{" + "text='" + Comment + '\'' + ", author='" + Author + '\'' +
                // ", context='" + Context + '\'' +  // Commented out
                ", timestamp=" + Timestamp + ", upvotes=" + Upvotes + ", downvotes=" + Downvotes + '}';
    }
}
