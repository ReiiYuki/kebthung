package skesw12.kebthung.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YukiReii on 2/6/2559.
 */
public class User {
    private List<Wallet> wallets;
    private String username;
    private static User instance;
    private User(){
        wallets = new ArrayList<Wallet>();
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
}
