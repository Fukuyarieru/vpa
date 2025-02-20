package school.videopirateapp.ListViewComponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Map;

import school.videopirateapp.DataStructures.Comment;
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
        ImageView userImage=view.findViewById(R.id.Comment_ListView_Component_ImageView_UserImage);
        TextView tvDate=view.findViewById(R.id.Comment_ListView_Component_TextView_Date);

        tvComment.setText(comment.getComment());
        tvUserName.setText(comment.getAuthor());
        Bitmap a= Utilities.BytyArrayToBitmap(comment.getAuthorImage()); // why cant i import utilities here properly? i could fix using this var later
        userImage.setImageBitmap(a);
        tvDate.setText(comment.getDate());

        return view;
    }

    @Override
    public int getCount() {
        return this.comments.size();
    }
}
