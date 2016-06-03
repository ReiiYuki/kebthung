package skesw12.kebthung.models;

/**
 * Created by YukiReii on 2/6/2559.
 */
public class Wallet {
    private String name;
    private double maxBalance;
    private double balance;
    public Wallet(String name,double maxBalance){
        this.name = name;
        this.maxBalance = maxBalance;
        this.balance = maxBalance;
    }
    public Wallet(double maxBalance){
        this.name = "Wallet";
        this.maxBalance = maxBalance;
        this.balance = maxBalance;
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

    public void getMoney(double amount){
        balance+=amount;
        if (balance>maxBalance) maxBalance=balance;
    }

    public boolean payMoney(double amount){
        if (amount>balance) return false;
        balance-=amount;
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

}
