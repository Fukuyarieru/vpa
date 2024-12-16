package school.videopirateapp;

import java.util.ArrayList;
import java.util.HashMap;


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
    public HashMap<String,String> ToHashMap() {
        HashMap<String,String>videoHashMap=new HashMap<>();
        videoHashMap.put("title",Title);
        videoHashMap.put("uploader",Uploader.Name);
        videoHashMap.put("comments", Comments.toString());
        videoHashMap.put("views",Views.toString());
        videoHashMap.put("upvotes",Upvotes.toString());
        videoHashMap.put("downvotes",Downvotes.toString());
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
}
