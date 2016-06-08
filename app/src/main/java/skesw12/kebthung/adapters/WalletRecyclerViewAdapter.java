package skesw12.kebthung.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.itangqi.waveloadingview.WaveLoadingView;
import skesw12.kebthung.R;
import skesw12.kebthung.models.Note;
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
        @BindView(R.id.edit_button) CircleButton editButton;
        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.plus) ImageView plus;
        public FooterViewHolder (View view) {
            super (view);
            ButterKnife.bind(this,view);
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
            if (ratio<=50){
                itemHolder.balance_wave.setCenterTitleColor(Color.BLACK);
                if (ratio<=15){
                    itemHolder.balance_wave.setWaveColor(Color.rgb(255,69,69));
                    itemHolder.balance_wave.setBorderColor(Color.rgb(255,69,69));
                }else{
                    itemHolder.balance_wave.setWaveColor(Color.rgb(255,187,52));
                    itemHolder.balance_wave.setBorderColor(Color.rgb(255,187,52));
                }
            }else {
                itemHolder.balance_wave.setCenterTitleColor(Color.WHITE);
                itemHolder.balance_wave.setWaveColor(Color.rgb(153,204,1));
                itemHolder.balance_wave.setBorderColor(Color.rgb(153,204,1));
            }
            final NoteListViewAdapter noteListViewAdapter = new NoteListViewAdapter(context,R.layout.note_cell,wallet.getNotes());
            itemHolder.noteListView.setAdapter(noteListViewAdapter);
            itemHolder.noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Note note = noteListViewAdapter.getItem(position);
                    showNoteDetialDialog(context,note);
                }
            });
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
            itemHolder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showEditWalletDialog(context,wallet);
                }
            });
        }else {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            footerHolder.plus.setColorFilter(R.color.colorPrimary, PorterDuff.Mode.MULTIPLY);
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
    private void showCreateWalletDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        builder.setView(inflater.inflate(R.layout.create_wallet_dialog,null))
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog dialogBox = (Dialog)dialog;
                        EditText nameText = (EditText) dialogBox.findViewById(R.id.wallet_name_input);
                        String name = nameText.getText().toString();
                        if (name.equals("")) {
                            showMessegeDialog(context,"Please Input Name of Wallet!");
                            return;
                        }
                        EditText amountText = (EditText) dialogBox.findViewById(R.id.wallet_amount_input);
                        String amountStr = amountText.getText().toString();
                        if (amountStr.equals("")) {
                            showMessegeDialog(context,"Please Input Current Amount of Money!");
                            return;
                        }
                        User.getInstance().addWallet(new Wallet(name,Double.parseDouble(amountStr)));
                        notifyChanged();
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
    private void showGetMonneyDialog(final Context context, final Wallet wallet, final NoteListViewAdapter adapter){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View getDialogView = inflater.inflate(R.layout.get_money_dialog,null);
        builder.setView(getDialogView)
                .setPositiveButton("GET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog dialogBox = (Dialog)dialog;
                        TextView amountGetText = (TextView) dialogBox.findViewById(R.id.get_amount_input);
                        String amountStr = amountGetText.getText().toString();
                        if (amountStr.equals("")) {
                            showMessegeDialog(context,"Please Input Amount of Money!");
                            return;
                        }
                        TextView purposeText = (TextView) dialogBox.findViewById(R.id.purpose_get_box);
                        String purposeStr = purposeText.getText().toString();
                        if (purposeStr.equals("")) {
                            showMessegeDialog(context,"Please Input Purpose!");
                            return;
                        }
                        wallet.getMoney(purposeStr,Double.parseDouble(amountStr));
                        notifyChanged();
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

    private void showPayMoneyDialog(final Context context, final Wallet wallet, final NoteListViewAdapter adapter){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View payDialogView = inflater.inflate(R.layout.pay_money_dialog,null);
        builder.setView(payDialogView)
                .setPositiveButton("PAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog dialogBox = (Dialog)dialog;
                        TextView amountPayText = (TextView) dialogBox.findViewById(R.id.pay_amount_input);
                        String amountStr = amountPayText.getText().toString();
                        if (amountStr.equals("")) {
                            showMessegeDialog(context,"Please Input Amount of Money!");
                            return;
                        }
                        TextView purposeText = (TextView) dialogBox.findViewById(R.id.purpose_pay_box);
                        String purposeStr = purposeText.getText().toString();
                        if (purposeStr.equals("")) {
                            showMessegeDialog(context,"Please Input Purpose!");
                            return;
                        }
                        if(!wallet.payMoney(purposeStr,Double.parseDouble(amountStr))){
                            showMessegeDialog(context,"Don't have enough money!");
                            return;
                        }
                        notifyChanged();
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

    private void showTransferDialog(final Context context, final Wallet wallet, final NoteListViewAdapter adapter){
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
                        TextView amountTransferText = (TextView) dialogBox.findViewById(R.id.transfer_amount_input);
                        Spinner transferSpinner = (Spinner) dialogBox.findViewById(R.id.transter_list);
                        String amountStr = amountTransferText.getText().toString();
                        if (amountStr.equals("")) {
                            showMessegeDialog(context,"Please Input Amount of Money!");
                            return;
                        }
                        TextView purposeText = (TextView) dialogBox.findViewById(R.id.purpose_transfer_box);
                        String purposeStr = purposeText.getText().toString();
                        if (purposeStr.equals("")) {
                            showMessegeDialog(context,"Please Input Purpose!");
                            return;
                        }
                        Wallet des = (Wallet) transferSpinner.getSelectedItem();
                        if (des==null) {
                            showMessegeDialog(context,"Please Input Destination");
                            return;
                        }
                        if (!wallet.transfer(purposeStr,des ,Double.parseDouble(amountStr))){
                            showMessegeDialog(context,"Don't have enough money!");
                            return;
                        }
                        notifyChanged();
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

    private void showNoteDetialDialog(final Context context, final Note note){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.note_detial_dialog,null);
        LinearLayout head = (LinearLayout) view.findViewById(R.id.note_head);
        head.setBackgroundResource(note.getColor());
        TextView noteType = (TextView) view.findViewById(R.id.note_type_text);
        TextView noteDetailText = (TextView) view.findViewById(R.id.note_detail_text);
        noteType.setText(note.getType());
        String noteDetail = String.format("Amount : %.2f\nDate : %s\nPurpose : %s",note.getAmount(),note.getFormattedTime(),note.getPurpose());
        if (note.getDesName()!=null) noteDetail+="\nDestination "+note.getDesName();
        noteDetailText.setText(noteDetail);
        builder.setView(view)
                .setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog dialogBox = (Dialog)dialog;
                        showEditNoteDialog(dialogBox.getContext(),note);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final Dialog dialog = builder.create();
        Button deleteNoteButton = (Button) view.findViewById(R.id.note_delete_button);
        deleteNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.onDelete();
                notifyChanged();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void showDeleteWalletDialog(Context context, final Wallet wallet){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Sure to Delete "+wallet.getName()+"?")
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User.getInstance().removeWallet(wallet);
                        notifyChanged();
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

    private void showEditNoteDialog(final Context context, final Note note){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.note_edit_dialog,null);
        EditText editPurposeText = (EditText) view.findViewById(R.id.edit_purpose_input);
        editPurposeText.setText(note.getPurpose());
        builder.setView(view)
                .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog dialogBox = (Dialog)dialog;
                        EditText editPurposeText = (EditText) dialogBox.findViewById(R.id.edit_purpose_input);
                        String purposeStr = editPurposeText.getText().toString();
                        if (purposeStr.equals("")) {
                            showMessegeDialog(context,"Please Input Purpose!");
                            return;
                        }
                        note.setPurpose(editPurposeText.getText().toString());
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

    private void showMessegeDialog(Context context,String messege){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(messege)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
    private void notifyChanged(){
        notifyDataSetChanged();
        User.getInstance().saveFile(context);
    }
    private void showEditWalletDialog(final Context context, final Wallet wallet){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.edit_wallet_dialog,null);
        EditText editText = (EditText) view.findViewById(R.id.wallet_name_input);
        editText.setText(wallet.getName());
        builder.setView(view)
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog dialogBox = (Dialog) dialog;
                        EditText nameText = (EditText) dialogBox.findViewById(R.id.wallet_name_input);
                        if (nameText.getText().toString().equals("")){
                            showMessegeDialog(context,"Please Input Wallet Name!");
                            return;
                        }
                        wallet.setName(nameText.getText().toString());
                        notifyChanged();
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
