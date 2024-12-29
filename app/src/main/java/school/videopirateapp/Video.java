package school.videopirateapp;

import java.util.ArrayList;


public class Video {
    private static Video defaultVideo=new Video();

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getUploaderName() {
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
//    public String getUploaderName() {
//        return this.Uploader;
//    }

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

    public String getUploader() {
        return Uploader;
    }

    private String Title;
    private String Uploader; // pointer
    private ArrayList<Comment>Comments;
    private Integer Views;
    private Integer Upvotes;
    private Integer Downvotes;
    private Integer commentCounter=0;
    //TODO: VideoView videodata; (add this to constructor later)

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
        return defaultVideo;
    }
//    public HashMap<String,HashMap<String,String>> commentsToHashMap() {
//        HashMap<String,HashMap<String,String>>commentsHashMap=new HashMap<>();
////        this.Comments.forEach(comment -> comment.ToHashMap());
//        for(int i=0;i<this.Comments.size();i++) {
////            commentsHashMap.put(new HashMap<String,String>());
//        }
//    }
}
