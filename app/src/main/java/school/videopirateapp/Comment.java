package school.videopirateapp;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class Comment{
    String Comment;
    User Author;
    
    
    public Comment(User author, String comment) {
        this.Comment=comment;
        this.Author=author;
    }
    // Default for empty case
    public Comment() {
        this.Comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
        this.Author = User.Default();
    }
    public static DatabaseReference GetTreeRef() {
        return Database.GetRef("comments");
    }
    public static String GetTreePath() {
        // is this necessary?
        return "comments/";
    }
    public HashMap<String,String> ToHashMap() {
        HashMap<String,String>commentHashMap=new HashMap<>();
        commentHashMap.put("uploader", this.Author.toString());
        commentHashMap.put("comment", Comment);
        return commentHashMap;
    }
    public static Comment Default() {
        return new Comment();
    }
}
