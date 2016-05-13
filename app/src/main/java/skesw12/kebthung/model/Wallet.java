package skesw12.kebthung.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YukiReii on 13/5/2559.
 */
public class Wallet {
    private double balance;
    private double monthlybalance;
    private List<Note> noteList;
    public Wallet(double b){
        balance = b;
        monthlybalance=b;
        noteList = new ArrayList<Note>();
    }
    public void pay(double amount,String reason){
        noteList.add(new Note(amount,reason));
        balance-=amount;
    }
    public void get(double amount,String reason){
        noteList.add(new Note(amount,reason));
        balance+=amount;
    }
    public void getMonthly(double amount,String reason){
        noteList.add(new Note(amount,reason));
        balance+=amount;
    }
    public List<Note> loadNote(){
        return noteList;
    }

    public double getBalance() {
        return balance;
    }

    public double getMonthlybalance() {
        return monthlybalance;
    }
}
