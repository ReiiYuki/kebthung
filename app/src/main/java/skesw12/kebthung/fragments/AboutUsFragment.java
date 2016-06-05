package skesw12.kebthung.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {

    @BindView(R.id.github_me) TextView me;
    @BindView(R.id.github_wave) TextView wave;
    @BindView(R.id.github_circle) TextView circle;
    @BindView(R.id.github_williamchart) TextView chart;
    public AboutUsFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this,view);
        bindlink();
        return view;
    }
    private void bindlink(){
        me.setText( Html.fromHtml("<a href=\"https://github.com/ReiiYuki/kebthung\">Github</a>"));
        wave.setText(Html.fromHtml("<a href=\"https://github.com/tangqi92/WaveLoadingView\">WaveLoadingView</a>"));
        circle.setText(Html.fromHtml("<a href=\"https://github.com/markushi/android-circlebutton\">CircleButton</a>"));
        chart.setText(Html.fromHtml("<a href=\"https://github.com/diogobernardino/WilliamChart\">WilliamChart</a>"));
        me.setMovementMethod(LinkMovementMethod.getInstance());
        wave.setMovementMethod(LinkMovementMethod.getInstance());
        circle.setMovementMethod(LinkMovementMethod.getInstance());
        chart.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
