package skesw12.kebthung.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.itangqi.waveloadingview.WaveLoadingView;
import skesw12.kebthung.R;
import skesw12.kebthung.models.User;
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
        @BindView(R.id.wallet_name) TextView wallet_name;
        @BindView(R.id.waveBalance) WaveLoadingView balance_wave;
        @BindView(R.id.pay_button) Button payButton;
        @BindView(R.id.get_button) Button getButton;
        @BindView(R.id.transfer_button) Button transferButton;
        @BindView(R.id.note_listview) ListView noteListView;
        @BindView(R.id.delete_wallet_button) CircleButton deleteWalletButton;
        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
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
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER){
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.create_wallet_layout, parent, false);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCreateWalletDialog(parent.getContext());
                }
            });
            return new FooterViewHolder(v);
        }else if (viewType==TYPE_ITEM){
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.wallet_layout, parent, false);
            return new ItemViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder){
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            final Wallet wallet = wallets.get(position);
            int ratio = (int) (wallet.getBalance()/wallet.getMaxBalance()*100);
            itemHolder.wallet_name.setText(wallet.getName());
            itemHolder.balance_wave.setCenterTitle(String.format("%.2f",wallet.getBalance()));
            itemHolder.balance_wave.setProgressValue(ratio);
            if (ratio<50){
                itemHolder.balance_wave.setCenterTitleColor(Color.BLACK);
            }else {
                itemHolder.balance_wave.setCenterTitleColor(Color.WHITE);
            }
            final NoteListViewAdapter noteListViewAdapter = new NoteListViewAdapter(context,R.layout.note_cell,wallet.getNotes());
            itemHolder.noteListView.setAdapter(noteListViewAdapter);
            itemHolder.getButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showGetMonneyDialog(context,wallet,noteListViewAdapter);
                }
            });
            itemHolder.payButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPayMoneyDialog(context,wallet,noteListViewAdapter);
                }
            });
            itemHolder.transferButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTransferDialog(context,wallet,noteListViewAdapter);
                }
            });
            itemHolder.deleteWalletButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteWalletDialog(context,wallet);
                }
            });
        }
    }

    private boolean isPositionFooter (int position) {
        return position == wallets.size();
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
    private void showCreateWalletDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        builder.setView(inflater.inflate(R.layout.create_wallet_dialog,null))
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog dialogBox = (Dialog)dialog;
                        EditText nameText = (EditText) dialogBox.findViewById(R.id.wallet_name_input);
                        EditText amountText = (EditText) dialogBox.findViewById(R.id.wallet_amount_input);
                        User.getInstance().addWallet(new Wallet(nameText.getText().toString(),Double.parseDouble(amountText.getText().toString())));
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
    private void showGetMonneyDialog(Context context, final Wallet wallet, final NoteListViewAdapter adapter){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View getDialogView = inflater.inflate(R.layout.get_money_dialog,null);
        builder.setView(getDialogView)
                .setPositiveButton("GET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog dialogBox = (Dialog)dialog;
                        TextView amountGetText = (TextView) dialogBox.findViewById(R.id.get_amount_input);
                        TextView purposeText = (TextView) dialogBox.findViewById(R.id.purpose_get_box);
                        wallet.getMoney(purposeText.getText().toString(),Double.parseDouble(amountGetText.getText().toString()));
                        notifyDataSetChanged();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    private void showPayMoneyDialog(Context context, final Wallet wallet, final NoteListViewAdapter adapter){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View payDialogView = inflater.inflate(R.layout.pay_money_dialog,null);
        builder.setView(payDialogView)
                .setPositiveButton("PAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog dialogBox = (Dialog)dialog;
                        TextView amountPayText = (TextView) dialogBox.findViewById(R.id.pay_amount_input);
                        TextView purposeText = (TextView) dialogBox.findViewById(R.id.purpose_pay_box);
                        wallet.payMoney(purposeText.getText().toString(),Double.parseDouble(amountPayText.getText().toString()));
                        notifyDataSetChanged();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    private void showTransferDialog(Context context, final Wallet wallet, final NoteListViewAdapter adapter){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View getDialogView = inflater.inflate(R.layout.transfer_money_dialog,null);
        Spinner transferSpinner = (Spinner) getDialogView.findViewById(R.id.transter_list);
        ArrayAdapter<Wallet> spinnerAdapter = new ArrayAdapter<Wallet>(context,R.layout.transfer_spinner_layout,User.getInstance().getTransferList(wallet));
        transferSpinner.setAdapter(spinnerAdapter);
        builder.setView(getDialogView)
                .setPositiveButton("TRANSFER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog dialogBox = (Dialog)dialog;
                        TextView amountPayText = (TextView) dialogBox.findViewById(R.id.pay_amount_input);
                        Spinner transferSpinner = (Spinner) dialogBox.findViewById(R.id.transter_list);
                        TextView purposeText = (TextView) dialogBox.findViewById(R.id.purpose_transfer_box);
                        wallet.transfer(purposeText.getText().toString(),(Wallet) transferSpinner.getSelectedItem(),Double.parseDouble(amountPayText.getText().toString()));
                        notifyDataSetChanged();
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    private void showNoteDetialDialog(){

    }

    private void showDeleteWalletDialog(Context context, final Wallet wallet){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Sure to Delete "+wallet.getName()+"?")
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User.getInstance().removeWallet(wallet);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
}
