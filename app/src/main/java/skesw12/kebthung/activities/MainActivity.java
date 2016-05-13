package skesw12.kebthung.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.itangqi.waveloadingview.WaveLoadingView;
import skesw12.kebthung.R;
import skesw12.kebthung.model.Wallet;

public class MainActivity extends AppCompatActivity {
    Wallet w;
    @BindView(R.id.waveBalance) WaveLoadingView wb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initComponent();
    }
    public void initComponent(){
        w = new Wallet(3000);
        updateWaveProgress();
    }
    @OnClick(R.id.pay_butt)
    public void pay(){
        w.pay(500,"");
        updateWaveProgress();
    }
    @OnClick(R.id.get_butt)
    public void get(){
        w.get(500,"");
        updateWaveProgress();
        Log.e("DDD", "get: EEEE");
    }
    public void updateWaveProgress(){
        wb.setCenterTitle(String.valueOf(w.getBalance()));
        int p=(int) (w.getBalance()/w.getMonthlybalance()*100);
        Log.i("Month: ",w.getMonthlybalance()+"");
        wb.setProgressValue((int) (w.getBalance()/w.getMonthlybalance()*100));
        Log.e( "updateWaveProgress: ", p +"");
        wb.setAmplitudeRatio(70);
    }

}
