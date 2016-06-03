package skesw12.kebthung.models;

/**
 * Created by YukiReii on 3/6/2559.
 */
public class TransferMoneyNote extends Note{
    private Wallet des;
    public TransferMoneyNote(Wallet wallet,Wallet des, String purpose, double amount) {
        super(wallet, purpose, amount);
    }

    public String getDesName() {
        return des.getName();
    }

    @Override
    public void onDelete() {
        wallet.setBalance(wallet.getBalance()+amount);
        des.setBalance(des.getBalance()-amount);
    }
}
