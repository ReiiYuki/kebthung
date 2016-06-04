package skesw12.kebthung.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import skesw12.kebthung.R;
import skesw12.kebthung.models.Wish;

/**
 * Created by YukiReii on 4/6/2559.
 */
public class WishSpinnerAdapter extends ArrayAdapter<Wish> {
    private final int TOTAL = 0;
    private final int ITEM = 1;
    public WishSpinnerAdapter(Context context, int resource, List<Wish> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view==null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.transfer_spinner_layout, null);
        }
        TextView spinnerItem = (TextView) view.findViewById(R.id.spinner_item);
        Wish wish = getItem(position);
        if (position==0) spinnerItem.setText("Total");
        else spinnerItem.setText(wish.getTitle());
        return view;
    }

    @Override
    public int getPosition(Wish item) {
        return super.getPosition(item)+1;
    }

    @Override
    public int getCount() {
        return super.getCount()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0) return TOTAL;
        return ITEM;
    }

    @Override
    public Wish getItem(int position) {
        if (position==0) return null;
        return super.getItem(position-1);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}
