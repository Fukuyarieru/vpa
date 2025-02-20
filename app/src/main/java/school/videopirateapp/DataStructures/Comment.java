package school.videopirateapp.DataStructures;

import static school.videopirateapp.Utilities.TimeNow;

import java.util.ArrayList;

public class Comment{

    private static final Comment defaultComment=new Comment();

    private String Comment;
    private String Author;
    private String Context;
    private String Date;
    private ArrayList<Byte> AuthorImage;
    // private ArrayList<Comment> Replies; --> TODO


    public ArrayList<Byte> getAuthorImage() {
        return AuthorImage;
    }

    public void setAuthorImage(ArrayList<Byte> authorImage) {
        AuthorImage = authorImage;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

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
        this.AuthorImage =new ArrayList<>();
    }
    public Comment(String comment, String author) {
        this(comment,author,"unset");
    }

    public Comment() {
        this("Lorem ipsum dolor sit amet, consectetur adipiscing elit","@Default","videos-defaultVideo-comments"); // User.Default().getName()
        //"videos/defaultVideo/comments"

    }
    public static Comment Default() {
        return defaultComment;
    }
}
