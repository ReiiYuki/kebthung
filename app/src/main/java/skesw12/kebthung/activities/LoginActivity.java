package skesw12.kebthung.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.R;
import skesw12.kebthung.fragments.AskNameFragment;
import skesw12.kebthung.fragments.FirstWalletFragment;
import skesw12.kebthung.fragments.InputPasscodeFragment;
import skesw12.kebthung.models.User;

public class LoginActivity extends AppCompatActivity {

    private Fragment askNameFragment,firstWalletFragment,inputPasscodeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initFragment();
        decideFragment();
    }

    private void initFragment(){
        askNameFragment = new AskNameFragment();
        firstWalletFragment = new FirstWalletFragment();
        inputPasscodeFragment = new InputPasscodeFragment();
    }

    private void decideFragment(){
        if (User.getInstance().getUsername()==null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.place_fragement,askNameFragment);
            transaction.commit();
        }else{
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.place_fragement,inputPasscodeFragment);
            transaction.commit();
        }
    }
}
