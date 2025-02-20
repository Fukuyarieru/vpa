package school.videopirateapp.DataStructures;

import static school.videopirateapp.Utilities.TimeNow;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Video {

    private static final Video defaultVideo=new Video();

    private String Title;
    private String Uploader;
    private ArrayList<Comment>Comments;
    private Integer Views;
    private Integer Upvotes;
    private Integer Downvotes;
    private String UploadDate;
    private ArrayList<Byte> Thumbnail;

    public ArrayList<Byte> getThumbnail() {
        return Thumbnail;
    }
    public void setThumbnail(ArrayList<Byte> thumbnail) {
        Thumbnail = thumbnail;
    }
    //TODO: VideoView videodata; (add this to constructor later)
    public String getUploadDate() {
        return UploadDate;
    }
    public void setUploadDate(String uploadDate) {
        UploadDate = uploadDate;
    }
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
        // this function adds a comment to the video's comments section, also it changes the context of the comment to be the video's path
        if(!this.Comments.contains(newComment)) {
            this.Comments.add(newComment);
            Log.w("Video: addComment","Video already contains added comment, not added");
        }
    }
    public Video(String Title, String Uploader) {
        this.Title =Title;
        this.Uploader=Uploader;
        this.Comments=new ArrayList<Comment>();
        this.Views=0;
        this.Upvotes=0;
        this.Downvotes=0;
        this.UploadDate= TimeNow();
    }
    public Video()  {
        this("defaultVideo", User.Default().getName());
    }

    public static Video Default() {
        return defaultVideo;
    }

    public String Context() {
        return "videos-"+this.Title+"-comments";
    }
    @NonNull
    @Override
    public String toString(){
        return "Video\nTitle: "+this.getTitle()+"\nUploader: "+this.getUploader();
    }
}
