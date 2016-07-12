package skesw12.kebthung.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.R;
import skesw12.kebthung.fragments.AboutUsFragment;
import skesw12.kebthung.fragments.WhatsNewFragment;
import skesw12.kebthung.fragments.WishFragment;
import skesw12.kebthung.fragments.ChartFragment;
import skesw12.kebthung.fragments.SettingFragment;
import skesw12.kebthung.fragments.WalletFragment;
import skesw12.kebthung.models.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.nav_view) NavigationView navigationView;
    Fragment walletFragment,wishFragment,chartFragment,settingFragment,aboutusFragment,announcementFragment;
    private boolean isPause;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getClass().getName(), "onCreate: ");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        User.getInstance().loadFile(this);
        intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        setUpFragment();
        initFragment();
        setTitle();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setUpFragment(){
        walletFragment = new WalletFragment();
        wishFragment = new WishFragment();
        chartFragment = new ChartFragment();
        settingFragment = new SettingFragment();
        aboutusFragment = new AboutUsFragment();
        announcementFragment = new WhatsNewFragment();
    }

    public void initFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.place_fragement,walletFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Log.d(getClass().getName(), "onBackPressed: ");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        User.getInstance().saveFile(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(getClass().getName(), "onPause: ");
        User.getInstance().saveFile(this);
        System.exit(0);
//        isPause=true;
//        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(getClass().getName(), "onResume: ");
//        if (isPause){
//            startActivity(intent);
//        }
    }

    @Override
    protected void onStop() {
        super.onPause();
        User.getInstance().saveFile(this);
        isPause=true;
        Log.d(getClass().getName(), "onStop: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(getClass().getName(), "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getName(), "onDestroy: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(getClass().getName(), "onStart: ");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        User.getInstance().saveFile(this);
        int id = item.getItemId();
        if (id == R.id.nav_wallet) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.place_fragement,walletFragment);
            transaction.commit();
        } else if (id == R.id.nav_wish) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.place_fragement,wishFragment);
            transaction.commit();
        } else if (id == R.id.nav_chart) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.place_fragement,chartFragment);
            transaction.commit();
        } else if (id == R.id.nav_settings) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.place_fragement,settingFragment);
            transaction.commit();
        } else if (id == R.id.nav_about_us) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.place_fragement,aboutusFragment);
            transaction.commit();
        } else if (id== R.id.nav_annoucement){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.place_fragement,announcementFragment);
            transaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setTitle(){
        TextView titleText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView);
        titleText.setText(User.getInstance().getUsername());
    }

}
