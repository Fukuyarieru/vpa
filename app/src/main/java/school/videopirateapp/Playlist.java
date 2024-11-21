package school.videopirateapp;


import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private List<String> items;

    public Playlist() {
        this.items = new ArrayList<String>();
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void addItem(String item) {
        items.add(item);
    }
}
