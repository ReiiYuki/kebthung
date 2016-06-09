package skesw12.kebthung.fragments;


import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;
import com.db.chart.view.Tooltip;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.BounceEase;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.Helper.ChartDetailHelper;
import skesw12.kebthung.R;
import skesw12.kebthung.adapters.WalletSpinnerAdapter;
import skesw12.kebthung.models.GetMoneyNote;
import skesw12.kebthung.models.Note;
import skesw12.kebthung.models.PayMoneyNote;
import skesw12.kebthung.models.TransferMoneyNote;
import skesw12.kebthung.models.User;
import skesw12.kebthung.models.Wallet;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment {

    @BindView(R.id.amount_chart) ChartView amountChart;
    @BindView(R.id.wallet_spinner) Spinner walletSpinner;
    private float[] values;
    private float max;
    private String[] label;
    public ChartFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  rootview  = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this,rootview);
        initSpinner();
        calculateWeek(null);
        initChart();
        return rootview;
    }
    private void initSpinner(){
        final WalletSpinnerAdapter adapter = new WalletSpinnerAdapter(getActivity(),R.layout.wallet_spinner,User.getInstance().getWallets());
        walletSpinner.setAdapter(adapter);
        walletSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Wallet selectWallet = adapter.getItem(position);
                Log.d("Select", "onNothingSelected: "+selectWallet);
                calculateWeek(selectWallet);
                amountChart.dismissAllTooltips();
                amountChart.updateValues(0,values);
                amountChart.setStep(ChartDetailHelper.getInstance().getMaxRatio());
                amountChart.notifyDataUpdate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void initChart(){
        final Tooltip tooltip = new Tooltip(getActivity(), R.layout.tool_tip_chart, R.id.value);
        tooltip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        tooltip.setDimensions((int) Tools.fromDpToPx(65), (int) Tools.fromDpToPx(25));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {

            tooltip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

            tooltip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                    PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                    PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

            tooltip.setPivotX(Tools.fromDpToPx(65) / 2);
            tooltip .setPivotY(Tools.fromDpToPx(25));
        }
        amountChart.setTooltips(tooltip);
        LineSet dataset = new LineSet(ChartDetailHelper.getInstance().getLabels(), ChartDetailHelper.getInstance().getValues());
        dataset.setColor(Color.WHITE)
                .setFill(getResources().getColor(android.R.color.holo_orange_light))
                .setDotsColor(Color.WHITE)
                .setThickness(4)
                .setDashed(new float[]{10f,10f})
                .beginAt(1)
                .endAt(8);
        amountChart.addData(dataset);
        Log.d(getClass().getName(), "initChart: "+Math.round(max/10));
        amountChart.setStep(ChartDetailHelper.getInstance().getMaxRatio());
        amountChart.setFontSize((int) (getResources().getDimension(R.dimen.dp_30)/getResources().getDisplayMetrics().density));
        amountChart.setLabelsColor(Color.WHITE);
        Runnable chartAction = new Runnable() {
            @Override
            public void run() {
                tooltip.prepare(amountChart.getEntriesArea(0).get(3), values[0]);
                amountChart.showTooltip(tooltip, true);
            }
        };
        Animation anim = new Animation()
                .setEasing(new BounceEase())
                .setEndAction(chartAction);
        amountChart.show(anim);
    }
    private void calculateWeek(Wallet selectWallet){
        values = new float[9];
        label = new String[9];
        for (int i = 0;i<9;i++){
            values[i] = 0;
            label[i] = "";
        }
        this.max = 10;
        if (selectWallet==null) {
            List<Wallet> wallets = User.getInstance().getWallets();
            for (Wallet w : wallets) {
                float[] values = new float[9];
                List<Note> notes = w.getNotes();
                Calendar tempCal = Calendar.getInstance();
                tempCal.set(Calendar.HOUR_OF_DAY, 0);
                tempCal.set(Calendar.MINUTE, 0);
                tempCal.set(Calendar.SECOND, 0);
                long now = tempCal.getTimeInMillis();
                int count = 6;
                double amount = w.getBalance();
                values[7] = (float) amount;
                label[7] = tempCal.get(Calendar.DATE) + "/" + (tempCal.get(Calendar.MONTH) + 1);
                Log.d(getClass().getName(), "calculateWeek: " + new SimpleDateFormat("yy-mm-DD HH:mm:ss").format(tempCal.getTime()));
                long create = w.getCreateDate().getTimeInMillis();
                now -= 86400;
                for (int i = notes.size() - 1; i >= 0 || count > 0; i--) {
                    if (i < 0) {
                        tempCal.setTimeInMillis(now);
                        label[count] = tempCal.get(Calendar.DATE) + "/" + (tempCal.get(Calendar.MONTH) + 1);
                        if (now>=create) values[count]=(float) amount;
                        count--;
                        if (values[count+1]>max) max = values[count+1];
                        now -= 86400000;
                    } else {
                        if (notes.get(i).getTimestamp() < now) {
                            tempCal.setTimeInMillis(now);
                            label[count] = tempCal.get(Calendar.DATE) + "/" + (tempCal.get(Calendar.MONTH) + 1);
                            values[count--] = (float) amount;
                            now -= 86400000;
                        }
                        if (notes.get(i) instanceof GetMoneyNote || notes.get(i) instanceof TransferMoneyNote.ReceiveTransferMoneyNote) {
                            amount -= notes.get(i).getAmount();
                        } else if (notes.get(i) instanceof PayMoneyNote || notes.get(i) instanceof TransferMoneyNote) {
                            amount += notes.get(i).getAmount();
                        }
                    }
                }
                for (int i = 0; i < 9; i++) {
                    this.values[i] += values[i];
                    Log.d(getClass().getName(), "calculateWeek: index " + i + " Data " + values[i]);
                    if (this.values[i] > max) max = this.values[i];
                }
            }
        }else {
            Log.d(getClass().getName(), "calculateWeek: Yes");
            List<Note>notes = selectWallet.getNotes();
            Calendar tempCal = Calendar.getInstance();
            tempCal.set(Calendar.HOUR_OF_DAY,0);
            tempCal.set(Calendar.MINUTE,0);
            tempCal.set(Calendar.SECOND,0);
            long now = tempCal.getTimeInMillis();
            int count = 6;
            double amount = selectWallet.getBalance();
            values[7] = (float) amount;
            max = (float) amount;
            label[7] = tempCal.get(Calendar.DATE)+"/"+(tempCal.get(Calendar.MONTH)+1);
            Log.d(getClass().getName(), "calculateWeek: "+new SimpleDateFormat("yy-mm-DD HH:mm:ss").format(tempCal.getTime()));
            now -=86400;
            long create =  selectWallet.getCreateDate().getTimeInMillis();
            for (int i = notes.size()-1;i>=0||count>0;i--){
                if (i<0) {
                    tempCal.setTimeInMillis(now);
                    label[count--] = tempCal.get(Calendar.DATE)+"/"+(tempCal.get(Calendar.MONTH)+1);
                    if (now>=create) values[count--]=(float) amount;
                    if (values[count+1]>max) max = values[count+1];
                    now -= 86400000;
                }else {
                    if (notes.get(i).getTimestamp()<now){
                        tempCal.setTimeInMillis(now);
                        label[count] = tempCal.get(Calendar.DATE)+"/"+(tempCal.get(Calendar.MONTH)+1);
                        values[count--] = (float) amount;
                        if (values[count+1]>max) max = values[count+1];
                        now -= 86400000;
                    }
                    if (notes.get(i)instanceof GetMoneyNote || notes.get(i) instanceof TransferMoneyNote.ReceiveTransferMoneyNote){
                        amount-=notes.get(i).getAmount();
                    }else if (notes.get(i) instanceof PayMoneyNote || notes.get(i) instanceof TransferMoneyNote){
                        amount+=notes.get(i).getAmount();
                    }
                }
            }
        }
        ChartDetailHelper.getInstance().setLabels(label);
        ChartDetailHelper.getInstance().setValues(values);
        ChartDetailHelper.getInstance().setMax(max);
//        Log.d("MAx", "calculateWeek: "+max);
    }

    @Override
    public void onDestroyView() {
        amountChart.dismissAllTooltips();
        amountChart.dismiss();
        super.onDestroyView();
    }
}
