package school.videopirateapp.Dialogs;

import static school.videopirateapp.Utilities.Feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import school.videopirateapp.datastructures.Comment;
import school.videopirateapp.datastructures.Video;
import school.videopirateapp.database.Database;
import school.videopirateapp.GlobalVariables;
import school.videopirateapp.R;

public class CommentOptionsDialogActivity extends AppCompatActivity {

    private Button btnDeleteComment;
    private Button btnCommentPage;
    private Button btnEditComment;
    // private Button btnReply;  // Commented out to disable reply functionality
    private Button btnUpvote;
    private Button btnDownvote;
    private TextView tvContext;
    private TextView tvScore;
    
    private Comment comment;
    private Video video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_options_dialog);

        // Initialize views
        btnDeleteComment = findViewById(R.id.CommentOptions_Dialog_Button_DeleteComment);
        btnCommentPage = findViewById(R.id.CommentOptions_Dialog_Button_CommentPage);
        btnEditComment = findViewById(R.id.CommentOptions_Dialog_Button_EditComment);
        // btnReply = findViewById(R.id.CommentOptions_Dialog_Button_Reply);  // Commented out
        btnUpvote = findViewById(R.id.CommentOptions_Dialog_Button_Upvote);
        btnDownvote = findViewById(R.id.CommentOptions_Dialog_Button_Downvote);
//        tvContext = findViewById(R.id.CommentOptions_Dialog_TextView_Context);
        tvScore = findViewById(R.id.CommentOptions_Dialog_TextView_Score);

        String videoTitle = getIntent().getStringExtra("videoTitle");
        String commentText = getIntent().getStringExtra("commentText");
        String commentAuthor = getIntent().getStringExtra("commentAuthor");

        video = Database.getVideo(videoTitle);

        comment=new Comment();

        tvContext.setText(comment.getText());
        tvScore.setText(String.valueOf(comment.getScore()));

        // Set up button click listeners
        btnDeleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GlobalVariables.loggedUser.isPresent()) {
                    Feedback(CommentOptionsDialogActivity.this, "Please log in to delete comments");
                    return;
                }

                // Check if user is the comment author
                if (!comment.getAuthor().equals(GlobalVariables.loggedUser.get().getName())) {
                    Feedback(CommentOptionsDialogActivity.this, "You can only delete your own comments");
                    return;
                }

                // Remove the comment from the video's comments
                if (video.getComments().remove(comment)) {
                    // Update the video in the database
                    Database.updateVideo(video);
                    Feedback(CommentOptionsDialogActivity.this, "Comment deleted successfully");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Feedback(CommentOptionsDialogActivity.this, "Failed to delete comment");
                }
            }
        });

        btnEditComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GlobalVariables.loggedUser.isPresent()) {
                    Feedback(CommentOptionsDialogActivity.this, "Please log in to edit comments");
                    return;
                }

                // Check if user is the comment author
                if (!comment.getAuthor().equals(GlobalVariables.loggedUser.get().getName())) {
                    Feedback(CommentOptionsDialogActivity.this, "You can only edit your own comments");
                    return;
                }

                // TODO: Implement edit comment functionality
                Feedback(CommentOptionsDialogActivity.this, "Edit comment functionality coming soon");
            }
        });

        btnUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GlobalVariables.loggedUser.isPresent()) {
                    Feedback(CommentOptionsDialogActivity.this, "Please log in to vote");
                    return;
                }

                Database.upvoteComment(comment);
                tvScore.setText(String.valueOf(comment.getScore()));

            }
        });

        btnDownvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GlobalVariables.loggedUser.isPresent()) {
                    Feedback(CommentOptionsDialogActivity.this, "Please log in to vote");
                    return;
                }

                Database.downvoteComment(comment);
                tvScore.setText(String.valueOf(comment.getScore()));
            }
        });

        // btnReply.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         if (!GlobalVariables.loggedUser.isPresent()) {
        //             Feedback(CommentOptionsDialogActivity.this, "Please log in to reply");
        //             return;
        //         }
        //
        //         // TODO: Implement reply functionality
        //         Feedback(CommentOptionsDialogActivity.this, "Reply functionality coming soon");
        //     }
        // });

        btnCommentPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}