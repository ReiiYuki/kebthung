package skesw12.kebthung.models;


import android.util.Log;

import java.util.Date;

/**
 * Created by YukiReii on 4/6/2559.
 */
public class Wish {
    private String title;
    private double target;
    private long deadline;
    public static class Builder{
        private String title;
        private double target;
        private long deadline;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTarget(double target) {
            this.target = target;
            return this;
        }

        public Builder setDeadline(long deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder(){
            title = "";
            target = 0;
            deadline = 0;
        }
        public Wish build(){
            return new Wish(this);
        }
    }
    private Wish(Builder builder){
        title = builder.title;
        target = builder.target;
        deadline = builder.deadline;
    }

    public long getDeadline() {
        return deadline;
    }

    public double getTarget() {
        return target;
    }

    public String getTitle() {
        return title;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDaysLeft(){
        int daysLeft = Math.round((deadline-System.currentTimeMillis()) / (1000f*60f*60f*24f));
        if (daysLeft<0) return 0;
        return daysLeft;
    }
}
