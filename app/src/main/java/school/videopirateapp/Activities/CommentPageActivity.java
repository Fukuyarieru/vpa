package school.videopirateapp.Activities;

import static school.videopirateapp.Utilities.Feedback;
import static school.videopirateapp.Utilities.updateUserPageButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.Database.Database;
import school.videopirateapp.GlobalVariables;
import school.videopirateapp.ListViewComponents.CommentAdapter;
import school.videopirateapp.R;

public class CommentPageActivity extends AppCompatActivity {

    ListView lvComments;
    CommentAdapter commentAdapter;
    EditText etNewComment;
    Button btnAddReply;
    Button btnUserPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_page);

        Database.refresh();

        String commentContext = getIntent().getStringExtra("commentContext");

        Comment comment =Database.getComment(commentContext);

        lvComments = findViewById(R.id.CommentPage_ListView_Comments);
        etNewComment = findViewById(R.id.CommentPage_EditText_NewComment);
        btnAddReply = findViewById(R.id.CommentPage_Button_AddReply);
        btnUserPage = findViewById(R.id.CommentPage_Button_UserPage);

//        ArrayList<Comment>insideComments= Comments.getCommentsFromContexts(comment.getComments());
//        commentAdapter = new CommentAdapter(this, R.layout.activity_comment_listview_component, insideComments);  // Set isReplyView to true to disable reply functionality
//        lvComments.setAdapter(commentAdapter);

        updateUserPageButton(this,btnUserPage);

        btnAddReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCommentText = etNewComment.getText().toString().trim();
                if (!newCommentText.isEmpty()) {
                    if (GlobalVariables.loggedUser.isPresent()) {
                        Comment newComment = new Comment(newCommentText, GlobalVariables.loggedUser.get().getName(), comment.getCommentsContext());  // Use default context
                        Database.addComment(newComment, newComment.getContext());
                        Database.addCommentToUser(newComment, GlobalVariables.loggedUser.get());
                        etNewComment.setText("");
                        commentAdapter.add(newComment);
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