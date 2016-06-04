package skesw12.kebthung.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.R;
import skesw12.kebthung.activities.MainActivity;
import skesw12.kebthung.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    @BindView(R.id.reset_button) Button resetButton;
    public SettingFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  rootview  = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this,rootview);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.getInstance().reset();
                User.getInstance().saveFile(getActivity());
                ((MainActivity)getActivity()).initFragment();
            }
        });
        return rootview;
    }

}
