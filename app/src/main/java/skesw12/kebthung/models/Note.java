package skesw12.kebthung.models;

import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by YukiReii on 3/6/2559.
 */
public abstract class Note {
    private long timestamp;
    protected Wallet wallet;
    protected String purpose;
    protected double amount;
    public Note(Wallet wallet,String purpose,double amount){
        this.wallet = wallet;
        this.purpose = purpose;
        this.amount = amount;
        this.timestamp = System.currentTimeMillis();
    }

    public double getAmount() {
        return amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getFormattedTime(){
        return new SimpleDateFormat("HH:mm:ss \ndd-MM-yyyy").format(new Date(timestamp));
    }

    public abstract void onDelete();
}
