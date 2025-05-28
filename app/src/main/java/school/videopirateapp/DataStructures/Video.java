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
    private String Url;
    private Integer Score;

    public ArrayList<Byte> getThumbnail() {
        return Thumbnail;
    }
    public void setThumbnail(ArrayList<Byte> thumbnail) {
        Thumbnail = thumbnail;
    }
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
    public String getUrl() { return Url; }
    public void setUrl(String url) { Url = url; }
    public Integer getScore() {
        return Score;
    }
    public void setScore(Integer score) {
        Score = score;
    }

    @Deprecated
    public void addComment(Comment newComment) {
        // this function adds a comment to the video's comments section, also it changes the context of the comment to be the video's path
        if(!this.Comments.contains(newComment)) {
            this.Comments.add(newComment);
            this.Score+=10;
        } else {
            Log.w("Video: addComment","Video already contains same added comment, was not added again");
        }
    }

    @Deprecated
    public void upvote() {
        Log.i("Video: upvote","Upvoted video: " +this.getTitle());
        this.Upvotes++;
        this.Score+=15;
    }
    @Deprecated
    public void downvote() {
        Log.i("Video: downvote","Downvoted video: " +this.getTitle());
        this.Downvotes++;
        this.Score-=15;
    }
    @Deprecated
    public void view() {
        this.Views++;
        this.Score+=1;
    }


    public Video(String title,String Uploader, ArrayList<Comment> comments, Integer views, Integer upvotes, Integer downvotes, String uploadDate, ArrayList<Byte> thumbnail, String url) {
        // detailed constructor for any video
        this.Title=title;
        this.Uploader=Uploader;
        this.Comments=comments;
        this.Views=views;
        this.Upvotes=upvotes;
        this.Downvotes=downvotes;
        this.UploadDate=uploadDate;
        this.Thumbnail=thumbnail;
        this.Url=url;
        this.Score=0;

        // do not evaluate score in constructor
//        this.Score+=views*1;
        Log.i("Video: Constructor","Video created");
    }
    public Video(String Title, String Uploader) {
        // constructor for new videos
        this(Title,Uploader,new ArrayList<Comment>(),0,0,0,TimeNow(),new ArrayList<Byte>(),"");
        Log.i("Video: Constructor","New video created");
    }
    public Video()  {
        // constructor for default video
        this("defaultVideo", User.Default().getName(),new ArrayList<Comment>(),0,0,0,"NO-DATE-SET",new ArrayList<Byte>(),"");
        Log.i("Video: Constructor","Default video created");
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
