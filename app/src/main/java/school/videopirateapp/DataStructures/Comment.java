package school.videopirateapp.DataStructures;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class Comment {
    private static final Comment defaultComment = new Comment();
    private String Comment;
    private String Author;
    // private String Context;  // Commented out to disable comment-to-comment functionality
    private ArrayList<Comment> Replies;
    private Date Timestamp;
    private Integer Upvotes;
    private Integer Downvotes;
    private String Context;
    private Integer Score;

    public Comment() {
        this("Default Comment", "@Default", "defaultVideo");
    }
    public Comment(String comment, String author, String video) {
        this.Comment = comment;
        this.Author = author;
        // this.Context = context;  // Commented out
        this.Replies = new ArrayList<>();
        this.Timestamp = new Date();
        this.Upvotes = 0;
        this.Downvotes = 0;
        this.Score = 0;
        this.Context =video;
    }

    public static Comment Default() {
        return defaultComment;
    }

    public String getVideoTitle() {
        return this.getComment().split("-")[1];
    }

    public String getContext() {
        return Context;
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

    public ArrayList<Comment> getReplies() {
        return Replies;
    }

    public void setReplies(ArrayList<Comment> replies) {
        Replies = replies;
    }

    public Date getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Date timestamp) {
        Timestamp = timestamp;
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

    public void addReply(Comment reply) {
        // reply.setParentCommentId(this.toString());  // Commented out
        this.Replies.add(reply);
        Log.i("Comment: addReply", "Added reply to comment: " + this.Comment);
    }

    public Integer getScore() {
        return this.Upvotes - this.Downvotes;
    }

    public void setScore(Integer score) {
        Score = score;
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
