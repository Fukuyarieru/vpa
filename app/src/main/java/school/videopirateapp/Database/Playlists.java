package school.videopirateapp.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import school.videopirateapp.DataStructures.Playlist;
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.GlobalVariables;

public abstract class Playlists {
    private static final Map<String, Playlist> Playlists = new HashMap<>();

    private Playlists() {
        throw new UnsupportedOperationException("This class is not instantiable.");
    }

    public static Map<String,Playlist> getPlaylists() {
        return Playlists;
    }

    public static Playlist getPlaylist(String playlistTitle) {
        Log.i("Playlists: getPlaylist", "Getting playlist for title: " + playlistTitle);
        return Playlists.get(playlistTitle);
    }

    public static void upvotePlaylist(Playlist targetPlaylist, User user) {

        DatabaseReference userRef = Database.getRef("users").child(user.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                // check if user exists
                if (!userSnapshot.exists()) {
                    DatabaseReference playlistRef = Database.getRef("playlists").child(targetPlaylist.getTitle());
                    playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                            // check if playlist exists
                            if (playlistSnapshot.exists()) {
                                targetPlaylist.setUpvotes(targetPlaylist.getUpvotes() + 1);
                                user.getUpvotes().add(targetPlaylist.getTitle());
                                if (user.getDownvotes().contains(targetPlaylist.getTitle())) {
                                    user.getDownvotes().remove(targetPlaylist.getTitle());
                                    targetPlaylist.setDownvotes(targetPlaylist.getDownvotes() - 1);
                                }
                                playlistRef.setValue(targetPlaylist);
                                userRef.setValue(user);
                                Playlists.put(targetPlaylist.getTitle(), targetPlaylist);
                                Log.i("Database: upvotePlaylist", "Upvoted playlist " + targetPlaylist.getTitle());
                            } else {
                                Log.e("Database: upvotePlaylist", "Playlist does not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Database: upvotePlaylist", "Failed to add listener to playlistRef");
                        }
                    });
                } else {
                    Log.e("Database: upvotePlaylist", "User does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database: upvotePlaylist", "Failed to add listener to userRef");
            }
        });
    }

    public static void downvotePlaylist(Playlist playlist) {
        if (!GlobalVariables.loggedUser.isPresent()) {
            Log.e("Database: downvotePlaylist", "No user logged in");
            return;
        }

        User user = GlobalVariables.loggedUser.get();
        DatabaseReference userRef = Database.getRef("users").child(user.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                // check if user exists
                if (userSnapshot.exists()) {
                    DatabaseReference playlistRef = Database.getRef("playlists").child(playlist.getTitle());
                    playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                            // check if playlist exists
                            if (playlistSnapshot.exists()) {
                                // Check if user previously upvoted
                                if (user.getUpvotes().contains(playlist.getTitle())) {
                                    playlist.setUpvotes(playlist.getUpvotes() - 1);
                                }
                                // Update user's downvotes
                                user.getDownvotes().add(playlist.getTitle());
                                playlist.setDownvotes(playlist.getDownvotes() + 1);
                                playlist.setScore(playlist.getUpvotes() - playlist.getDownvotes());

                                // Update database
                                Database.updatePlaylist(playlist);
                                userRef.setValue(user);
                                Playlists.put(playlist.getTitle(), playlist);
                                Log.i("Database: downvotePlaylist", "Downvoted playlist: " + playlist.getTitle());
                            } else {
                                Log.e("Database: downvotePlaylist", "Playlist does not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Database: downvotePlaylist", "Failed to add listener to playlistRef");
                        }
                    });
                } else {
                    Log.e("Database: downvotePlaylist", "User does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database: downvotePlaylist", "Failed to add listener to userRef");
            }
        });
    }

    public static void upvotePlaylist(Playlist playlist) {
        if (!GlobalVariables.loggedUser.isPresent()) {
            Log.e("Database: upvotePlaylist", "No user logged in");
            return;
        }

        User user = GlobalVariables.loggedUser.get();
        DatabaseReference userRef = Database.getRef("users").child(user.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                // check if user exists
                if (userSnapshot.exists()) {
                    DatabaseReference playlistRef = Database.getRef("playlists").child(playlist.getTitle());
                    playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                            // check if playlist exists
                            if (playlistSnapshot.exists()) {
                                // Check if user previously downvoted
                                if (user.getDownvotes().contains(playlist.getTitle())) {
                                    playlist.setDownvotes(playlist.getDownvotes() - 1);
                                }
                                // Update user's upvotes
                                user.getUpvotes().add(playlist.getTitle());
                                playlist.setUpvotes(playlist.getUpvotes() + 1);
                                playlist.setScore(playlist.getUpvotes() - playlist.getDownvotes());

                                // Update database
                                Database.updatePlaylist(playlist);
                                userRef.setValue(user);
                                Playlists.put(playlist.getTitle(), playlist);
                                Log.i("Database: upvotePlaylist", "Upvoted playlist: " + playlist.getTitle());
                            } else {
                                Log.e("Database: upvotePlaylist", "Playlist does not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Database: upvotePlaylist", "Failed to add listener to playlistRef");
                        }
                    });
                } else {
                    Log.e("Database: upvotePlaylist", "User does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database: upvotePlaylist", "Failed to add listener to userRef");
            }
        });
    }

    public static void updatePlaylist(@NonNull Playlist playlist) {
        DatabaseReference playlistRef = Database.getRef("playlists").child(playlist.getTitle());
        playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                if (playlistSnapshot.exists()) {
                    playlistRef.setValue(playlist);
                    Playlists.put(playlist.getTitle(), playlist);
                    Log.i("Database: updatePlaylist", "Updated playlist in database: " + playlist.getTitle());
                } else {
                    Log.e("Database: updatePlaylist", "Playlist does not exist: " + playlist.getTitle());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database: updatePlaylist", "Failed to check if playlist exists: " + error.getMessage());
            }
        });
    }

    public static void downvotePlaylist(Playlist targetPlaylist, User user) {
        DatabaseReference userRef = Database.getRef("users").child(user.getName());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                // check if user exists
                if (!userSnapshot.exists()) {
                    DatabaseReference playlistRef = Database.getRef("playlists").child(targetPlaylist.getTitle());
                    playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                            // check if playlist exists
                            if (playlistSnapshot.exists()) {
                                targetPlaylist.setDownvotes(targetPlaylist.getDownvotes() + 1);
                                user.getDownvotes().add(targetPlaylist.getTitle());
                                if (user.getUpvotes().contains(targetPlaylist.getTitle())) {
                                    user.getUpvotes().remove(targetPlaylist.getTitle());
                                    targetPlaylist.setUpvotes(targetPlaylist.getUpvotes() - 1);
                                }
                                playlistRef.setValue(targetPlaylist);
                                userRef.setValue(user);
                                Playlists.put(targetPlaylist.getTitle(), targetPlaylist);
                                Log.i("Database: downvotePlaylist", "Downvoted playlist " + targetPlaylist.getTitle());
                            } else {
                                Log.e("Database: downvotePlaylist", "Playlist does not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Database: downvotePlaylist", "Failed to add listener to playlistRef");
                        }
                    });
                } else {
                    Log.e("Database: downvotePlaylist", "User does not exist");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Database: downvotePlaylist", "Failed to add listener to userRef");
            }
        });
    }

    public static void addPlaylist(Playlist newPlaylist) {
        DatabaseReference playlistRef = Database.getRef("playlists").child(newPlaylist.getTitle());
        playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                // check if playlist doesn't exist
                if (!playlistSnapshot.exists()) {
                    DatabaseReference userRef = Database.getRef("users").child(newPlaylist.getOwner());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            // check if associated user exists
                            if (userSnapshot.exists()) {
                                User user = userSnapshot.getValue(User.class);
                                assert user != null;
                                user.addPlaylist(newPlaylist);
                                playlistRef.setValue(newPlaylist);
                                userRef.setValue(user);
                                Playlists.put(newPlaylist.getTitle(), newPlaylist);
                                Log.i("Database: addPlaylist", "Added playlist to database: " + newPlaylist.getTitle());
                            } else {
                                // user does not exist, do not add the playlist
                                Log.e("Database: addPlaylist", "User does not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Database: addPlaylist", "Failed to add listener to userRef");
                        }
                    });
                } else {
                    Log.w("Database: addPlaylist", "Playlist already exists");
                    // playlist exists
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // some error
                Log.e("Database: addPlaylist", "Failed to add listener to playlistRef");
            }
        });
    }

    public static void Refresh() {
        Log.i("Playlists: Refresh", "Refreshing Playlists");
        DatabaseReference playlistsRef = Database.getRef("playlists");
        playlistsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot playlistsSnapshot) {
                if (playlistsSnapshot.exists()) {
                    Map<String, Playlist> PlaylistMap = new HashMap<>();
                    Log.i("Playlists: Refresh", "Found playlists from database");
                    for (DataSnapshot playlistSnapshot : playlistsSnapshot.getChildren()) {
                        Playlist playlist = playlistSnapshot.getValue(Playlist.class);
                        if (playlist != null) {
                            PlaylistMap.put(playlistSnapshot.getKey(), playlist);
                            Log.i("Playlists: Refresh", "Fetched playlist: " + playlist.getTitle());
                        } else {
                            Log.e("Playlists: Refresh", "Fetched playlist is null");
                        }
                    }
                    Playlists.clear();
                    Playlists.putAll(PlaylistMap);
                } else {
                    Log.e("Playlists: Refresh", "Playlists do not exist?");
                }
                Log.i("Playlists: Refresh", "Finished getting playlists");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Playlists: Refresh", "DatabaseError Failed to fetch playlists: " + error.getMessage());
            }
        });
    }

    public static void initialize() {
        Log.i("Playlists: initialize", "Starting playlist initialization");
        DatabaseReference playlistsRef = Database.getRef("playlists");
        playlistsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists() || !snapshot.hasChildren()) {
                    Log.w("Playlists: initialize", "Creating default playlist");
                    Playlist defaultPlaylist = Playlist.Default();
                    DatabaseReference playlistRef = Database.getRef("playlists").child(defaultPlaylist.getTitle());
                    playlistRef.setValue(defaultPlaylist).addOnSuccessListener(aVoid -> {
                        Log.i("Playlists: initialize", "Default playlist added successfully");
                        Playlists.put(defaultPlaylist.getTitle(), defaultPlaylist);
                        Refresh();
                    }).addOnFailureListener(e -> {
                        Log.e("Playlists: initialize", "Failed to add default playlist: " + e.getMessage());
                    });
                } else {
                    Log.i("Playlists: initialize", "Playlists tree exists with data, refreshing");
                    Refresh();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Playlists: initialize", "Failed to initialize playlists: " + error.getMessage());
            }
        });
    }
}
