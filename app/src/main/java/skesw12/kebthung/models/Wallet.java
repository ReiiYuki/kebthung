package skesw12.kebthung.models;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YukiReii on 2/6/2559.
 */
public class Wallet implements Serializable{
    private String name;
    private double maxBalance;
    private double balance;
    private List<Note> notes;
    public Wallet(String name,double maxBalance){
        this.name = name;
        this.maxBalance = maxBalance;
        this.balance = maxBalance;
        notes = new ArrayList<Note>();
    }

    public double getBalance() {
        return balance;
    }

    public double getMaxBalance() {
        return maxBalance;
    }

    public String getName() {
        return name;
    }

    public void getMoney(String purpose,double amount){
        balance+=amount;
        if (balance>maxBalance) maxBalance=balance;
        notes.add(new GetMoneyNote(this,purpose,amount));
    }

    public boolean payMoney(String purpose,double amount){
        if (balance-amount<0) return false;
        balance-=amount;
        notes.add(new PayMoneyNote(this,purpose,amount));
        return true;
    }

    public boolean transfer(String purpose,Wallet des,double amount){
        if (balance-amount<0) return false;
        balance-=amount;
        des.getMoney(purpose,amount);
        notes.add(new TransferMoneyNote(this,des,purpose,amount));
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setBalance(double balance) {
        if (balance>maxBalance) maxBalance = balance;
        this.balance = balance;
    }

    void addNote(Note note){
        notes.add(note);
    }

    void removeNote(Note note){
        notes.remove(note);
    }

    public List<Note> getNotes() {
        return notes;
    }
}
