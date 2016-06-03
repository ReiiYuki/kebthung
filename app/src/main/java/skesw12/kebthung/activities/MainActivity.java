package skesw12.kebthung.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.R;
import skesw12.kebthung.fragments.BankAccountFragment;
import skesw12.kebthung.fragments.ChartFragment;
import skesw12.kebthung.fragments.SettingFragment;
import skesw12.kebthung.fragments.WalletFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.nav_view) NavigationView navigationView;
    Fragment walletFragment,bankAccountFragment,chartFragment,settingFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpFragment();
        initFragment();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setUpFragment(){
        walletFragment = new WalletFragment();
        bankAccountFragment = new BankAccountFragment();
        chartFragment = new ChartFragment();
        settingFragment = new SettingFragment();
    }

    private void initFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.place_fragement,walletFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_wallet) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.place_fragement,walletFragment);
            transaction.commit();
        } else if (id == R.id.nav_bank_account) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.place_fragement,bankAccountFragment);
            transaction.commit();
        } else if (id == R.id.nav_chart) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.place_fragement,chartFragment);
            transaction.commit();
        } else if (id == R.id.nav_settings) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.place_fragement,settingFragment);
            transaction.commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
