package skesw12.kebthung.fragments;


import android.animation.PropertyValuesHolder;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;
import com.db.chart.view.Tooltip;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.BounceEase;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment {

    @BindView(R.id.amount_chart) LineChartView amountChart;
    private final String[] mLabels= {"","Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",""};
    private final float[]mValues = {0,100, 200, 300, 80, 50, 900, 300,0,};
    private Tooltip tooltip;

    public ChartFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  rootview  = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this,rootview);
        initChart();
        return rootview;
    }

    private void initChart(){
        tooltip = new Tooltip(getActivity(), R.layout.tool_tip_chart, R.id.value);
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
        LineSet dataset = new LineSet(mLabels, mValues);
        dataset.setColor(Color.WHITE)
                .setFill(getResources().getColor(android.R.color.holo_orange_light))
                .setDotsColor(Color.WHITE)
                .setThickness(4)
                .setDashed(new float[]{10f,10f})
                .beginAt(1)
                .endAt(8);
        amountChart.addData(dataset);
        amountChart.setStep(150);
        amountChart.setFontSize(40);
        amountChart.setLabelsColor(Color.WHITE);

        Runnable chartAction = new Runnable() {
            @Override
            public void run() {
                tooltip.prepare(amountChart.getEntriesArea(0).get(3), mValues[0]);
                amountChart.showTooltip(tooltip, true);
            }
        };
        Animation anim = new Animation()
                .setEasing(new BounceEase())
                .setEndAction(chartAction);
        amountChart.show(anim);
    }

}
