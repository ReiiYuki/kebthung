package skesw12.kebthung.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.R;
import skesw12.kebthung.fragments.FirstWalletFragment;
import skesw12.kebthung.models.User;

public class LoginActivity extends AppCompatActivity {

    private Fragment askNameFragment,firstWalletFragment,inputPasscodeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initFileSetting();
        initFragment();
        decideFragment();
    }

    private void initFileSetting(){
        User.getInstance().loadFile(this);
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

    private void addFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.place_fragement,inputPasscodeFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    class AskNameFragment extends Fragment {

        @BindView(R.id.name_text)
        EditText usernameText;
        @BindView(R.id.empty_name_text)
        TextView emptyNameText;
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
                        addFragment(inputPasscodeFragment);
                    }
                    return false;
                }
            });
        }
    }
    class InputPasscodeFragment extends Fragment {
        @BindView(R.id.pin1) ImageView pin1;
        @BindView(R.id.pin2) ImageView pin2;
        @BindView(R.id.pin3) ImageView pin3;
        @BindView(R.id.pin4) ImageView pin4;
        private ImageView[] pins;
        @BindView(R.id.num_0) Button num0;
        @BindView(R.id.num_1) Button num1;
        @BindView(R.id.num_2) Button num2;
        @BindView(R.id.num_3) Button num3;
        @BindView(R.id.num_4) Button num4;
        @BindView(R.id.num_5) Button num5;
        @BindView(R.id.num_6) Button num6;
        @BindView(R.id.num_7) Button num7;
        @BindView(R.id.num_8) Button num8;
        @BindView(R.id.num_9) Button num9;
        @BindView(R.id.num_x) Button numx;
        @BindView(R.id.title) TextView title;
        private Button[] nums;
        private int count;
        private String passcode;
        private String oldpasscode;
        private boolean second;
        public InputPasscodeFragment() {}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootview = inflater.inflate(R.layout.fragment_input_passcode, container, false);
            ButterKnife.bind(this,rootview);
            initArray();
            initCounter();
            initListener();
            return rootview;
        }

        private void initArray(){
            pins = new ImageView[]{pin1,pin2,pin3,pin4};
            nums = new Button[]{num0,num1,num2,num3,num4,num5,num6,num7,num8,num9};
        }
        private void initCounter(){
            count=0;
            passcode="";
            oldpasscode = "";
            second = false;
        }
        private void initListener(){
            for (int i = 0;i<10;i++){
                final int finalI = i;
                nums[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (count==0){
                            setNormal();
                        }
                        if (count<4){
                            passcode+=Integer.toString(finalI);
                            pins[count].setBackground(getResources().getDrawable(R.drawable.circle_circle));
                            count++;
                        }
                        if (count==4){
                            if (!User.getInstance().isActive()){
                                if(!second){
                                    oldpasscode=passcode;
                                    second=true;
                                    setNormal();
                                    setSecond();
                                }else {
                                    if (passcode.equals(oldpasscode)){
                                        setNormal();
                                        second = false;
                                        addFragment(firstWalletFragment);
                                    }else {
                                        setWrong();
                                        setNotMatch();
                                    }
                                }
                            }else {
                                if (User.getInstance().authorize(passcode)){
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }else {
                                    setWrong();
                                }
                            }
                        }
                    }
                });
            }
            numx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (count>0){
                        pins[count-1].setBackground(getResources().getDrawable(R.drawable.circle));
                        count-=1;
                        passcode = passcode.substring(0,count);
                    }
                }
            });
        }
        private void setNormal(){
            count = 0;
            passcode="";
            title.setText("Input your passcode");
            title.setTextColor(Color.BLACK);
            for (ImageView m : pins) {
                m.setBackground(getResources().getDrawable(R.drawable.circle));
            }
        }
        private void setSecond(){
            title.setText("Input your passcode again");
        }
        private void setNotMatch(){
            title.setText("Passcode doesn't match!");
        }
        private void setWrong(){
            count=0;
            passcode="";
            second = false;
            title.setText("Invalid Passcode!");
            title.setTextColor(Color.parseColor("#FF0000"));
            for (ImageView m : pins) {
                m.setBackground(getResources().getDrawable(R.drawable.red_circle));
            }
        }
    }
}
