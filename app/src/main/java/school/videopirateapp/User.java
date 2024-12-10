package school.videopirateapp;

import android.content.Intent;

import java.util.HashMap;

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
//    public void Watch(Video video) {
//        Intent intent=new Intent(this,Video);
//        intent
//    }

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

    // test
    public HashMap<String,String> ToHashMap() {
        HashMap<String,String>hashMap=new HashMap<>();
        hashMap.put("name",this.Name);
        hashMap.put("id",this.Id);
        return hashMap;
    }
}