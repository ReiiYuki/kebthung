package skesw12.kebthung.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.itangqi.waveloadingview.WaveLoadingView;
import skesw12.kebthung.R;
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
    @BindView(R.id.wish_amount) TextView wishAmountText;
    @BindView(R.id.wish_purpose) TextView wishPurposeText;
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
        return rootView;
    }

    private void initializeSpinner(){
        walletAdapter = new ArrayAdapter<Wallet>(getActivity(),R.layout.transfer_spinner_layout, User.getInstance().getWallets());
        walletSpinner.setAdapter(walletAdapter);
        wishAdapter = new WishSpinnerAdapter(getActivity(),R.layout.transfer_spinner_layout,User.getInstance().getWishs());
        wishSpinner.setAdapter(wishAdapter);
    }
    private void refresh(){
        if (selectedWallet==null){

        }
        if (selectedWish==null){

        }
    }
}
