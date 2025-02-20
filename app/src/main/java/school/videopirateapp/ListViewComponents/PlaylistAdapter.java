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

import school.videopirateapp.DataStructures.Playlist;

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
        LayoutInflater layoutInflater=(LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(this.resource,null);
        Playlist playlist= playlists.get(position);



        return view;
    }

    @Override
    public int getCount() {
        return this.playlists.size();
    }
}
