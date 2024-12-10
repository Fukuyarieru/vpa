package school.videopirateapp;

public class Comment extends DatabaseAccesser {
    String Comment;
    User Author;
    
    
    public Comment(User author, String comment) {
        this.Comment=comment;
        this.Author=author;
    }
    // Default for empty case
    public Comment() {
        this.Comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
        this.Author = User.Get(User.DefaultUser());
    }
    public void Add(Comment newComment) {

    }
    
}
