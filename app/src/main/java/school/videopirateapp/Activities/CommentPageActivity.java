package school.videopirateapp.Activities;

import static school.videopirateapp.Utilities.Feedback;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Comments;
import school.videopirateapp.Database.Database;
import school.videopirateapp.GlobalVariables;
import school.videopirateapp.ListViewComponents.CommentAdapter;
import school.videopirateapp.R;

public class CommentPageActivity extends AppCompatActivity {

    String inVideo;
    String commentText;
    Video video;
    ListView lvComments;
    CommentAdapter commentAdapter;
    EditText etNewComment;
    Button btnAddComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_page);

        lvComments = findViewById(R.id.CommentPage_ListView_Comments);
        etNewComment = findViewById(R.id.CommentPage_EditText_NewComment);
        btnAddComment = findViewById(R.id.CommentPage_Button_AddComment);

        inVideo = getIntent().getStringExtra("inVideo");
        commentText = getIntent().getStringExtra("commentText");

        lvComments = findViewById(R.id.CommentPage_ListView_Comments);
        etNewComment = findViewById(R.id.CommentPage_EditText_NewComment);
        btnAddComment = findViewById(R.id.CommentPage_Button_AddComment);

        video = Database.getVideo(inVideo);

        // Create adapter without reply functionality
        ArrayList<Comment>comments= Comments.getCommentsFromContexts(video.getCommentContextes());
        commentAdapter = new CommentAdapter(this, R.layout.activity_comment_listview_component, comments, true);  // Set isReplyView to true to disable reply functionality
        lvComments.setAdapter(commentAdapter);

        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCommentText = etNewComment.getText().toString().trim();
                if (!newCommentText.isEmpty()) {
                    if (GlobalVariables.loggedUser.isPresent()) {
                        Comment newComment = new Comment(newCommentText, GlobalVariables.loggedUser.get().getName(), "videos");  // Use default context
                        Database.addComment(newComment, video);
                        etNewComment.setText("");
                        commentAdapter.notifyDataSetChanged();
                        Feedback(CommentPageActivity.this, "Comment added successfully");
                    } else {
                        Feedback(CommentPageActivity.this, "Please log in to comment");
                    }
                } else {
                    Feedback(CommentPageActivity.this, "Comment cannot be empty");
                }
            }
        });
    }
}