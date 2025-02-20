package school.videopirateapp.ListViewComponents;

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
import java.util.List;

import school.videopirateapp.DataStructures.Playlist;
import school.videopirateapp.R;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {

    private Context context;
    private int resource;
    private ArrayList<Playlist> playlists;

    // android studio created constructor for custom listView adapters
    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.playlists =(ArrayList<Playlist>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE); // SO THIS PIECE OF SHIT OF LINE DID ALL THE MAGIC WORK WHICH I COULDNT SEE

        View view = layoutInflater.inflate(this.resource,null);
        Playlist playlist= playlists.get(position);

        // TEXTVIEWS ASSEMBLE
        TextView tvTitle=view.findViewById(R.id.Playlist_ListView_Component_TextView_Title);
        TextView tvDate=view.findViewById(R.id.Playlist_ListView_Component_TextView_Date);
        TextView tvViews=view.findViewById(R.id.Playlist_ListView_Component_TextView_ViewCount);
        TextView tvVideosCount=view.findViewById(R.id.Playlist_ListView_Component_TextView_VideoCount);
        TextView tvDescription=view.findViewById(R.id.Playlist_ListView_Component_TextView_Description);
        ImageView image=view.findViewById(R.id.Playlist_ListView_Component_ImageView_Image);

        tvTitle.setText(playlist.getTitle());
        tvDate.setText("DATE NOT IMPLEMENTED YET");
        tvViews.setText("VIEWS NOT IMPLEMENTED YET");
        tvVideosCount.setText("Video Count: "+playlist.getVideos().size());
        tvDescription.setText(playlist.getPlaylistDescription());


        return view;
    }

    @Override
    public int getCount() {
        return this.playlists.size();
    }
}
