package skesw12.kebthung.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;
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
    private final float[]mValues = {0,100, 200, 300, 80, 50, 900, 300,0};

    public ChartFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  rootview  = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this,rootview);
        editChart();
        return rootview;
    }

    private void editChart(){
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
        amountChart.show();
    }

}
