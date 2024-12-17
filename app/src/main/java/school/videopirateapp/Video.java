package school.videopirateapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Video {
    String title;
    User Uploader; // pointer
    ArrayList<Comment>Comments;
    Integer Views;
    Integer Upvotes;
    Integer Downvotes;
    //TODO: Video videodata; (add this to constructor later)

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
        videoHashMap.put("comments", this.Comments.forEach(comment -> comment.ToHashMap()));
        videoHashMap.put("views",this.Views.toString());
        videoHashMap.put("upvotes",this.Upvotes.toString());
        videoHashMap.put("downvotes",this.Downvotes.toString());
        return videoHashMap;
    }
    public void AddComment(Comment comment) {
        Comments.add(comment);
    }
    public static Video Default() {
        Video default_video=new Video("DefaultVideo",User.Default());
        default_video.AddComment(Comment.Default());
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
    public HashMap<String,HashMap<String,String>> commentsToHashMap() {
        HashMap<String,HashMap<String,String>>commentsHashMap=new HashMap<>();
//        this.Comments.forEach(comment -> comment.ToHashMap());
        for(int i=0;i<this.Comments.size();i++) {
//            commentsHashMap.put(new HashMap<String,String>());
        }
    }
}
