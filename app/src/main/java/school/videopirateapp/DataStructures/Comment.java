package school.videopirateapp.DataStructures;

import static school.videopirateapp.Utilities.TimeNow;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Comment{

    private static final Comment defaultComment=new Comment();

    private String Comment;
    private String Author;
    private String Context;
    private String Date;
    private byte[] Image;
    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

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
        this.Date=TimeNow();
        this.Image=new byte[]{};
    }
    public Comment(String comment, String author) {
        this(comment,author,"unset");
    }

    public Comment() {
        this("Lorem ipsum dolor sit amet, consectetur adipiscing elit","@Default","videos/defaultVideo/comments"); // User.Default().getName()

    }
    public static Comment Default() {
        return defaultComment;
    }
}
