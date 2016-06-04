package skesw12.kebthung.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import skesw12.kebthung.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputPasscodeFragment extends Fragment {


    public InputPasscodeFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input_passcode, container, false);
    }

}
