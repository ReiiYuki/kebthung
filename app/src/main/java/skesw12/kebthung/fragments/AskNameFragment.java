package skesw12.kebthung.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.R;
import skesw12.kebthung.models.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskNameFragment extends Fragment {

    @BindView(R.id.name_text) EditText usernameText;
    @BindView(R.id.empty_name_text) TextView emptyNameText;
    public AskNameFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_ask_name, container, false);
        ButterKnife.bind(this,rootview);
        initListener();
        return rootview;
    }
    private void initListener(){
        usernameText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String username = usernameText.getText().toString();
                    if (username.equals("")) {
                        emptyNameText.setVisibility(View.VISIBLE);
                        return true;
                    }
                    User.getInstance().setUsername(username);
                    emptyNameText.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }
}
