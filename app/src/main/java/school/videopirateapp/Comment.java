package school.videopirateapp;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class Comment{
    private static Comment defaultComment=new Comment();
    private String Comment;
    private String Author;

    private String Context;
    // private ArrayList<Comment> Replies;
    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getAuthor() {
        return Author;
    }
public Comment(String Comment,String author, String context) {
        this.Comment=Comment;
        this.Author=author;
        this.Context=context;
}
    public Comment(String comment, String author) {
        this(comment,author,"unset");
//        this.id=id;
//        this.video=video;
    }

    public Comment() {
        this("Lorem ipsum dolor sit amet, consectetur adipiscing elit","@Default","videos/defaultVideo/comments"); // User.Default().getName()
    }
    public static Comment Default() {
        return defaultComment;
    }
}
