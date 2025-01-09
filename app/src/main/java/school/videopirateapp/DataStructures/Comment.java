package school.videopirateapp.DataStructures;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Comment{

    private static final Comment defaultComment=new Comment();

    private String Comment;
    private String Author;
    private String Context;
    private String Date;

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
        // TODO, make this later like my own code and not some stack overflow solution
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String datetime = dateformat.format(c.getTime());
        this.Date= datetime;
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
