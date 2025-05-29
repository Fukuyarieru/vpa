package school.videopirateapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;

public class Utilities {
    private Utilities() {
        throw new UnsupportedOperationException("This class is not instantiable.");
    }

    public static <T> ArrayList<T> HashMapToArrayList(@NonNull HashMap<String, T> hashMap) {
        Log.i("Utilities", "Converting HashMap to ArrrayList");
        // this function does not do any sorting
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
//        ArrayList<T> arrayList = new ArrayList<>();
//        for (T value : hashMap.values()) {
//            arrayList.add(value);
//        }
//        return arrayList;

        return new ArrayList<>(hashMap.values());
    }

    public static String TimeNow() {
        Log.i("Utilities: TimeNow", "Getting the time now");
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
        intent.putExtra("context", commentContext);
        currentActivityThis.startActivity(intent);
    }

    public static void openCommentOptionsDialog(Context contextThis, Comment comment) {
        Log.i("Utilities: openCommentOptionsDialog", "Comment options dialog opened");
        Dialog dialog = new Dialog(contextThis);
        dialog.setContentView(R.layout.activity_comment_options_dialog);

        Button btnDeleteComment = dialog.findViewById(R.id.CommentOptions_Dialog_Button_DeleteComment);
        Button btnCommentPage = dialog.findViewById(R.id.CommentOptions_Dialog_Button_CommentPage);
        Button btnEditComment = dialog.findViewById(R.id.CommentOptions_Dialog_Button_EditComment);
        TextView tvContext = dialog.findViewById(R.id.CommentOptions_Dialog_TextView_Context);
        TextView tvScore = dialog.findViewById(R.id.CommentOptions_Dialog_TextView_Score);
        Button btnDownvote = dialog.findViewById(R.id.CommentOptions_Dialog_Button_Downvote);
        Button btnUpvote = dialog.findViewById(R.id.CommentOptions_Dialog_Button_Upvote);
        Button btnReply = dialog.findViewById(R.id.CommentOptions_Dialog_Button_Reply);

        tvContext.setText(comment.getContext());
        btnDeleteComment.setText("Delete Comment");
        btnCommentPage.setText("Comment Page");
        btnEditComment.setText("Edit Comment");
        tvScore.setText("0"); // TODO, score for replies not implemented yet
        btnDownvote.setText("\uD83D\uDC4E");
        btnUpvote.setText("\uD83D\uDC4D");
        btnReply.setText("Reply");

        btnDeleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Utilities: openCommentOptionsDialog", "TODO: DELETE_COMMENT");
                Feedback(contextThis, "TODO: DELETE_COMMENT");
            }
        });

        btnCommentPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Utilities: openCommentOptionsDialog", "Opening comment page");
                openCommentPage(contextThis, comment.getContext());
            }
        });

        btnEditComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Utilities: openCommentOptionsDialog", "TODO: EDIT_COMMENT");
                Feedback(contextThis, "TODO: EDIT_COMMENT");
            }
        });

        btnDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Utilities: openCommentOptionsDialog", "TODO: DOWNVOTE");
                Feedback(contextThis, "TODO: DOWNVOTE");
            }
        });

        btnUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Utilities: openCommentOptionsDialog", "TODO: UPVOTE");
                Feedback(contextThis, "TODO: UPVOTE");
            }
        });

        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Utilities: openCommentOptionsDialog", "TODO: REPLY");
                Feedback(contextThis, "TODO: REPLY");
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


    public static void openLoginDialog(Context thisContext, Button originalLoginBtn) {
        // TODO, very stupid solutions that work

        Dialog loginDialog = new Dialog(thisContext); //this screen as context
        loginDialog.setContentView(R.layout.activity_login_dialog);
        EditText etUsername = loginDialog.findViewById(R.id.Login_Dialog_EditText_Username);
        EditText etPassword = loginDialog.findViewById(R.id.Login_Dialog_EditText_Password);
        Button btnLogin = loginDialog.findViewById(R.id.Login_Dialog_Button_Login);
        Button btnSignup = loginDialog.findViewById(R.id.Login_Dialog_Button_Signup);

        final User[] desiredUser = {User.Default()};

        final String[] username = {etUsername.getText().toString()};
        final String[] password = {etPassword.getText().toString()};

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password[0] =editable.toString();

                if (username[0].isEmpty() || password[0].isEmpty()) {
                } else if (!username[0].startsWith("@")) {
                } else {
                    desiredUser[0] =Database.getUser(username[0]);
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
                username[0] =editable.toString();

                if (username[0].isEmpty() || password[0].isEmpty()) {
                } else if (!username[0].startsWith("@")) {
                } else {
                    desiredUser[0] =Database.getUser(username[0]);
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Feedback(thisContext,username);

                if (username[0].isEmpty() || password[0].isEmpty()) {
                    // Display a message if the fields are empty
                    Feedback(thisContext, "Please enter both username and password");
                } else if (!username[0].startsWith("@")) {
                    Feedback(thisContext, "Usernames must start with @");
                } else {
//                    User desiredUser=Database.getUser(username[0]);
//                    Feedback(thisContext,desiredUser.toString());
                    if (desiredUser[0] == null) { // TODO, BUG HERE, I DONT KNOW WHY, (SKIPS CHECKING PASSWORD), desiredUser will always be default if not found
                        Feedback(thisContext, "User was not found");
                    } else if (!desiredUser[0].getPassword().equals(password[0])) {
                        Feedback(thisContext, "Password does not match");
                    } else {
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
                // TODO, check if etVideoTitle is empty?
                String chosenTitle = etVideoTitle.getText().toString();
//                if chosenTitle=""  // add a function named Video.IsProperName(videoName) which returns true if good and false if bad;
                if (Database.getVideo(chosenTitle) != null) {
                    Feedback(thisContext, "Video with that title already exists");
                } else {
                    if (GlobalVariables.loggedUser.isPresent()) {
                        Video newVideo = new Video(chosenTitle, GlobalVariables.loggedUser.get().getName());
                        Feedback(thisContext, "Added video named: " + newVideo.getTitle());
                        Database.addVideo(newVideo);
                        uploadDialog.dismiss();
                    } // but why?
                    else {
                        Feedback(thisContext, "You need to be logged in to upload videos");
                    }
                }
            }
        });
        btnChooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public static void openVideoOptionsDialog(Context thisContext, Video video) {
        Dialog dialog = new Dialog(thisContext);
        dialog.setContentView(R.layout.activity_video_option_dialog);

        Button btnDeleteVideo = dialog.findViewById(R.id.VideoOptions_Dialog_Button_DeleteVideo);
        Button btnEditVideo = dialog.findViewById(R.id.VideoOptions_Dialog_Button_EditVideo);
        Button btnAddVideoToPlaylist = dialog.findViewById(R.id.VideoOptions_Dialog_Button_AddVideoToPlaylist);

        btnEditVideo.setText("Edit Video");
        btnDeleteVideo.setText("Delete Video");
        btnAddVideoToPlaylist.setText("Add Video to Playlist");

        btnDeleteVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Utilities: openVideoOptionsDialog", "TODO: DELETE_VIDEO");
                Feedback(thisContext, "TODO: DELETE_VIDEO");
            }
        });

        btnEditVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Utilities: openVideoOptionsDialog", "TODO: EDIT_VIDEO");
                Feedback(thisContext, "TODO: EDIT_VIDEO");
            }
        });

        btnAddVideoToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Utilities: openVideoOptionsDialog", "TODO: ADD_VIDEO_TO_PLAYLIST");
                Feedback(thisContext, "TODO: ADD_VIDEO_TO_PLAYLIST");
            }
        });

        dialog.show();
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
        Integer score = 0;
        score += video.getViews();
        score += video.getUpvotes() * 15;
        score += video.getDownvotes() * 15;
        score += video.getComments().size() * 10;
        video.setScore(score);
    }

}
