package skesw12.kebthung.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.R;
import skesw12.kebthung.adapters.WalletRecyclerViewAdapter;
import skesw12.kebthung.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends Fragment {

    @BindView(R.id.wallet_list) RecyclerView walletRecyclerView;
    private WalletRecyclerViewAdapter walletAdapter;
    public WalletFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallet, container, false);
        ButterKnife.bind(this,rootView);
        setUpRecyclerView();
        return rootView;
    }

    private void setUpRecyclerView(){
        walletRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        walletRecyclerView.setLayoutManager(layoutManager);
        User u = User.getInstance();
        walletAdapter = new WalletRecyclerViewAdapter(getActivity(),u.getWallets());
        walletRecyclerView.setAdapter(walletAdapter);
    }
}
