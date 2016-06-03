package skesw12.kebthung.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YukiReii on 2/6/2559.
 */
public class User {
    private List<Wallet> wallets;
    private String username;
    private User instance;
    private User(){
        wallets = new ArrayList<Wallet>();
    }
    public User getInstance(){
        if (instance==null) {
            instance = new User();
        }
        return instance;
    }

}
