package school.videopirateapp;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class Comment{
    private static Comment defaultComment=new Comment();
    private String Comment;
    private String Author;
//    private Integer id; // must have an ID field because comment dont have distinct features like names or titles, a proper ID will need to be implemented
//    private String video;

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getAuthorName() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

//    public Integer getId() {
//        return id;
//    }

//    public void setId(Integer id) {
//        this.id = id;
//    }
//    public String getVideo() {
//        return video;
//    }
//
//    public void setVideo(String video) {
//        this.video = video;
//    }

    public String getAuthor() {
        return Author;
    }

    public Comment(String comment, String author) {
        this.Comment=comment;
        this.Author=author;
//        this.id=id;
//        this.video=video;
    }

    public Comment() {
        this("Lorem ipsum dolor sit amet, consectetur adipiscing elit","@Default"); // User.Default().getName()
    }
    public static Comment Default() {
        return defaultComment;
    }
}
