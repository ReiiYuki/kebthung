package skesw12.kebthung.models;

/**
 * Created by YukiReii on 3/6/2559.
 */
public class TransferMoneyNote extends Note{
    private Wallet des;
    private ReceiveTransferMoneyNote note;
    private class ReceiveTransferMoneyNote extends Note{
        private Wallet des;
        private TransferMoneyNote note;
        public ReceiveTransferMoneyNote(Wallet wallet,Wallet des,TransferMoneyNote note, String purpose, double amount) {
            super(wallet, "Transfer From "+des.getName()+" : "+purpose, amount);
            this.des = des;
            this.note = note;
        }

        @Override
        public void onDelete() {
            wallet.setBalance(wallet.getBalance()-amount);
            des.setBalance(des.getBalance()+amount);
            des.removeNote(note);
            wallet.removeNote(this);
        }
    }

    public TransferMoneyNote(Wallet wallet,Wallet des, String purpose, double amount) {
        super(wallet, "Transfer To "+des.getName()+" : "+purpose, amount);
        this.des = des;
        note = new ReceiveTransferMoneyNote(des,wallet,this,purpose,amount);
        des.addNote(note);
    }

    @Override
    public void onDelete() {
        wallet.setBalance(wallet.getBalance()+amount);
        des.setBalance(des.getBalance()-amount);
        des.removeNote(note);
        wallet.removeNote(this);
    }
}
