package school.videopirateapp;

import java.util.Vector;

public class Playlist {
    String Name;
    String Id;
    Vector<Video>Videos;
    User Creator;
    Vector<User>Owners;
    Boolean Editable;

    public void Add(Video v){
        if(Editable) Videos.add(v);
        else throw new Error("Playlist is not editable");
    }
}