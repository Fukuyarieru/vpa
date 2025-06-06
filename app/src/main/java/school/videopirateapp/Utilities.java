package school.videopirateapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import school.videopirateapp.Activities.CommentPageActivity;
import school.videopirateapp.Activities.PlaylistPageActivity;
import school.videopirateapp.Activities.SignupActivity;
import school.videopirateapp.Activities.UserPageActivity;
import school.videopirateapp.Activities.VideoPageActivity;
import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.Playlist;
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;
import school.videopirateapp.ListViewComponents.PlaylistAdapter;

public class Utilities {
    private Utilities() {
        throw new UnsupportedOperationException("This class is not instantiable.");
    }

    public static <T> ArrayList<T> HashMapToArrayList(@NonNull HashMap<String, T> hashMap) {
        Log.i("Utilities", "Converting HashMap to ArrrayList");
        // this function does not do any sorting
        // existing code equals to this
//        ArrayList<T> arrayList = new ArrayList<>();
//        for (T value : hashMap.values()) {
//            arrayList.add(value);
//        }
//        return arrayList;

        return new ArrayList<>(hashMap.values());

    }

    public static <T> ArrayList<T> MapToArrayList(@NonNull Map<String, T> hashMap) {
        Log.i("Utilities", "Converting HashMap to ArrrayList");
        // this function does not do any sorting
        // existing code equals to this
//        ArrayList<T> arrayList = new ArrayList<>();
//        for (T value : hashMap.values()) {
//            arrayList.add(value);
//        }
//        return arrayList;

        return new ArrayList<>(hashMap.values());
    }

    public static String TimeNow() {
        // logging time makes us get SPAM
//        Log.i("Utilities: TimeNow", "Getting the time now");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String datetime = dateformat.format(c.getTime());
        return datetime;
    }

    public static void openVideoPage(@NonNull Context currentActivityThis, String videoTitle) {
        Log.i("Utilities: openVideoPage", "Video page opened");
        Intent intent = new Intent(currentActivityThis, VideoPageActivity.class);
        intent.putExtra("videoTitle", videoTitle);
        currentActivityThis.startActivity(intent);
    }

    public static void openUserPage(@NonNull Context currentActivityThis, String userName) {
        Log.i("Utilities: openUserPage", "User page opened");
        Intent intent = new Intent(currentActivityThis, UserPageActivity.class);
        intent.putExtra("user", userName);
        currentActivityThis.startActivity(intent);
    }

    public static void openCommentPage(@NonNull Context currentActivityThis, String commentContext) {
        Log.i("Utilities: openCommentPage", "Comment page opened");
        Intent intent = new Intent(currentActivityThis, CommentPageActivity.class);
        intent.putExtra("commentContext", commentContext);
        currentActivityThis.startActivity(intent);
    }

    public static void openCommentOwnerOptionsDialog(Context contextThis, Comment comment) {
        Log.i("Utilities: openCommentOwnerOptionsDialog", "Comment owner options dialog opened");
        Dialog dialog = new Dialog(contextThis);
        dialog.setContentView(R.layout.activity_comment_options_dialog);

        Button btnDeleteComment = dialog.findViewById(R.id.CommentOptions_Dialog_Button_DeleteComment);
        Button btnCommentPage = dialog.findViewById(R.id.CommentOptions_Dialog_Button_CommentPage);
        Button btnEditComment = dialog.findViewById(R.id.CommentOptions_Dialog_Button_EditComment);
//        TextView tvContext = dialog.findViewById(R.id.CommentOptions_Dialog_TextView_Context);
        TextView tvScore = dialog.findViewById(R.id.CommentOptions_Dialog_TextView_Score);
        Button btnDownvote = dialog.findViewById(R.id.CommentOptions_Dialog_Button_Downvote);
        Button btnUpvote = dialog.findViewById(R.id.CommentOptions_Dialog_Button_Upvote);
//        Button btnReply = dialog.findViewById(R.id.CommentOptions_Dialog_Button_Reply);

        // tvContext.setText(comment.getContext());  // Commented out
        btnDeleteComment.setText("Delete Comment");
        btnCommentPage.setText("Comment Page");
        btnEditComment.setText("Edit Comment");
        tvScore.setText(String.valueOf(comment.getScore()));
        btnDownvote.setText("\uD83D\uDC4E");
        btnUpvote.setText("\uD83D\uDC4D");
//        btnReply.setText("Reply");

        // Show owner-specific buttons
        btnDeleteComment.setVisibility(View.VISIBLE);
        btnEditComment.setVisibility(View.VISIBLE);

        btnDeleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.loggedUser.isPresent() && GlobalVariables.loggedUser.get().getName().equals(comment.getAuthor())) {
                    Video video = Database.getVideo(comment.getContext());
                    if (video != null) {
                        video.getComments().remove(comment);
                        Database.updateVideo(video);
                        dialog.dismiss();
                        Feedback(contextThis, "Comment deleted successfully");
                    } else {
                        Feedback(contextThis, "Error: Video not found");
                    }
                } else {
                    Feedback(contextThis, "You can only delete your own comments");
                }
            }
        });

        btnCommentPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Utilities: openCommentOwnerOptionsDialog", "Opening comment page");
                dialog.dismiss();
                openCommentPage(contextThis, comment.getContext());
            }
        });

        btnEditComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.loggedUser.isPresent() && GlobalVariables.loggedUser.get().getName().equals(comment.getAuthor())) {
                    // Create a dialog for editing the comment
                    Dialog editDialog = new Dialog(contextThis);
                    editDialog.setContentView(R.layout.activity_comment_input_dialog);
                    
                    EditText etEditComment = editDialog.findViewById(R.id.CommentInput_Dialog_EditText_Comment);
                    Button btnSubmit = editDialog.findViewById(R.id.CommentInput_Dialog_Button_Submit);
                    Button btnCancel = editDialog.findViewById(R.id.CommentInput_Dialog_Button_Cancel);
                    
                    // Set the current comment text
                    etEditComment.setText(comment.getComment());
                    
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String newCommentText = etEditComment.getText().toString().trim();
                            if (!newCommentText.isEmpty()) {
                                comment.setComment(newCommentText);
                                Video video = Database.getVideo(comment.getContext());
                                if (video != null) {
                                    Database.updateComment(comment);
                                    editDialog.dismiss();
                                    dialog.dismiss();
                                    Feedback(contextThis, "Comment edited successfully");
                                } else {
                                    Feedback(contextThis, "Error: Video not found");
                                }
                            } else {
                                Feedback(contextThis, "Comment cannot be empty");
                            }
                        }
                    });
                    
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editDialog.dismiss();
                        }
                    });
                    
                    editDialog.show();
                } else {
                    Feedback(contextThis, "You can only edit your own comments");
                }
            }
        });

        btnDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.loggedUser.isPresent()) {
                    comment.downvote();
                    tvScore.setText(String.valueOf(comment.getScore()));
                    Database.updateComment(comment);
                    Feedback(contextThis, "Comment downvoted");
                } else {
                    Feedback(contextThis, "Please log in to vote");
                }
            }
        });

        btnUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.loggedUser.isPresent()) {
                    comment.upvote();
                    tvScore.setText(String.valueOf(comment.getScore()));
                    Database.updateComment(comment);
                    Feedback(contextThis, "Comment upvoted");
                } else {
                    Feedback(contextThis, "Please log in to vote");
                }
            }
        });

        /* Commented out reply functionality
        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.loggedUser.isPresent()) {
                    // Create a dialog for reply input
                    Dialog replyDialog = new Dialog(contextThis);
                    replyDialog.setContentView(R.layout.activity_comment_input_dialog);
                    
                    EditText etReply = replyDialog.findViewById(R.id.CommentInput_Dialog_EditText_Comment);
                    Button btnSubmit = replyDialog.findViewById(R.id.CommentInput_Dialog_Button_Submit);
                    Button btnCancel = replyDialog.findViewById(R.id.CommentInput_Dialog_Button_Cancel);
                    
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String replyText = etReply.getText().toString().trim();
                            if (!replyText.isEmpty()) {
                                Comment reply = new Comment(replyText, GlobalVariables.loggedUser.get().getName(), comment.getContext());
                                comment.addReply(reply);
                                Database.updateComment(comment, Database.getVideo(comment.getContext()));
                                replyDialog.dismiss();
                                dialog.dismiss();
                                Feedback(contextThis, "Reply added successfully");
                            } else {
                                Feedback(contextThis, "Reply cannot be empty");
                            }
                        }
                    });
                    
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replyDialog.dismiss();
                        }
                    });
                    
                    replyDialog.show();
                } else {
                    Feedback(contextThis, "Please log in to reply");
                }
            }
        });
        */
        dialog.show();
    }

    public static void openCommentViewerOptionsDialog(Context contextThis, Comment comment) {
        Log.i("Utilities: openCommentViewerOptionsDialog", "Comment viewer options dialog opened");
        Dialog dialog = new Dialog(contextThis);
        dialog.setContentView(R.layout.activity_comment_options_dialog);

        Button btnDeleteComment = dialog.findViewById(R.id.CommentOptions_Dialog_Button_DeleteComment);
        Button btnCommentPage = dialog.findViewById(R.id.CommentOptions_Dialog_Button_CommentPage);
        Button btnEditComment = dialog.findViewById(R.id.CommentOptions_Dialog_Button_EditComment);
//        TextView tvContext = dialog.findViewById(R.id.CommentOptions_Dialog_TextView_Context);
        TextView tvScore = dialog.findViewById(R.id.CommentOptions_Dialog_TextView_Score);
        Button btnDownvote = dialog.findViewById(R.id.CommentOptions_Dialog_Button_Downvote);
        Button btnUpvote = dialog.findViewById(R.id.CommentOptions_Dialog_Button_Upvote);
//        Button btnReply = dialog.findViewById(R.id.CommentOptions_Dialog_Button_Reply);

        // tvContext.setText(comment.getContext());  // Commented out
        btnCommentPage.setText("Comment Page");
        tvScore.setText(String.valueOf(comment.getScore()));
        btnDownvote.setText("\uD83D\uDC4E");
        btnUpvote.setText("\uD83D\uDC4D");
//        btnReply.setText("Reply");

        // Hide owner-specific buttons
        btnDeleteComment.setVisibility(View.GONE);
        btnEditComment.setVisibility(View.GONE);

        btnCommentPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Utilities: openCommentViewerOptionsDialog", "Opening comment page");
                dialog.dismiss();
                openCommentPage(contextThis, comment.getContext());
            }
        });

        btnDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.loggedUser.isPresent()) {
                    comment.downvote();
                    tvScore.setText(String.valueOf(comment.getScore()));
                    Database.updateComment(comment);
                    Feedback(contextThis, "Comment downvoted");
                } else {
                    Feedback(contextThis, "Please log in to vote");
                }
            }
        });

        btnUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.loggedUser.isPresent()) {
                    comment.upvote();
                    tvScore.setText(String.valueOf(comment.getScore()));
                    Database.updateComment(comment);
                    Feedback(contextThis, "Comment upvoted");
                } else {
                    Feedback(contextThis, "Please log in to vote");
                }
            }
        });

        /* Commented out reply functionality
        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.loggedUser.isPresent()) {
                    // Create a dialog for reply input
                    Dialog replyDialog = new Dialog(contextThis);
                    replyDialog.setContentView(R.layout.activity_comment_input_dialog);
                    
                    EditText etReply = replyDialog.findViewById(R.id.CommentInput_Dialog_EditText_Comment);
                    Button btnSubmit = replyDialog.findViewById(R.id.CommentInput_Dialog_Button_Submit);
                    Button btnCancel = replyDialog.findViewById(R.id.CommentInput_Dialog_Button_Cancel);
                    
                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String replyText = etReply.getText().toString().trim();
                            if (!replyText.isEmpty()) {
                                Comment reply = new Comment(replyText, GlobalVariables.loggedUser.get().getName(), comment.getContext());
                                comment.addReply(reply);
                                Database.updateComment(comment, Database.getVideo(comment.getContext()));
                                replyDialog.dismiss();
                                dialog.dismiss();
                                Feedback(contextThis, "Reply added successfully");
                            } else {
                                Feedback(contextThis, "Reply cannot be empty");
                            }
                        }
                    });
                    
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            replyDialog.dismiss();
                        }
                    });
                    
                    replyDialog.show();
                } else {
                    Feedback(contextThis, "Please log in to reply");
                }
            }
        });
        */
        dialog.show();
    }

    public static void openVideoUploadDialog(Context thisContext) {
        Dialog uploadDialog = new Dialog(thisContext);
        uploadDialog.setContentView(R.layout.activity_upload_video_dialog);
        uploadDialog.show();

        Button btnChooseVideo = uploadDialog.findViewById(R.id.UploadVideo_Dialog_Button_ChooseVideo);
        Button btnUploadVideo = uploadDialog.findViewById(R.id.UploadVideo_Dialog_Button_UploadVideo);
        ImageView thumbnail = uploadDialog.findViewById(R.id.UploadVideo_Dialog_ImageView_Thumbnail);
        EditText etVideoTitle = uploadDialog.findViewById(R.id.UploadVideo_Dialog_EditText_VideoTitle);

        btnUploadVideo.setText("Upload");
        btnChooseVideo.setText("Choose");

        btnUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chosenTitle = etVideoTitle.getText().toString();
                if (chosenTitle.isEmpty()) {
                    Feedback(thisContext, "Please enter a video title");
                    return;
                }
                if (Database.getVideo(chosenTitle) != null) {
                    Feedback(thisContext, "Video with that title already exists");
                    return;
                }
                if (!GlobalVariables.loggedUser.isPresent()) {
                    Feedback(thisContext, "You need to be logged in to upload videos");
                    return;
                }

                Video newVideo = new Video(chosenTitle, GlobalVariables.loggedUser.get().getName());
                Database.addVideo(newVideo);
                Feedback(thisContext, "Added video named: " + newVideo.getTitle());
                uploadDialog.dismiss();
            }
        });
        btnChooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static void openVideoOwnerOptionsDialog(Context thisContext, Video video) {
        Dialog dialog = new Dialog(thisContext);
        dialog.setContentView(R.layout.activity_video_option_dialog);

        Button btnDeleteVideo = dialog.findViewById(R.id.VideoOptions_Dialog_Button_DeleteVideo);
        Button btnEditVideo = dialog.findViewById(R.id.VideoOptions_Dialog_Button_EditVideo);
        Button btnAddVideoToPlaylist = dialog.findViewById(R.id.VideoOptions_Dialog_Button_AddVideoToPlaylist);

        btnEditVideo.setText("Edit Video");
        btnDeleteVideo.setText("Delete Video");
        btnAddVideoToPlaylist.setText("Add Video to Playlist");

        // Show owner-specific buttons
        btnDeleteVideo.setVisibility(View.VISIBLE);
        btnEditVideo.setVisibility(View.VISIBLE);

        btnDeleteVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Implement delete video functionality
                Feedback(thisContext, "Delete video functionality coming soon");
            }
        });

        btnEditVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Implement edit video functionality
                Feedback(thisContext, "Edit video functionality coming soon");
            }
        });

        btnAddVideoToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openPlaylistSelectionDialog(thisContext, video);
            }
        });

        dialog.show();
    }

    public static void openVideoViewerOptionsDialog(Context thisContext, Video video) {
        Dialog dialog = new Dialog(thisContext);
        dialog.setContentView(R.layout.activity_video_option_dialog);

        Button btnDeleteVideo = dialog.findViewById(R.id.VideoOptions_Dialog_Button_DeleteVideo);
        Button btnEditVideo = dialog.findViewById(R.id.VideoOptions_Dialog_Button_EditVideo);
        Button btnAddVideoToPlaylist = dialog.findViewById(R.id.VideoOptions_Dialog_Button_AddVideoToPlaylist);

        btnAddVideoToPlaylist.setText("Add Video to Playlist");

        // Hide owner-specific buttons
        btnDeleteVideo.setVisibility(View.GONE);
        btnEditVideo.setVisibility(View.GONE);

        btnAddVideoToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openPlaylistSelectionDialog(thisContext, video);
            }
        });

        dialog.show();
    }

    public static void openPlaylistSelectionDialog(Context thisContext, Video video) {
        if (GlobalVariables.loggedUser.isEmpty()) {
            Feedback(thisContext, "You must be logged in to add videos to playlists");
            return;
        }

        Dialog dialog = new Dialog(thisContext);
        dialog.setContentView(R.layout.activity_playlist_selection_dialog);

        ListView listView = dialog.findViewById(R.id.PlaylistSelection_Dialog_ListView_Playlists);
        Button btnCancel = dialog.findViewById(R.id.PlaylistSelection_Dialog_Button_Cancel);

        // Get user's playlists
        ArrayList<Playlist> userPlaylists = new ArrayList<>();
        User user = GlobalVariables.loggedUser.get();
        for (String playlistTitle : user.getOwnedPlaylists()) {
            Playlist playlist = Database.getPlaylist(playlistTitle);
            if (playlist != null) {
                userPlaylists.add(playlist);
            }
        }

        // Create a custom adapter for playlist selection that overrides the click behavior
        PlaylistAdapter adapter = new PlaylistAdapter(thisContext, R.layout.activity_playlist_list_view_component, userPlaylists) {
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                
                // Override the click listener set in the parent adapter
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Playlist selectedPlaylist = userPlaylists.get(position);
                        Database.addVideoToPlaylist(video, selectedPlaylist);
                        Feedback(thisContext, "Video added to playlist: " + selectedPlaylist.getTitle());
                        dialog.dismiss();
                    }
                });
                
                return view;
            }
        };
        listView.setAdapter(adapter);

        // Handle cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static void openUserOwnerOptionsDialog(Context thisContext, User user) {
        Dialog dialog = new Dialog(thisContext);
        dialog.setContentView(R.layout.activity_user_options_dialog);
        
        Button btnLogout = dialog.findViewById(R.id.UserOptions_Dialog_Button_Logout);
        Button btnChangeImage = dialog.findViewById(R.id.UserOptions_Dialog_Button_ChangeImage);
        Button btnChangeDescription = dialog.findViewById(R.id.UserOptions_Dialog_Button_ChangeDescription);
        Button btnCreatePlaylist = dialog.findViewById(R.id.UserOptions_Dialog_Button_CreatePlaylist);

        btnLogout.setText("Logout");
        btnChangeImage.setText("Change Profile Picture");
        btnChangeDescription.setText("Change Description");
        btnCreatePlaylist.setText("Create Playlist");

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariables.loggedUser = Optional.empty();
                Feedback(thisContext, "Logged out successfully");
                dialog.dismiss();
            }
        });

        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a dialog for image source selection
                Dialog imageSourceDialog = new Dialog(thisContext);
                imageSourceDialog.setContentView(R.layout.activity_image_source_dialog);
                
                Button btnTakePhoto = imageSourceDialog.findViewById(R.id.ImageSource_Dialog_Button_TakePhoto);
                Button btnChooseGallery = imageSourceDialog.findViewById(R.id.ImageSource_Dialog_Button_ChooseGallery);
                
                btnTakePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // Set high quality for camera
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, 
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            takePictureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                            
                            if (takePictureIntent.resolveActivity(((Activity) thisContext).getPackageManager()) != null) {
                                ((Activity) thisContext).startActivityForResult(takePictureIntent, 2);
                            } else {
                                Feedback(thisContext, "No camera app found");
                            }
                        } catch (Exception e) {
                            Log.e("Utilities", "Error launching camera", e);
                            Feedback(thisContext, "Error launching camera: " + e.getMessage());
                        }
                        imageSourceDialog.dismiss();
                    }
                });
                
                btnChooseGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"});
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                            
                            ((Activity) thisContext).startActivityForResult(
                                Intent.createChooser(intent, "Select Profile Picture"),
                                1
                            );
                        } catch (android.content.ActivityNotFoundException ex) {
                            Feedback(thisContext, "Please install a File Manager.");
                        } catch (Exception e) {
                            Log.e("Utilities", "Error launching gallery", e);
                            Feedback(thisContext, "Error accessing gallery: " + e.getMessage());
                        }
                        imageSourceDialog.dismiss();
                    }
                });
                
                imageSourceDialog.show();
                dialog.dismiss();
            }
        });

        btnChangeDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Implement change description functionality
                Feedback(thisContext, "Change description functionality coming soon");
            }
        });

        btnCreatePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Dialog createPlaylistDialog = new Dialog(thisContext);
                createPlaylistDialog.setContentView(R.layout.activity_create_playlist_dialog);

                EditText etTitle = createPlaylistDialog.findViewById(R.id.CreatePlaylist_Dialog_EditText_Title);
                EditText etDescription = createPlaylistDialog.findViewById(R.id.CreatePlaylist_Dialog_EditText_Description);
                Button btnCreate = createPlaylistDialog.findViewById(R.id.CreatePlaylist_Dialog_Button_Create);
                Button btnCancel = createPlaylistDialog.findViewById(R.id.CreatePlaylist_Dialog_Button_Cancel);

                btnCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = etTitle.getText().toString().trim();
                        String description = etDescription.getText().toString().trim();

                        if (title.isEmpty()) {
                            Feedback(thisContext, "Please enter a playlist title");
                            return;
                        }

                        if (!title.startsWith("&")) {
                            title = "&" + title;
                        }

                        Playlist newPlaylist = new Playlist(title, user.getName());
                        if (!description.isEmpty()) {
                            newPlaylist.setDescription(description);
                        }

                        Database.addPlaylist(newPlaylist);
                        user.addPlaylist(newPlaylist);
                        Database.updateUser(user);
                        
                        Feedback(thisContext, "Playlist created successfully");
                        createPlaylistDialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createPlaylistDialog.dismiss();
                    }
                });

                createPlaylistDialog.show();
            }
        });
        
        dialog.show();
    }

    public static void openUserViewerOptionsDialog(Context thisContext, User user) {
        Dialog dialog = new Dialog(thisContext);
        dialog.setContentView(R.layout.activity_user_options_dialog);
        
        Button btnLogout = dialog.findViewById(R.id.UserOptions_Dialog_Button_Logout);
        Button btnChangeImage = dialog.findViewById(R.id.UserOptions_Dialog_Button_ChangeImage);
        Button btnChangeDescription = dialog.findViewById(R.id.UserOptions_Dialog_Button_ChangeDescription);
        Button btnCreatePlaylist = dialog.findViewById(R.id.UserOptions_Dialog_Button_CreatePlaylist);

        // Hide owner-specific buttons
        btnLogout.setVisibility(View.GONE);
        btnChangeImage.setVisibility(View.GONE);
        btnChangeDescription.setVisibility(View.GONE);
        btnCreatePlaylist.setVisibility(View.GONE);
        
        dialog.show();
    }

    /**
     * Opens a dialog for playlist owner options with optional source button state management.
     * The source button will be updated to reflect the selected operation's state if provided.
     * 
     * @param thisContext The context in which the dialog is shown
     * @param playlistTitle The title of the playlist
     */
    public static void openPlaylistOwnerOptionsDialog(Context thisContext, String playlistTitle) {
        Dialog dialog = new Dialog(thisContext);
        dialog.setContentView(R.layout.activity_playlist_options_dialog);
        
        Button btnEditPlaylist = dialog.findViewById(R.id.PlaylistOptions_Dialog_Button_EditPlaylist);
        Button btnDeletePlaylist = dialog.findViewById(R.id.PlaylistOptions_Dialog_Button_DeletePlaylist);
        Button btnSharePlaylist = dialog.findViewById(R.id.PlaylistOptions_Dialog_Button_SharePlaylist);
        Button btnPlaylistPage = dialog.findViewById(R.id.PlaylistOptions_Dialog_Button_PlaylistPage);
        Button btnUpvote = dialog.findViewById(R.id.PlaylistOptions_Dialog_Button_Upvote);
        Button btnDownvote = dialog.findViewById(R.id.PlaylistOptions_Dialog_Button_Downvote);
        TextView tvScore = dialog.findViewById(R.id.PlaylistOptions_Dialog_TextView_Score);
        
        Playlist playlist = Database.getPlaylist(playlistTitle);
        if (playlist == null) {
            Feedback(thisContext, "Playlist not found");
            dialog.dismiss();
            return;
        }

        tvScore.setText(String.valueOf(playlist.getVoteScore()));
        
        btnEditPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Implement edit playlist functionality
                Feedback(thisContext, "Edit playlist functionality coming soon");
            }
        });
        
        btnDeletePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Implement delete playlist functionality
                Feedback(thisContext, "Delete playlist functionality coming soon");
            }
        });
        
        btnSharePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Implement share playlist functionality
                Feedback(thisContext, "Share playlist functionality coming soon");
            }
        });

        btnPlaylistPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlaylistPage(thisContext, playlistTitle);
                dialog.dismiss();
            }
        });

        btnUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.loggedUser.isEmpty()) {
                    Feedback(thisContext, "You must be logged in to upvote");
                } else {
                    if (GlobalVariables.loggedUser.get().getUpvotes().contains(playlist.getTitle())) {
                        Feedback(thisContext, "You have this playlist already upvoted");
                    } else {
                        Database.upvotePlaylist(playlist, GlobalVariables.loggedUser.get());
                        EvaluatePlaylist(playlist);
                        tvScore.setText(String.valueOf(playlist.getVoteScore()));
                        Feedback(thisContext, "Playlist upvoted");
                    }
                }
            }
        });

        btnDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.loggedUser.isEmpty()) {
                    Feedback(thisContext, "You must be logged in to downvote");
                } else {
                    if (GlobalVariables.loggedUser.get().getDownvotes().contains(playlist.getTitle())) {
                        Feedback(thisContext, "You have this playlist already downvoted");
                    } else {
                        Database.downvotePlaylist(playlist, GlobalVariables.loggedUser.get());
                        EvaluatePlaylist(playlist);
                        tvScore.setText(String.valueOf(playlist.getVoteScore()));
                        Feedback(thisContext, "Playlist downvoted");
                    }
                }
            }
        });
        
        dialog.show();
    }

    public static void openPlaylistViewerOptionsDialog(Context thisContext, String playlistTitle) {
        Dialog dialog = new Dialog(thisContext);
        dialog.setContentView(R.layout.activity_playlist_options_dialog);
        
        Button btnEditPlaylist = dialog.findViewById(R.id.PlaylistOptions_Dialog_Button_EditPlaylist);
        Button btnDeletePlaylist = dialog.findViewById(R.id.PlaylistOptions_Dialog_Button_DeletePlaylist);
        Button btnSharePlaylist = dialog.findViewById(R.id.PlaylistOptions_Dialog_Button_SharePlaylist);
        Button btnPlaylistPage = dialog.findViewById(R.id.PlaylistOptions_Dialog_Button_PlaylistPage);
        Button btnUpvote = dialog.findViewById(R.id.PlaylistOptions_Dialog_Button_Upvote);
        Button btnDownvote = dialog.findViewById(R.id.PlaylistOptions_Dialog_Button_Downvote);
        TextView tvScore = dialog.findViewById(R.id.PlaylistOptions_Dialog_TextView_Score);
        
        Playlist playlist = Database.getPlaylist(playlistTitle);
        if (playlist == null) {
            Feedback(thisContext, "Playlist not found");
            dialog.dismiss();
            return;
        }

        tvScore.setText(String.valueOf(playlist.getVoteScore()));
        
        // Hide owner-specific buttons
        btnEditPlaylist.setVisibility(View.GONE);
        btnDeletePlaylist.setVisibility(View.GONE);
        
        btnSharePlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Utilities: openPlaylistViewerOptionsDialog", "Share playlist clicked");
                // TODO: Implement share playlist functionality
                Feedback(thisContext, "Share playlist functionality coming soon");
            }
        });

        btnPlaylistPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Utilities: openPlaylistViewerOptionsDialog", "Opening playlist page");
                openPlaylistPage(thisContext, playlistTitle);
                dialog.dismiss();
            }
        });

        btnUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.loggedUser.isEmpty()) {
                    Feedback(thisContext, "You must be logged in to upvote");
                } else {
                    if (GlobalVariables.loggedUser.get().getUpvotes().contains(playlist.getTitle())) {
                        Feedback(thisContext, "You have this playlist already upvoted");
                    } else {
                        Database.upvotePlaylist(playlist, GlobalVariables.loggedUser.get());
                        EvaluatePlaylist(playlist);
                        tvScore.setText(String.valueOf(playlist.getVoteScore()));
                        Feedback(thisContext, "Playlist upvoted");
                    }
                }
            }
        });

        btnDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GlobalVariables.loggedUser.isEmpty()) {
                    Feedback(thisContext, "You must be logged in to downvote");
                } else {
                    if (GlobalVariables.loggedUser.get().getDownvotes().contains(playlist.getTitle())) {
                        Feedback(thisContext, "You have this playlist already downvoted");
                    } else {
                        Database.downvotePlaylist(playlist, GlobalVariables.loggedUser.get());
                        EvaluatePlaylist(playlist);
                        tvScore.setText(String.valueOf(playlist.getVoteScore()));
                        Feedback(thisContext, "Playlist downvoted");
                    }
                }
            }
        });
        
        dialog.show();
    }

    public static void openSignupActivity(@NonNull Context currentActivityThis) {
        Log.i("Utilities: openSignupActivity", "Signup activity opened");
        Intent openSignupActivity=new Intent(currentActivityThis, SignupActivity.class);
        currentActivityThis.startActivity(openSignupActivity);
    }

    public static void openPlaylistPage(@NonNull Context currentActivityThis, String playlistTitle) {
        Log.i("Utilities: openPlaylistPage", "Playlist page opened");
        Intent intent = new Intent(currentActivityThis, PlaylistPageActivity.class);
        intent.putExtra("playlistTitle", playlistTitle);
        currentActivityThis.startActivity(intent);
    }

    public static Bitmap ByteArrayToBitmap(ArrayList<Byte> byteArray) {
        if (byteArray == null || byteArray.isEmpty()) {
            byte[] arr = new byte[]{1};
            return BitmapFactory.decodeByteArray(arr, 0, arr.length);
        }

        byte[] bytes = new byte[byteArray.size()];
        for (int i = 0; i < byteArray.size(); i++) {
            bytes[i] = byteArray.get(i);
        }

        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    public static ArrayList<Byte> BitmapToByteArray(Bitmap bitmap) {
        if (bitmap == null) {
            return new ArrayList<>();
        }
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Compress with quality 85 for good balance of quality and size
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream);
            byte[] byteArray = stream.toByteArray();
            ArrayList<Byte> byteArrayList = new ArrayList<>();
            for (byte b : byteArray) {
                byteArrayList.add(b);
            }
            stream.close();
            return byteArrayList;
        } catch (Exception e) {
            Log.e("Utilities", "Error converting bitmap to byte array", e);
            return new ArrayList<>();
        }
    }
    public static ArrayList<Byte> getDefaultUserImage() {
       return BitmapToByteArray(BitmapFactory.decodeResource(null, R.drawable.default_user_image));
    }
    public static ArrayList<Byte> getDefaultVideoImage() {
        return BitmapToByteArray(BitmapFactory.decodeResource(null, R.drawable.default_video_image));
    }
    public static ArrayList<Byte> getDefaultPlaylistImage() {
        return BitmapToByteArray(BitmapFactory.decodeResource(null, R.drawable.default_playlist_image));
    }


    public static void openLoginDialog(Context thisContext, Button originalLoginBtn) {
        Dialog loginDialog = new Dialog(thisContext);
        loginDialog.setContentView(R.layout.activity_login_dialog);
        EditText etUsername = loginDialog.findViewById(R.id.Login_Dialog_EditText_Username);
        EditText etPassword = loginDialog.findViewById(R.id.Login_Dialog_EditText_Password);
        Button btnLogin = loginDialog.findViewById(R.id.Login_Dialog_Button_Login);
        Button btnSignup = loginDialog.findViewById(R.id.Login_Dialog_Button_Signup);
        CheckBox cbRememberPassword = loginDialog.findViewById(R.id.checkBox);
        CheckBox cbRememberUsername = loginDialog.findViewById(R.id.checkBox2);

        final User[] desiredUser = {null};
        final String[] username = {""};
        final String[] password = {""};

        // Load saved credentials from SharedPreferences
        SharedPreferences prefs = thisContext.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        String savedUsername = prefs.getString("username", "");
        String savedPassword = prefs.getString("password", "");
        boolean rememberUsername = prefs.getBoolean("remember_username", false);
        boolean rememberPassword = prefs.getBoolean("remember_password", false);

        // Set saved values
        if (rememberUsername && !savedUsername.isEmpty()) {
            etUsername.setText(savedUsername);
            username[0] = savedUsername;
            cbRememberUsername.setChecked(true);
        }
        if (rememberPassword && !savedPassword.isEmpty()) {
            etPassword.setText(savedPassword);
            password[0] = savedPassword;
            cbRememberPassword.setChecked(true);
        }

        desiredUser[0]= Database.getUser(username[0]);

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                password[0] = editable.toString();
                // Only try to get user if we have both username and password
                if (!username[0].isEmpty() && !password[0].isEmpty() && username[0].startsWith("@")) {
                    desiredUser[0] = Database.getUser(username[0]);
                } else {
                    desiredUser[0] = null;
                }
            }
        });

        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                username[0] = editable.toString();
                // Only try to get user if we have both username and password
                if (!username[0].isEmpty() && !password[0].isEmpty() && username[0].startsWith("@")) {
                    desiredUser[0] = Database.getUser(username[0]);
                } else {
                    desiredUser[0] = null;
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username[0].isEmpty() || password[0].isEmpty()) {
                    Feedback(thisContext, "Please enter both username and password");
                } else if (!username[0].startsWith("@")) {
                    Feedback(thisContext, "Usernames must start with @");
                } else {
                    // Get fresh user data when login is attempted
                    desiredUser[0] = Database.getUser(username[0]);
                    
                    if (desiredUser[0] == null) {
                        Feedback(thisContext, "User not found");
                    } else if (!desiredUser[0].getPassword().equals(password[0])) {
                        Feedback(thisContext, "Password does not match");
                    } else {
                        // Save credentials if checkboxes are checked
                        SharedPreferences.Editor editor = prefs.edit();
                        
                        if (cbRememberUsername.isChecked()) {
                            editor.putString("username", username[0]);
                            editor.putBoolean("remember_username", true);
                        } else {
                            editor.remove("username");
                            editor.putBoolean("remember_username", false);
                        }
                        
                        if (cbRememberPassword.isChecked()) {
                            editor.putString("password", password[0]);
                            editor.putBoolean("remember_password", true);
                        } else {
                            editor.remove("password");
                            editor.putBoolean("remember_password", false);
                        }
                        
                        editor.apply();

                        GlobalVariables.loggedUser = Optional.of(desiredUser[0]);
                        Feedback(thisContext, "Logged in successfully");
                        originalLoginBtn.setText(username[0]);

                        originalLoginBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                openUserPage(thisContext, username[0]);
                            }
                        });
                        loginDialog.dismiss();
                    }
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignupActivity(thisContext);
            }
        });

        loginDialog.show();
        Log.i("Utilities: openLoginDialog", "Login dialog opened");
    }

    public static void openUserOptionsDialog(Context thisContext, User user) {
        Dialog dialog = new Dialog(thisContext);
        dialog.setContentView(R.layout.activity_user_options_dialog);
        dialog.show();
        // TODO, add dialog logic
    }

    public static void openPlaylistOptionsDialog(Context thisContext, String playlistTitle) {
        Dialog dialog = new Dialog(thisContext);
        dialog.setContentView(R.layout.activity_playlist_options_dialog);
        dialog.show();
        // TODO, add dialog logic
    }

    public static void Feedback(Context contextThis, String message) {
        Log.i("Utilities: Feedback", "Feedback sent: "+message);
        Log.i("Utilities: Feedback", "Feedback sent from: "+contextThis.toString());
        Toast.makeText(contextThis, message, Toast.LENGTH_SHORT).show();
    }

    public static void updateUserPageButton(Context thisContext ,Button btnUserPage) {
        if(GlobalVariables.loggedUser.isEmpty()) {
            btnUserPage.setText("Login");
            btnUserPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openLoginDialog(thisContext,btnUserPage);
                }
            });
        } else {
            btnUserPage.setText(GlobalVariables.loggedUser.get().getName());
            btnUserPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.openUserPage(thisContext, GlobalVariables.loggedUser.get().getName());
                }
            });
        }
    }

    // Video may be expected in edge case to be given as null, function demands it not to be
    public static void EvaluateVideo(@NonNull Video video) {
        Integer score = video.getUpvotes() - video.getDownvotes();
        video.setScore(score);
    }

    public static void EvaluatePlaylist(@NonNull Playlist playlist) {
        Integer score = playlist.getUpvotes() - playlist.getDownvotes();
        playlist.setScore(score);
    }

    public static Bitmap processAndCompressImage(Bitmap originalBitmap, int maxWidth, int maxHeight) {
        if (originalBitmap == null) return null;
        
        try {
            int width = originalBitmap.getWidth();
            int height = originalBitmap.getHeight();
            
            // Calculate scaling factor
            float scale = Math.min(
                (float) maxWidth / width,
                (float) maxHeight / height
            );
            
            // Only scale down if needed
            if (scale < 1) {
                int newWidth = Math.round(width * scale);
                int newHeight = Math.round(height * scale);
                
                return Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            }
            
            return originalBitmap;
        } catch (Exception e) {
            Log.e("Utilities", "Error processing image", e);
            return originalBitmap;
        }
    }

}
