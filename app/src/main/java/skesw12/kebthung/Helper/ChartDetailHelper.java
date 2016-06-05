package skesw12.kebthung.Helper;

/**
 * Created by YukiReii on 6/5/2016.
 */
public class ChartDetailHelper {
    private float[] values;
    private String[] labels;
    private float max;
    private static  ChartDetailHelper instance;
    private ChartDetailHelper(){}
    public static ChartDetailHelper getInstance(){
        if (instance==null) instance = new ChartDetailHelper();
        return instance;
    }

    public int getMaxRatio() {
        return Math.round(max/10);
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float[] getValues() {
        return values;
    }

    public void setValues(float[] values) {
        this.values = values;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }
}
