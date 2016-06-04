package skesw12.kebthung.models;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YukiReii on 2/6/2559.
 */
public class User implements Serializable{
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

    public void reset(){
        username = null;
        wallets = new ArrayList<Wallet>();
        wishs = new ArrayList<Wish>();
    }

    public void saveFile(Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("usr.kt",Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            Log.e(getClass().getName(), "saveFile: ",e );
        } catch (IOException e) {
            Log.e(getClass().getName(), "saveFile: ",e );
        }
    }
    public void loadFile(Context context){
        try {
            FileInputStream fileInputStream = context.openFileInput("usr.kt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            instance = (User) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            Log.e(getClass().getName(), "loadFile: ",e );
        } catch (StreamCorruptedException e) {
            Log.e(getClass().getName(), "loadFile: ",e );
        } catch (IOException e) {
            Log.e(getClass().getName(), "loadFile: ",e );
        } catch (ClassNotFoundException e) {
            Log.e(getClass().getName(), "loadFile: ",e );
        }
    }
}
