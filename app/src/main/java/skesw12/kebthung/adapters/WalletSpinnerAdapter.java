package skesw12.kebthung.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import skesw12.kebthung.R;
import skesw12.kebthung.models.Wallet;

/**
 * Created by YukiReii on 4/6/2559.
 */
public class WalletSpinnerAdapter extends ArrayAdapter<Wallet>{
    private final int TOTAL = 0;
    private final int ITEM = 1;
    public WalletSpinnerAdapter(Context context, int resource, List<Wallet> objects) {
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
        Wallet wallet = getItem(position);
        if (position==0) spinnerItem.setText("Total");
        else spinnerItem.setText(wallet.getName());
        return view;
    }

    @Override
    public int getPosition(Wallet item) {
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
    public Wallet getItem(int position) {
        if (position==0) return null;
        return super.getItem(position-1);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position,convertView,parent);
    }
}
