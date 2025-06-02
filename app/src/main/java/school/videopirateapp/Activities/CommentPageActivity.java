package school.videopirateapp.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.Database.Database;
import school.videopirateapp.GlobalVariables;
import school.videopirateapp.ListViewComponents.CommentAdapter;
import school.videopirateapp.R;
import school.videopirateapp.Utilities;

public class CommentPageActivity extends AppCompatActivity {
    private String videoTitle;
    private Video video;
    private ListView commentListView;
    private CommentAdapter commentAdapter;
    private EditText etNewComment;
    private Button btnAddComment;
    private TextView tvNoComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_page);

        // Get the video title from the intent
        videoTitle = getIntent().getStringExtra("videoTitle");
        if (videoTitle == null) {
            Toast.makeText(this, "Error: Video title not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize views
        commentListView = findViewById(R.id.CommentPage_ListView_Comments);
        etNewComment = findViewById(R.id.CommentPage_EditText_NewComment);
        btnAddComment = findViewById(R.id.CommentPage_Button_AddComment);
        tvNoComments = findViewById(R.id.CommentPage_TextView_NoComments);

        // Get the video from the database
        video = Database.getVideo(videoTitle);
        if (video == null) {
            Toast.makeText(this, "Error: Video not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set up the comment adapter
        ArrayList<Comment> comments = video.getComments();
        commentAdapter = new CommentAdapter(this, R.layout.activity_comment_listview_component, comments);
        commentListView.setAdapter(commentAdapter);

        // Show/hide the "No Comments" message
        updateNoCommentsVisibility();

        // Set up the add comment button
        btnAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewComment();
            }
        });
    }

    private void updateNoCommentsVisibility() {
        if (video.getComments().isEmpty()) {
            tvNoComments.setVisibility(View.VISIBLE);
            commentListView.setVisibility(View.GONE);
        } else {
            tvNoComments.setVisibility(View.GONE);
            commentListView.setVisibility(View.VISIBLE);
        }
    }

    private void addNewComment() {
        String commentText = etNewComment.getText().toString().trim();
        if (commentText.isEmpty()) {
            Toast.makeText(this, "Please enter a comment", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!GlobalVariables.loggedUser.isPresent()) {
            Toast.makeText(this, "Please log in to comment", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create and add the new comment
        Comment newComment = new Comment(
            commentText,
            GlobalVariables.loggedUser.get().getName(),
            videoTitle
        );

        Database.addComment(newComment, video);
        
        // Clear the input field and update the UI
        etNewComment.setText("");
        commentAdapter.notifyDataSetChanged();
        updateNoCommentsVisibility();
        
        Toast.makeText(this, "Comment added successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the video data and update the UI
        video = Database.getVideo(videoTitle);
        if (video != null) {
            commentAdapter.clear();
            commentAdapter.addAll(video.getComments());
            commentAdapter.notifyDataSetChanged();
            updateNoCommentsVisibility();
        }
    }
}