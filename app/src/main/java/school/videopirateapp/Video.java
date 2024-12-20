package school.videopirateapp;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;


public class Video {
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
    public HashMap<String,String> ToHashMap() {
        HashMap<String,String>videoHashMap=new HashMap<>();
        videoHashMap.put("title", this.title);
        videoHashMap.put("uploader",this.Uploader.name);
//        videoHashMap.put("comments", this.Comments.forEach(comment -> comment.ToHashMap()));
        videoHashMap.put("comments",this.Comments.toString());
        videoHashMap.put("views",this.Views.toString());
        videoHashMap.put("upvotes",this.Upvotes.toString());
        videoHashMap.put("downvotes",this.Downvotes.toString());
        return videoHashMap;
    }
    public void addComment(Comment comment) {
        comment.id=this.commentCounter;
        this.Comments.add(comment);
//        this.initializeLastComment();
        this.initializeComments();
    }
//    public void addComment(User user)
    public static Video Default() {
        Video default_video=new Video("DefaultVideo",User.Default());
        default_video.addComment(Comment.Default());
        return default_video;
    }
    public static String GetTreePath() {
        return "videos/";
    }
    public static String GetVideoPath(String videoTitle) {
        return Video.GetTreePath()+videoTitle+"/";
    }
    @Override
    public String toString(){
        return this.title;
    }
//    public HashMap<String,HashMap<String,String>> commentsToHashMap() {
//        HashMap<String,HashMap<String,String>>commentsHashMap=new HashMap<>();
////        this.Comments.forEach(comment -> comment.ToHashMap());
//        for(int i=0;i<this.Comments.size();i++) {
////            commentsHashMap.put(new HashMap<String,String>());
//        }
//    }
    public void initializeComments() {
//        DatabaseReference ref=Database.GetRef(this.getPath()+"comments/");
        for(int i=0;i<this.Comments.size();i++) {
            DatabaseReference commentRef = Database.GetRef(this.getPath()+"comments/"+this.Comments.get(i).id);
            commentRef.setValue(this.Comments.get(i).ToHashMap());
        }
    }
    public void initializeLastComment() {
        Comment lastComment=this.Comments.get(this.Comments.size()-1);
        Database.GetRef(this.getPath()+"comments/"+lastComment.id+"/").setValue(lastComment.ToHashMap());
    }
    public String getPath() {
        return Video.GetTreePath()+this.title+"/";
    }

}
