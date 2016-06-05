package skesw12.kebthung.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.Helper.HelperListener;
import skesw12.kebthung.R;
import skesw12.kebthung.activities.LoginActivity;
import skesw12.kebthung.activities.MainActivity;
import skesw12.kebthung.models.User;
import skesw12.kebthung.views.PasscodeView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    @BindView(R.id.reset_button) Button resetButton;
    @BindView(R.id.edit_username_button) Button usernameButton;
    @BindView(R.id.edit_password_button) Button passwordButton;
    @BindView(R.id.img) ImageView img;
    private String newpass;
    public SettingFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  rootview  = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this,rootview);
        changeColor();
        initButton();
        return rootview;
    }
    private void initButton(){
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputPassDialog(new HelperListener() {
                    @Override
                    public void onAction() {
                        showResetDialog();
                    }
                },false);
            }
        });
        usernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputPassDialog(new HelperListener() {
                    @Override
                    public void onAction() {
                        showUsernameEditDialog();
                        ((MainActivity)getActivity()).setTitle();
                        User.getInstance().saveFile(getActivity());
                    }
                },false);
            }
        });
        passwordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputPassDialog(new HelperListener() {
                    @Override
                    public void onAction() {
                       showInputPassDialog(new HelperListener() {
                           @Override
                           public void onAction() {
                               User.getInstance().setPasscode(newpass);
                               User.getInstance().saveFile(getActivity());
                           }
                       },true);
                    }
                },false);
            }
        });
        newpass = "";
    }
    private void changeColor(){
        img.setColorFilter(R.color.colorPrimary, PorterDuff.Mode.MULTIPLY);
    }
    private void showInputPassDialog(final HelperListener helper,boolean isCheck){
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.passcode_dialog,null);
        final PasscodeView passcodeView = (PasscodeView) view.findViewById(R.id.passcode);
        passcodeView.setCheckMatch(isCheck);
        builder.setView(view);
        final Dialog dialog = builder.create();
        passcodeView.setHelperListener(new HelperListener() {
            @Override
            public void onAction() {
                if (passcodeView.isMatch()) {
                    newpass = passcodeView.getPasscode();
                    helper.onAction();
                    dialog.cancel();
                }
            }
        });
        dialog.show();
    }
    private void showResetDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure to reset ?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User.getInstance().reset();
                        User.getInstance().saveFile(getActivity());
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
    private void showUsernameEditDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_wallet_dialog,null);
        TextView nameText = (TextView) view.findViewById(R.id.wallet_name_input);
        nameText.setHint("Input new username");
        builder.setView(view)
                .setPositiveButton("COMFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView nameText = (TextView) ((Dialog)dialog).findViewById(R.id.wallet_name_input);
                        if (nameText.getText().toString().equals("")){
                            showMessegeDialog("Please input username!");
                            return;
                        }
                        User.getInstance().setUsername(nameText.getText().toString());
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
    private void showMessegeDialog(String messege){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(messege)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
}
