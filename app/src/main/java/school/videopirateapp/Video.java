package school.videopirateapp;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;


public class Video {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUploader() {
        return Uploader;
    }

    public void setUploader(User uploader) {
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
    public String getUploaderName() {
        return this.Uploader.getName();
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

    public Integer getCommentCounter() {
        return commentCounter;
    }

    public void setCommentCounter(Integer commentCounter) {
        this.commentCounter = commentCounter;
    }

    String title;
    User Uploader; // pointer
    ArrayList<Comment>Comments;
    Integer Views;
    Integer Upvotes;
    Integer Downvotes;
    Integer commentCounter=0;
    //TODO: VideoView videodata; (add this to constructor later)

    public Video(String Title, User Uploader) {
        this.title =Title;
        this.Uploader=Uploader;
        Comments=new ArrayList<Comment>();
        Views=0;
        Upvotes=0;
        Downvotes=0;
    }
    public Video()  {
        this("defaultTitle",User.Default());
    }
    public void addComment(Comment newComment) {
        newComment.setId(commentCounter);
        this.Comments.add(newComment);
        this.commentCounter+=1;
    }
//    public HashMap<String,String> ToHashMap() {
//        HashMap<String,String>videoHashMap=new HashMap<>();
//        videoHashMap.put("title", this.title);
//        videoHashMap.put("uploader",this.Uploader.name);
////        videoHashMap.put("comments", this.Comments.forEach(comment -> comment.ToHashMap()));
//        videoHashMap.put("comments",this.Comments.toString());
//        videoHashMap.put("views",this.Views.toString());
//        videoHashMap.put("upvotes",this.Upvotes.toString());
//        videoHashMap.put("downvotes",this.Downvotes.toString());
//        return videoHashMap;
//    }
    public static Video Default() {
        Video default_video=new Video("DefaultVideo",User.Default());
        default_video.Comments.add(Comment.Default());
        return default_video;
    }
    public String getPath() {
        return "videos/" + this.title+"/";
    }
//    public HashMap<String,HashMap<String,String>> commentsToHashMap() {
//        HashMap<String,HashMap<String,String>>commentsHashMap=new HashMap<>();
////        this.Comments.forEach(comment -> comment.ToHashMap());
//        for(int i=0;i<this.Comments.size();i++) {
////            commentsHashMap.put(new HashMap<String,String>());
//        }
//    }
}
