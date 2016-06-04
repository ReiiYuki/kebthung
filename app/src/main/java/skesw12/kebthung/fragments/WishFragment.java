package skesw12.kebthung.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.itangqi.waveloadingview.WaveLoadingView;
import skesw12.kebthung.R;
import skesw12.kebthung.adapters.WalletSpinnerAdapter;
import skesw12.kebthung.adapters.WishSpinnerAdapter;
import skesw12.kebthung.models.User;
import skesw12.kebthung.models.Wallet;
import skesw12.kebthung.models.Wish;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishFragment extends Fragment {

    @BindView(R.id.wish_name) TextView wishTitleText;
    @BindView(R.id.wish_progress_wave) WaveLoadingView wishProgress;
    @BindView(R.id.wish_spinner) Spinner wishSpinner;
    @BindView(R.id.add_wish_button) Button addWishButton;
    @BindView(R.id.wallet_spinner) Spinner walletSpinner;
    @BindView(R.id.wish_target) TextView wishTargetText;
    @BindView(R.id.wish_left) TextView wishLeftText;
    @BindView(R.id.wish_deadline) TextView wishDeadlineText;
    @BindView(R.id.wish_days) TextView wishDaysLeftText;
    private ArrayAdapter<Wallet> walletAdapter;
    private ArrayAdapter<Wish> wishAdapter;
    private Wish selectedWish;
    private Wallet selectedWallet;
    public WishFragment() {    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_wish, container, false);
        ButterKnife.bind(this,rootView);
        initializeButton();
        initializeSpinner();
        refresh();
        return rootView;
    }

    private void initializeSpinner(){
        walletAdapter = new WalletSpinnerAdapter(getActivity(),R.layout.transfer_spinner_layout, User.getInstance().getWallets());
        walletSpinner.setAdapter(walletAdapter);
        walletSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedWallet = walletAdapter.getItem(position);
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedWallet = null;
                refresh();
            }
        });
        wishAdapter = new WishSpinnerAdapter(getActivity(),R.layout.transfer_spinner_layout,User.getInstance().getWishs());
        wishSpinner.setAdapter(wishAdapter);
        wishSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedWish = wishAdapter.getItem(position);
                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedWish = null;
                refresh();
            }
        });
    }

    private void refresh(){
        walletAdapter.notifyDataSetChanged();
        wishAdapter.notifyDataSetChanged();
        double target = 0;
        long deadline = 0;
        int dayLeft = 0;
        double amount = 0;
        String title = "Total";
        String targetstr = "";
        String deadlinestr = "";
        String dayLeftstr = "";
        if (selectedWish==null){
            for (Wish w:User.getInstance().getWishs()){
                if (deadline==0) {
                    deadline=w.getDeadline();
                    dayLeft=w.getDaysLeft();
                }
                if (deadline>w.getDeadline()) {
                    deadline = w.getDeadline();
                    dayLeft = w.getDaysLeft();

                }
                target+=w.getTarget();
            }
            targetstr = String.format("Total target : %.2f",target);
            dayLeftstr = String.format("%d Nearest Day Left",dayLeft);
            if (deadline==0) deadlinestr = "Nearest Deadline : No deadline";
            else {
                deadlinestr = String.format("Nearest Deadline : %s",new SimpleDateFormat("dd-MM-yyyy").format(new Date(deadline)));
            }
        }else {
            target = selectedWish.getTarget();
            deadline = selectedWish.getDeadline();
            dayLeft = selectedWish.getDaysLeft();
            title = selectedWish.getTitle();
            targetstr = String.format("Target : %.2f",target);
            dayLeftstr = String.format("%d Days Left",dayLeft);
            if (deadline==0) deadlinestr = "Deadline : No deadline";
            else deadlinestr = String.format("Deadline : %s",new SimpleDateFormat("dd-MM-yyyy").format(new Date(deadline)));
        }
        if (selectedWallet==null){
            for (Wallet w:User.getInstance().getWallets()){
                amount+=w.getBalance();
            }
        }else {
            amount = selectedWallet.getBalance();
        }
        double left = target-amount;
        if (left<0) left = 0;
        wishTitleText.setText(title);
        wishTargetText.setText(targetstr);
        wishDaysLeftText.setText(dayLeftstr);
        wishDeadlineText.setText(deadlinestr);
        wishLeftText.setText(String.format("Amount Left : %.2f",(left)));
        int ratio = 100;
        if (target==0) target = 1;
        else ratio = (int) (amount/target*100);
        wishProgress.setCenterTitle(String.format("%d%%",ratio));
        wishProgress.setProgressValue(ratio);
        if (ratio<=75){
            wishProgress.setBorderColor(Color.parseColor("#00ffbf"));
            wishProgress.setWaveColor(Color.parseColor("#00ffbf"));
        }else if (ratio<=50){
            wishProgress.setWaveColor(Color.parseColor("#bfff00"));
            wishProgress.setBorderColor(Color.parseColor("#bfff00"));
        }else if (ratio<=25){
            wishProgress.setWaveColor(Color.parseColor("#ffbf00"));
            wishProgress.setBorderColor(Color.parseColor("#ffbf00"));
        }else if (ratio<=10){
            wishProgress.setBorderColor(Color.parseColor("#ff4000"));
            wishProgress.setWaveColor(Color.parseColor("#ff4000"));
        }else {
            wishProgress.setBorderColor(Color.parseColor("#00bfff"));
            wishProgress.setWaveColor(Color.parseColor("#00bfff"));
        }
        if (ratio<=50){
            wishProgress.setCenterTitleColor(Color.BLACK);
        }else {
            wishProgress.setCenterTitleColor(Color.WHITE);
        }
    }

    private void initializeButton(){
        addWishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateWishDialog();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    private void showCreateWishDialog(){
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.create_wish_dialog,null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.wish_datepicker);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.wish_hasdeadline_checkbox);
        checkBox.setChecked(true);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) datePicker.setVisibility(View.VISIBLE);
                else datePicker.setVisibility(View.GONE);
            }
        });
        builder.setView(view)
                .setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog dialogBox = (Dialog) dialog;
                        Wish.Builder builder = new Wish.Builder();
                        TextView titleText = (TextView) dialogBox.findViewById(R.id.wish_title_input);
                        String title = titleText.getText().toString();
                        if (title.equals("")) {
                            showMessegeDialog("Please input Wish Title!");
                            return;
                        }
                        builder = builder.setTitle(title);
                        TextView targetText = (TextView) dialogBox.findViewById(R.id.wish_target_input);
                        String targetStr = targetText.getText().toString();
                        if (targetStr.equals("")){
                            showMessegeDialog("Please input Wish Title!");
                            return;
                        }
                        double target = Double.parseDouble(targetStr);
                        builder = builder.setTarget(target);
                        CheckBox hasCheck = (CheckBox) dialogBox.findViewById(R.id.wish_hasdeadline_checkbox);
                        if (hasCheck.isChecked()){
                            DatePicker deadlinePicker = (DatePicker) dialogBox.findViewById(R.id.wish_datepicker);
                            int date = deadlinePicker.getDayOfMonth();
                            int month = deadlinePicker.getMonth();
                            int year = deadlinePicker.getYear();
                            Calendar cal = Calendar.getInstance();
                            cal.set(year,month,date);
                            builder = builder.setDeadline(cal.getTimeInMillis());
                        }
                        User.getInstance().addWish(builder.build());
                        wishAdapter.notifyDataSetChanged();
                        refresh();
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
    private void showMessegeDialog(String messege){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(messege)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
}
