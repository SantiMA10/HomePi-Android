package xyz.santima.homepi.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.santima.homepi.R;
import xyz.santima.homepi.ui.fragment.ConfigurationFragment;
import xyz.santima.homepi.ui.fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initMenu();
    }

    private void initMenu() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, MainFragment.newInstance())
                .commit();
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

        android.support.v4.app.FragmentTransaction transaction = null;

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            AuthUI.getInstance()
                    .signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                    // user is now signed out
                    startActivity(new Intent(MainActivity.this, SplashActivity.class));
                    finish();
                }
            });
        }
        else if(id == R.id.nav_main){
            transaction = this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor, MainFragment.newInstance());
        }
        else if(id == R.id.nav_configuration){
            transaction = this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor, ConfigurationFragment.newInstance());
        }

        if(transaction != null){
            transaction.addToBackStack(null).commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
