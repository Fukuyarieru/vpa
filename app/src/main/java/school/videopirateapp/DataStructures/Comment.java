package school.videopirateapp.DataStructures;

import static school.videopirateapp.Utilities.TimeNow;

import android.util.Log;

import java.util.ArrayList;

public class Comment {
    private static final Comment defaultComment = new Comment();
    private String Comment;
    private String Author;
    private ArrayList<String> Comments; // reply contexts
    private String Date;
    private Integer Upvotes;
    private Integer Downvotes;
    private String Context;

    public Comment() {
        this("Default Comment", "@Default", "videos-defaultVideo-comments");
    }

    public Comment(String comment, String author, String context) {
        this.Comment = comment;
        this.Author = author;
        this.Comments = new ArrayList<>();
        this.Date = TimeNow();
        this.Upvotes = 0;
        this.Downvotes = 0;
        this.Context = context;
    }

    public static Comment Default() {
        return defaultComment;
    }

    public String getContext() {
        return this.getSourceContext() + "-" + this.getComment();
    }
    public String getSourceContext() {
        return Context;
    }

    public void setContext(String context) {
        this.Context = context;
    }

    public String getCommentsContext() {
        return this.getContext() + "-comments";
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

    public ArrayList<String> getComments() {
        return Comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.Comments = comments;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public Integer getUpvotes() {
        return Upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        Upvotes = upvotes;
    }

    public Integer getScore() {
        return this.Upvotes - this.Downvotes;
    }

    public Integer getDownvotes() {
        return Downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        Downvotes = downvotes;
    }

    public void addReply(String replyContext) {
        // reply.setParentCommentId(this.toString());  // Commented out
        this.Comments.add(replyContext);
        Log.i("Comment: addReply", "Added reply to comment: " + this.Comment);
    }


    @Deprecated
    public void upvote() {
        Log.i("Comment: upvote", "Upvoted comment by: " + this.Author);
        this.Upvotes++;
    }

    @Deprecated
    public void downvote() {
        Log.i("Comment: downvote", "Downvoted comment by: " + this.Author);
        this.Downvotes++;
    }

    @Override
    public String toString() {
        return "Comment{" + "text='" + Comment + '\'' + ", author='" + Author + '\'' +
                // ", context='" + Context + '\'' +  // Commented out
                ", timestamp=" + Date + ", upvotes=" + Upvotes + ", downvotes=" + Downvotes + '}';
    }
}
