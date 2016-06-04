package skesw12.kebthung.fragments;


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
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
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
        double target = 0;
        Date deadline = null;
        int dayLeft = 0;
        double amount = 0;
        String title = "Total";
        String targetstr = "";
        String deadlinestr = "";
        String dayLeftstr = "";
        if (selectedWish==null){
            for (Wish w:User.getInstance().getWishs()){
                if (deadline==null) deadline=w.getDeadline();
                if (deadline.compareTo(w.getDeadline())==1) {
                    deadline = w.getDeadline();
                    dayLeft = w.getDaysLeft();
                }
                target+=w.getTarget();
            }
            targetstr = String.format("Total target : %.2f",target);
            dayLeftstr = String.format("%d Nearest Day Left",dayLeft);
            if (deadline==null) deadlinestr = "Nearest Deadline : No deadline";
            else deadlinestr = String.format("Nearest Deadline : %s",new SimpleDateFormat("dd-MM-yyyy").format(deadline));
        }else {
            target = selectedWish.getTarget();
            deadline = selectedWish.getDeadline();
            dayLeft = selectedWish.getDaysLeft();
            title = selectedWish.getTitle();
            targetstr = String.format("Target : %.2f",target);
            dayLeftstr = String.format("%d Days Left",dayLeft);
            if (deadline==null) deadlinestr = "Deadline : No deadline";
            else deadlinestr = String.format("Deadline : %s",new SimpleDateFormat("dd-MM-yyyy").format(deadline));
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
            wishProgress.setCenterTitleColor(Color.BLACK);
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
    }
}
