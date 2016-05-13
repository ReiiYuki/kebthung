package skesw12.kebthung.model;

/**
 * Created by YukiReii on 13/5/2559.
 */
public class Note {
    private String reason;
    private double balance;
    public Note(double balance,String reason){
        this.reason=reason;
        this.balance=balance;
    }

    public double getBalance() {
        return balance;
    }

    public String getReason() {
        return reason;
    }
}
