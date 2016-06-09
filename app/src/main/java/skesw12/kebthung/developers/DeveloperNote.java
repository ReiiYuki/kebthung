package skesw12.kebthung.developers;

import java.util.Calendar;

/**
 * Created by YukiReii on 6/9/2016.
 */
public class DeveloperNote {
    private String tile;
    private String note;
    public DeveloperNote(String tile,String note){
        this.note = note;
        this.tile = tile;
    }

    public String getNote() {
        return note;
    }

    public String getTile() {
        return tile;
    }

    @Override
    public String toString() {
        return tile;
    }
}
