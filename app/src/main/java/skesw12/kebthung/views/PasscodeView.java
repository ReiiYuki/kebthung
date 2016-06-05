package skesw12.kebthung.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.helperinterfaces.HelperListener;
import skesw12.kebthung.R;
import skesw12.kebthung.models.User;

/**
 * Created by YukiReii on 6/5/2016.
 */
public class PasscodeView extends LinearLayout{
    @BindView(R.id.pin1)
    ImageView pin1;
    @BindView(R.id.pin2) ImageView pin2;
    @BindView(R.id.pin3) ImageView pin3;
    @BindView(R.id.pin4) ImageView pin4;
    private ImageView[] pins;
    @BindView(R.id.num_0)
    Button num0;
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
    private View rootview;
    private Button[] nums;
    private int count;
    private String passcode;
    private String oldpasscode;
    private boolean second;
    private boolean checkMatch;
    private boolean isFinish;
    private boolean isMatch;
    private HelperListener helperListener;
    public PasscodeView(Context context, AttributeSet attr){
        super(context,attr);
        initView(context);
    }
    public PasscodeView(Context context) {
        super(context);
        initView(context);
    }
    public void initView(Context context){
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        View rootView = inflate(context,R.layout.passcode_view,this);
        ButterKnife.bind(this,rootView);
        setDuplicateParentStateEnabled(true);
        initCounter();
        initArray();
        addListener();
    }
    public boolean isMatch(){
        return isMatch;
    }
    public void setCheckMatch(boolean checkMatch){
        this.checkMatch =checkMatch;
    }
    public String getPasscode(){
        return oldpasscode;
    }
    public boolean isFinish(){ return  isFinish;}
    private void addListener(){
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
                        if (checkMatch){
                            if(!second){
                                oldpasscode=passcode;
                                second=true;
                                setNormal();
                                setSecond();
                            }else {
                                if (passcode.equals(oldpasscode)){
                                    setNormal();
                                    second = false;
                                    isMatch = true;
                                    isFinish = true;
                                    onFinish();
                                }else {
                                    setWrong();
                                    setNotMatch();
                                }
                            }
                        }else {
                            if (User.getInstance().authorize(passcode)){
                                isFinish = true;
                                isMatch = true;
                                onFinish();
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
    public void setHelperListener(HelperListener helperListener){
        this.helperListener = helperListener;
    }
    public void onFinish(){
        helperListener.onAction();
    }
}
