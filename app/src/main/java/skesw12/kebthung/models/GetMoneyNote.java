package skesw12.kebthung.models;

import android.graphics.Color;

/**
 * Created by YukiReii on 3/6/2559.
 */
public class GetMoneyNote extends Note{

    public GetMoneyNote(Wallet wallet, String purpose, double amount) {
        super(wallet, purpose, amount);
    }

    @Override
    public int getColor() {
        return Color.parseColor("#66ff66");
    }

    @Override
    public String getType() {
        return "Get";
    }

    @Override
    public void onDelete() {
        wallet.setBalance(wallet.getBalance()-amount);
        wallet.removeNote(this);
    }
}
