package skesw12.kebthung.models;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by YukiReii on 3/6/2559.
 */
public class PayMoneyNote extends Note{
    public PayMoneyNote(Wallet wallet, String purpose, double amount) {
        super(wallet, purpose, amount);
    }

    @Override
    public int getColor() {
        return android.R.color.holo_red_light;
    }

    @Override
    public String getType() {
        return "Pay";
    }

    @Override
    public String getDesName() {
        return null;
    }

    @Override
    public void onDelete() {
        wallet.setBalance(wallet.getBalance()+amount);
        wallet.removeNote(this);
    }
}
