package school.videopirateapp.ListViewComponents;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import school.videopirateapp.DataStructures.Video;
import school.videopirateapp.R;

public class VideoAdapter extends ArrayAdapter<Video> {

    private Context context;
    private int resource;
    private ArrayList<Video> videos;

    // android studio created constructor for custom listView adapters
    public VideoAdapter(@NonNull Context context, int resource, @NonNull List<Video> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.videos=(ArrayList<Video>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(this.resource,null);
        TextView tvUploaderUserName=view.findViewById(R.id.Video_ListView_Component_TextView_UploaderUsername);
        TextView tvVideoTitle=view.findViewById(R.id.Video_ListView_Component_TextView_VideoTitle);
        TextView tvUpvotes=view.findViewById(R.id.Video_ListView_Component_TextView_Upvotes);
        TextView tvDownvotes=view.findViewById(R.id.Video_ListView_Component_TextView_Downvotes);
        ImageView uploaderImage=view.findViewById(R.id.Video_ListView_Component_ImageView_UploaderImage);
        return super.getView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return this.videos.size();
    }
}
