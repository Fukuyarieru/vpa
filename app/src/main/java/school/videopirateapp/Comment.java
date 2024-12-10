package school.videopirateapp;

import com.google.firebase.database.DatabaseReference;

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
//    public static DatabaseReference GetTree() {
//        Database.GetReference("Comments")
//    }
}
