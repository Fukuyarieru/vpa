package school.videopirateapp;

import java.util.ArrayList;


public class Video {
    String Title;
    User Uploader; // pointer
    ArrayList<Comment>Comments;
    Integer Views;
    Integer Upvotes;
    Integer Downvotes;
    //TODO: Video videodata; (add this to constructor later)

    public Video(String Title, User Uploader) {
        this.Title=Title;
        this.Uploader=Uploader;
        Comments=new ArrayList<Comment>();
        Views=0;
        Upvotes=0;
        Downvotes=0;
    }
}
