package skesw12.kebthung.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import skesw12.kebthung.R;
import skesw12.kebthung.fragments.AboutUsFragment;
import skesw12.kebthung.fragments.WishFragment;
import skesw12.kebthung.fragments.ChartFragment;
import skesw12.kebthung.fragments.SettingFragment;
import skesw12.kebthung.fragments.WalletFragment;
import skesw12.kebthung.models.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.nav_view) NavigationView navigationView;
    Fragment walletFragment,wishFragment,chartFragment,settingFragment,aboutusFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
    }

    public void initFragment(){
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
