package school.videopirateapp;

import java.util.ArrayList;

public class Comments {
    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    ArrayList<Comment> comments;
    public Comments() {
        this.comments=new ArrayList<Comment>();
    }
    public void Add(Comment newComment) {
        this.comments.add(newComment);
    }

}
