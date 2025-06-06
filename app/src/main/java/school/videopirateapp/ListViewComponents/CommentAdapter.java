package school.videopirateapp.ListViewComponents;

import static school.videopirateapp.Utilities.ByteArrayToBitmap;
import static school.videopirateapp.Utilities.openUserPage;
import static school.videopirateapp.Utilities.openCommentOwnerOptionsDialog;
import static school.videopirateapp.Utilities.openCommentViewerOptionsDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.Database.Database;
import school.videopirateapp.R;
import school.videopirateapp.GlobalVariables;

public class CommentAdapter extends ArrayAdapter<Comment> {
    private Context context;
    private int resource;
    private ArrayList<Comment> comments;

    public CommentAdapter(@NonNull Context context, int resource, @NonNull List<Comment> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.comments = (ArrayList<Comment>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(this.resource, null);

        Comment comment = comments.get(position);

        TextView tvComment = view.findViewById(R.id.Comment_ListView_Component_TextView_Comment);
        TextView tvAuthor = view.findViewById(R.id.Comment_ListView_Component_TextView_UserName);
        TextView tvDate = view.findViewById(R.id.Comment_ListView_Component_TextView_Date);
//        TextView tvContext = view.findViewById(R.id.Comment_ListView_Component_TextView_Context);
        ImageView userImage = view.findViewById(R.id.Comment_ListView_Component_ImageView_UserImage);
//        TextView tvReplies = view.findViewById(R.id.Comment_ListView_Component_TextView_Replies);

        tvComment.setText(comment.getComment());
        tvAuthor.setText(comment.getAuthor());
        tvDate.setText(comment.getDate());
        // tvContext.setText(comment.getContext());  // Commented out

        User author = Database.getUser(comment.getAuthor());
        if (author != null) {
            userImage.setImageBitmap(ByteArrayToBitmap(author.getImage()));
        }

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserPage(context, comment.getAuthor());
            }
        });

        // Add click listener for comment to open options dialog
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalVariables.loggedUser.isPresent() && GlobalVariables.loggedUser.get().getName().equals(comment.getAuthor())) {
                    openCommentOwnerOptionsDialog(context, comment);
                } else {
                    openCommentViewerOptionsDialog(context, comment);
                }
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        return this.comments.size();
    }
}
