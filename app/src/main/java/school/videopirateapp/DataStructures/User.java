package school.videopirateapp.DataStructures;


import java.util.ArrayList;

public class User {

    private String name;
    private Playlist Uploads;

    // TODO, comments should a hashmap which takes the comment context as a key to an array of comments
    // TODO 2, P.S, maybe not
    private ArrayList<Comment> Comments;
    private ArrayList<Playlist> ownedPlaylists;
    private String Password;
    private byte[] image;

// Integer totalViews =====> TODO
    // Integer totalUpvotes ===> TODO
    // Integer totalDownvotes => TODO
    // Integer watched ========> TODO

    private static User defaultUser=new User();


    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addPlaylist(Playlist newPlaylist) {
        newPlaylist.setOwner(this.name);
        if(!this.ownedPlaylists.contains(newPlaylist)) {
            this.ownedPlaylists.add(newPlaylist);
        }
    }
    public Playlist getUploads() {
        return Uploads;
    }

    public void setUploads(Playlist uploads) {
        this.Uploads = uploads;
    }
    public ArrayList<Comment> getComments() {
        return Comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        Comments = comments;
    }

    public ArrayList<Playlist> getOwnedPlaylists() {
        return ownedPlaylists;
    }

    public void setOwnedPlaylists(ArrayList<Playlist> ownedPlaylists) {
        this.ownedPlaylists = ownedPlaylists;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    public void addComment(Comment newComment) {
        for(int i=0;i<this.Comments.size();i++) {
            Comment comment=this.Comments.get(i);
            if(comment.getComment()!=newComment.getComment()) {
                if(comment.getContext()!=newComment.getContext()) {
                    this.Comments.add(newComment);
                }
            }
        }
//        if(!this.Comments.contains()) {
//            this.Comments.add(newComment);
//        }
//        if(!this.Comments.contains(newComment)) {
//            // half ass solution
//            boolean notContain=true;
//            for(int i=0;i<this.Comments.size();i++) {
//                if(this.Comments.get(i).getComment()==newComment.getComment()) {
//                    notContain=false;
//                }
//            }
//            if(notContain) {
//                this.Comments.add(newComment);
//            }
//        }
    }
    public void addVideo(Video newVideo) {
        this.Uploads.addVideo(newVideo);
    }

    // Default constructor required for Firebase
    public User() {
        // NOTE: NEVER AGAIN ADD DEFAULT DETAILS TO DEFAULT DATA, THAT IS NOT NECESSERY TO BE THERE

        // Empty constructor for Firebase
        this("@Default","123");
    }

    // Constructor with parameters
    public User(String name, String password) {
        if (!name.startsWith("@")) {
            name = "@" + name;
        }
        this.name = name;
        this.Uploads = new Playlist("Uploads",name);  // If you want to initialize Playlist when a User is created
        this.Comments = new ArrayList<Comment>();
        this.Comments.add(Comment.Default());
        this.ownedPlaylists=new ArrayList<Playlist>();
        this.ownedPlaylists.add(Playlist.Default());
//        ownedPlaylists.add(Playlist.Default());
        this.image=null; // TODO , do this later
        this.Password=password;
    }
    public static User Default() {
        return defaultUser;
    }
//    public void Watch(Video video) {
//        Intent intent=new Intent(,VideoPlayerActivity.class);
//
//    }
}