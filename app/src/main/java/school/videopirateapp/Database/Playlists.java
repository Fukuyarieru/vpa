package school.videopirateapp.Database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import school.videopirateapp.DataStructures.Playlist;

public class Playlists {

    // TODO, BIG, CONSIDER MAKING ALL THESE DATABASE HELPERS TO HANDLE THE FIREBASE TREES TOO
    private static Playlist savedPlaylist=Playlist.Default();
    private Playlists() {
        throw new UnsupportedOperationException("This class is not instantiable.");
    }

    public static Playlist getSavedPlaylist() {
        return savedPlaylist;
    }

    public static void setSavedPlaylist(Playlist savedPlaylist) {
        Playlists.savedPlaylist = savedPlaylist;
    }
    public static Playlist getPlaylist(String playlistTitle) {
        if(savedPlaylist.getTitle()!=playlistTitle) {
            DatabaseReference playlistRef=Database.getRef("playlists").child(playlistTitle);
            playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                    if(playlistSnapshot.exists()) {
                        savedPlaylist=playlistSnapshot.getValue(Playlist.class);
                    }
                    // throw exception playlist doesnt exist?
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return savedPlaylist;
    }

}
