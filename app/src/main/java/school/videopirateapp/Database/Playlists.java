package school.videopirateapp.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import school.videopirateapp.DataStructures.Playlist;
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.GlobalVariables;

public abstract class Playlists {

    // TODO, BIG, CONSIDER MAKING ALL THESE DATABASE HELPERS TO HANDLE THE FIREBASE TREES TOO
    private static Playlist savedPlaylist = Playlist.Default();

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
        if (!savedPlaylist.getTitle().equals(playlistTitle)) {
            DatabaseReference playlistRef = Database.getRef("playlists").child(playlistTitle);
            playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                    if (playlistSnapshot.exists()) {
                        savedPlaylist = playlistSnapshot.getValue(Playlist.class);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Playlists: getPlaylist", "Failed to get playlist: " + error.getMessage());
                }
            });
        }
        return savedPlaylist;
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

    public static boolean downvotePlaylist(Playlist playlist) {
        if (!GlobalVariables.loggedUser.isPresent()) {
            Log.e("Database: downvotePlaylist", "No user logged in");
            return false;
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
        return true;
    }

    public static boolean upvotePlaylist(Playlist playlist) {
        if (!GlobalVariables.loggedUser.isPresent()) {
            Log.e("Database: upvotePlaylist", "No user logged in");
            return false;
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
        return true;
    }

    public static void updatePlaylist(@NonNull Playlist playlist) {
        DatabaseReference playlistRef = Database.getRef("playlists").child(playlist.getTitle());
        playlistRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot playlistSnapshot) {
                if (playlistSnapshot.exists()) {
                    playlistRef.setValue(playlist);
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

    public static void initialize() {
        DatabaseReference playlistsRef = Database.getRef("playlists");
        playlistsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Log.w("Playlists: initialize", "Creating default playlist");
                    addPlaylist(Playlist.Default());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Playlists: initialize", "Failed to initialize playlists: " + error.getMessage());
            }
        });
    }

    public static void addPlaylist(Playlist newPlaylist) {
        DatabaseReference playlistRef = Database.getRef("playlists").child("&" + newPlaylist.getTitle());
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
                                playlistRef.setValue(newPlaylist);
                                savedPlaylist = newPlaylist;
                                Log.i("Playlists: addPlaylist", "Added playlist to database: " + newPlaylist.getTitle());
                            } else {
                                // user does not exist, do not add the playlist
                                Log.e("Playlists: addPlaylist", "User does not exist");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("Playlists: addPlaylist", "Failed to add listener to userRef");
                        }
                    });
                } else {
                    Log.w("Playlists: addPlaylist", "Playlist already exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Playlists: addPlaylist", "Failed to add listener to playlistRef");
            }
        });
    }

}
