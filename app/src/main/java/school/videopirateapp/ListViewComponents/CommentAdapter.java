package school.videopirateapp.ListViewComponents;

import static school.videopirateapp.Utilities.ByteArrayToBitmap;
import static school.videopirateapp.Utilities.openCommentOptionsDialog;
import static school.videopirateapp.Utilities.openUserPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.User;
import school.videopirateapp.Database.Database;
import school.videopirateapp.R;
import school.videopirateapp.Utilities;

public class CommentAdapter extends ArrayAdapter<Comment> {
    private Context context;
    private int resource;
    private ArrayList<Comment> comments;
    public CommentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Comment> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.comments =(ArrayList<Comment>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater=(LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(this.resource,null);
        Comment comment= comments.get(position);

        TextView tvUserName=view.findViewById(R.id.Comment_ListView_Component_TextView_UserName);
        TextView tvComment=view.findViewById(R.id.Comment_ListView_Component_TextView_Comment);
        TextView tvContext=view.findViewById(R.id.Comment_ListView_Component_TextView_Context);
        TextView tvDate=view.findViewById(R.id.Comment_ListView_Component_TextView_Date);
        ImageView userImage=view.findViewById(R.id.Comment_ListView_Component_ImageView_UserImage);

        tvComment.setText(comment.getComment());
        tvUserName.setText(comment.getAuthor());
        tvContext.setText(comment.getContext());
        tvDate.setText(comment.getDate());

        User user= Database.getUser(comment.getAuthor());
        try {
            userImage.setImageBitmap(ByteArrayToBitmap(user.getImage()));
        } catch (Exception e) {
            userImage.setImageResource(R.drawable.default_user_image);
        }
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserPage(CommentAdapter.super.getContext(),comment.getAuthor());
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCommentOptionsDialog(CommentAdapter.super.getContext(),comment);
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        return this.comments.size();
    }
}
