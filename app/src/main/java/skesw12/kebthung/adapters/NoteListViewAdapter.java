package skesw12.kebthung.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import skesw12.kebthung.R;
import skesw12.kebthung.models.Note;

/**
 * Created by YukiReii on 3/6/2559.
 */
public class NoteListViewAdapter extends ArrayAdapter<Note>{
    public NoteListViewAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view==null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.note_cell, null);
        }
        Note note = getItem(position);
        view.setBackgroundColor(note.getColor());
        TextView typeText = (TextView) view.findViewById(R.id.note_type_text);
        typeText.setText(note.getType());
        TextView purposeText = (TextView) view.findViewById(R.id.note_purpose_text);
        purposeText.setText(note.getPurpose());
        TextView amountText = (TextView) view.findViewById(R.id.note_amount_text);
        amountText.setText(String.format("%.2f",note.getAmount()));
        TextView dateText = (TextView) view.findViewById(R.id.note_date_text);
        dateText.setText(note.getFormattedTime());
        return view;
    }
    @Override
    public Note getItem(int position) {
        return super.getItem(super.getCount() - position - 1);
    }
}
