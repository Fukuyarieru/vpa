package school.videopirateapp.ListViewComponents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import school.videopirateapp.DataStructures.Comment;
import school.videopirateapp.DataStructures.Playlist;

public class CommentAdapter extends ArrayAdapter<Comment> {
    private Context context;
    private int resource;
    private ArrayList<Comment> comments;
    public CommentAdapter(@NonNull Context context, int resource, @NonNull List<Comment> objects) {
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



        return view;
    }

    @Override
    public int getCount() {
        return this.comments.size();
    }
}
