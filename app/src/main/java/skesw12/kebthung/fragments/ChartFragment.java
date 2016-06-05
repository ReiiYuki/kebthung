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

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment {

    @BindView(R.id.amount_chart) LineChartView amountChart;
    private final String[] mLabels= {"Jan", "Fev", "Mar", "Apr", "Jun", "May", "Jul", "Aug", "Sep"};
    private final float[][] mValues = {{3.5f, 4.7f, 4.3f, 8f, 6.5f, 9.9f, 7f, 8.3f, 7.0f},
            {4.5f, 2.5f, 2.5f, 9f, 4.5f, 9.5f, 5f, 8.3f, 1.8f}};

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
        LineSet dataset = new LineSet(mLabels, mValues[0]);
        dataset.setColor(Color.WHITE)
                .setFill(getResources().getColor(android.R.color.holo_orange_light))
                .setDotsColor(Color.WHITE)
                .setThickness(4)
                .setDashed(new float[]{10f,10f})
                .beginAt(0);
        amountChart.addData(dataset);
        amountChart.setLabelsColor(Color.WHITE);
        amountChart.show();
    }

}
