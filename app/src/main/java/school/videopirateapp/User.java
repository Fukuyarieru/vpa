package school.videopirateapp;

public class User {
    String Name;
    String Id;
    Playlist Uploads;


    // Default constructor required for Firebase
    public User() {
        // Empty constructor for Firebase
    }

    // Constructor with parameters
    public User(String name, String id) {
        this.Name = name;
        this.Id = id;
        this.Uploads = new Playlist();  // If you want to initialize Playlist when a User is created
    }

    // Getters and setters for Firebase to access the fields
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public Playlist getUploads() {
        return Uploads;
    }

    public void setUploads(Playlist uploads) {
        this.Uploads = uploads;
    }
}