package school.videopirateapp;

import java.util.ArrayList;


public class Video {

    private String Title;
    private String Uploader; // pointer
    private ArrayList<Comment>Comments;
    private Integer Views;
    private Integer Upvotes;
    private Integer Downvotes;
    //TODO: VideoView videodata; (add this to constructor later)

    private static Video defaultVideo=new Video();

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getUploader() {
        return Uploader;
    }

    public void setUploader(String uploader) {
        Uploader = uploader;
    }

    public ArrayList<Comment> getComments() {
        return Comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        Comments = comments;
    }

    public Integer getViews() {
        return Views;
    }

    public void setViews(Integer views) {
        Views = views;
    }

    public Integer getUpvotes() {
        return Upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        Upvotes = upvotes;
    }

    public Integer getDownvotes() {
        return Downvotes;
    }

    public void setDownvotes(Integer downvotes) {
        Downvotes = downvotes;
    }
    public void addComment(Comment newComment) {
        newComment.setContext("videos/"+this.Title+"/comments");
        this.Comments.add(newComment);
    }
    public Video(String Title, String Uploader) {
        this.Title =Title;
        this.Uploader=Uploader;
        this.Comments=new ArrayList<Comment>();
        this.Views=0;
        this.Upvotes=0;
        this.Downvotes=0;
    }
    public Video()  {
        this("defaultVideo",User.Default().getName());
    }
    public static Video Default() {
        return defaultVideo;
    }
}
