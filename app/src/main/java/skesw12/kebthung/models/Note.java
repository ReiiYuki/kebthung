package skesw12.kebthung.models;

/**
 * Created by YukiReii on 3/6/2559.
 */
public abstract class Note {
    protected Wallet wallet;
    protected String purpose;
    protected double amount;
    public Note(Wallet wallet,String purpose,double amount){
        this.wallet = wallet;
        this.purpose = purpose;
        this.amount = amount;
    }
    public abstract void onDelete();
}
