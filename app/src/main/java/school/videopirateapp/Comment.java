package school.videopirateapp;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class Comment{
    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public User getAuthor() {
        return Author;
    }

    public void setAuthor(User author) {
        Author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getAuthorName() {
        return this.Author.getName();
    }

    String Comment;
    User Author;
    Integer id; // must have an ID field because comment dont have distinct features like names or titles, a proper ID will need to be implemented
    
    
    public Comment(User author, String comment, Integer id) {
        this.Comment=comment;
        this.Author=author;
        this.id=id;
    }
    // Default for empty case
    public Comment() {
        this.Comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
        this.Author = User.Default();
        this.id=0;
    }
//    public HashMap<String,String> ToHashMap() {
//        HashMap<String,String>commentHashMap=new HashMap<>();
//        commentHashMap.put("uploader", this.Author.toString()); (note later after commenting this function) // there wasnt a toString() initially, why did i do this?
//        commentHashMap.put("comment", Comment);
//        return commentHashMap;
//    }
    public Comment(Integer id) { // create a default comment, but with a set id
//        this=new Comment(User.Default(),"Lorem ipsum dolor sit amet, consectetur adipiscing elit.",id);
        this.Comment="Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
        this.Author=User.Default();
        this.id=id;
    }
    public static Comment Default() {
        return new Comment();
    }
//    public HashMap<String,String> ToHashMap() {
//        HashMap<String,String>commentHashMap=new HashMap<String,String>();
//        commentHashMap.put("comment",this.Comment);
//        commentHashMap.put("author",this.Author.name);
//        return commentHashMap;
//    }
    @Override
    public String toString() {
        return this.Comment;
    }
}
