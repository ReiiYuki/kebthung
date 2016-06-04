package skesw12.kebthung.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YukiReii on 2/6/2559.
 */
public class User {
    private List<Wallet> wallets;
    private List<Wish> wishs;
    private String username;
    private static User instance;
    private User(){
        wallets = new ArrayList<Wallet>();
        wishs = new ArrayList<Wish>();
    }
    public static User getInstance(){
        if (instance==null) {
            instance = new User();
        }
        return instance;
    }
    public void addWallet(Wallet wallet){
        wallets.add(wallet);
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public List<Wallet> getTransferList(Wallet wallet){
        List<Wallet> transferList = new ArrayList<Wallet>(wallets);
        transferList.remove(wallet);
        return transferList;
    }

    public void removeWallet(Wallet wallet){
        wallets.remove(wallet);
    }

    public void addWish(Wish wish){
        wishs.add(wish);
    }

    public void removeWish(Wish wish){
        wishs.remove(wish);
    }

    public List<Wish> getWishs() {
        return wishs;
    }
}
