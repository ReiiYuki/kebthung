package skesw12.kebthung.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import skesw12.kebthung.R;
import skesw12.kebthung.models.Wallet;

/**
 * Created by YukiReii on 3/6/2559.
 */
public class WalletRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Wallet> wallets;
    private Context context;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View view) {
            super(view);

        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder (View view) {
            super (view);
        }
    }

    public WalletRecyclerViewAdapter(Context context,List<Wallet> wallets){
        this.context = context;
        this.wallets = wallets;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER){
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.create_wallet_layout, parent, false);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(getClass().getName(), "On click");
                }
            });
            return new FooterViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder){

        }
    }

    private boolean isPositionFooter (int position) {
        if (wallets.size()==0) return position==0;
        return position == wallets.size() + 1;
    }

    @Override
    public int getItemCount () {
        return wallets.size () + 1;
    }

    @Override
    public int getItemViewType (int position) {
        if(isPositionFooter (position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }
}
