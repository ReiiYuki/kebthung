package skesw12.kebthung.models;


import java.util.Date;

/**
 * Created by YukiReii on 4/6/2559.
 */
public class Wish {
    private String title;
    private String purpose;
    private double target;
    private Date deadline;
    public static class Builder{
        private String title;
        private String purpose;
        private double target;
        private Date deadline;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setPurpose(String purpose) {
            this.purpose = purpose;
            return this;
        }

        public Builder setTarget(double target) {
            this.target = target;
            return this;
        }

        public Builder setDeadline(Date deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder(){
            title = "";
            purpose = "";
            target = 0;
            deadline = null;
        }
        public Wish build(){
            return new Wish(this);
        }
    }
    private Wish(Builder builder){
        title = builder.title;
        purpose = builder.purpose;
        target = builder.target;
        deadline = builder.deadline;
    }

    public String getPurpose() {
        return purpose;
    }

    public Date getDeadline() {
        return deadline;
    }

    public double getTarget() {
        return target;
    }

    public String getTitle() {
        return title;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDaysLeft(){
        int daysLeft = (int) (deadline.getTime()-System.currentTimeMillis() / (1000*60*60*24));
        if (daysLeft<0) return 0;
        return daysLeft;
    }
}
