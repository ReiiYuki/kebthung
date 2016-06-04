package skesw12.kebthung.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.R;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.pin1) ImageView pin1;
    @BindView(R.id.pin2) ImageView pin2;
    @BindView(R.id.pin3) ImageView pin3;
    @BindView(R.id.pin4) ImageView pin4;
    private ImageView[] imageViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initPinCodeCircle();
    }
    private void initPinCodeCircle(){
        imageViews = new ImageView[]{pin1, pin2, pin3, pin4};
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(16,16,16,16);
//        for (ImageView m : imageViews){
//            m.setLayoutParams(lp);
//        }
    }
}
