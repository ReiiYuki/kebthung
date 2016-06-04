package skesw12.kebthung.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import skesw12.kebthung.models.Wallet;

/**
 * Created by YukiReii on 4/6/2559.
 */
public class WalletSpinnerAdapter extends ArrayAdapter<Wallet>{
    public WalletSpinnerAdapter(Context context, int resource, List<Wallet> objects) {
        super(context, resource, objects);
    }
}
