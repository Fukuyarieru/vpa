package school.videopirateapp;

import java.util.ArrayList;

public class CommentSection {
    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private ArrayList<Comment> comments;
    private String id;
}
