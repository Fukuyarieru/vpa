package school.videopirateapp.DataStructures;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class Comment {
    private static final Comment defaultComment = new Comment();
    private String Comment;
    private String Author;
    private String Context;
    private ArrayList<Comment> Replies;
    private Date Timestamp;
    private Integer Upvotes;
    private Integer Downvotes;
    private String ParentCommentId; // For replies
    private Integer Score;

    public Comment() {
        this("Default Comment", "@Default", "default-context");
    }

    public Comment(String comment, String author, String context) {
        this.Comment = comment;
        this.Author = author;
        this.Context = context;
        this.Replies = new ArrayList<>();
        this.Timestamp = new Date();
        this.Upvotes = 0;
        this.Downvotes = 0;
        this.ParentCommentId = null;
        this.Score = 0;
    }

    public static Comment Default() {
        return defaultComment;
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

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
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

    public String getParentCommentId() {
        return ParentCommentId;
    }

    public void setParentCommentId(String parentCommentId) {
        ParentCommentId = parentCommentId;
    }

    public void addReply(Comment reply) {
        reply.setParentCommentId(this.toString());
        this.Replies.add(reply);
        Log.i("Comment: addReply", "Added reply to comment: " + this.Comment);
    }

    public Integer getScore() {
        return this.Upvotes - this.Downvotes;
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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Comment other = (Comment) obj;
        return Comment.equals(other.Comment) && 
               Author.equals(other.Author) && 
               Context.equals(other.Context) &&
               Timestamp.equals(other.Timestamp);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + Comment + '\'' +
                ", author='" + Author + '\'' +
                ", context='" + Context + '\'' +
                ", timestamp=" + Timestamp +
                ", upvotes=" + Upvotes +
                ", downvotes=" + Downvotes +
                '}';
    }
}
