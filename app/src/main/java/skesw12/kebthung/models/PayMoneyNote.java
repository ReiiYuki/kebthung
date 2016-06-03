package skesw12.kebthung.models;

/**
 * Created by YukiReii on 3/6/2559.
 */
public class PayMoneyNote extends Note {
    public PayMoneyNote(Wallet wallet, String purpose, double amount) {
        super(wallet, purpose, amount);
    }

    @Override
    public void onDelete() {
        wallet.setBalance(wallet.getBalance()+amount);
    }
}
