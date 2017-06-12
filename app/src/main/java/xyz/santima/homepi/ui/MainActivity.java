package xyz.santima.homepi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.santima.homepi.R;
import xyz.santima.homepi.model.Service;
import xyz.santima.homepi.ui.adapter.ServiceHolder;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.home_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initMenu();
        initRecycleView();
    }

    private void initRecycleView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/service");

        recyclerView.setAdapter(new FirebaseRecyclerAdapter<Service, ServiceHolder>(Service.class, R.layout.sensor_card, ServiceHolder.class, ref) {
            @Override
            protected void populateViewHolder(ServiceHolder viewHolder, final Service model, final int position) {

                switch (model.getType()){
                    case "garage":
                        String status = "";
                        String target = "";
                        boolean enabled = true;
                        switch (model.getStatus()){
                            case "0":
                                status = "Abierta";
                                target = "Cerrar";
                                enabled = true;
                                break;
                            case "1":
                                status = "Cerrada";
                                target = "Abrir";
                                enabled = true;
                                break;
                            case "2":
                                status = "Abriendo";
                                target = status;
                                enabled = false;
                                break;
                            case "3":
                                status = "Cerrando";
                                target = status;
                                enabled = false;
                                break;
                        }
                        viewHolder.setStatus(status);
                        viewHolder.getCardButton().setText(target);
                        viewHolder.getCardButton().setEnabled(enabled);
                        setupCard(viewHolder, model, this.getRef(position));
                        break;
                    case "temperature":
                        viewHolder.setStatus(model.getStatus() + "º");
                        setupCard(viewHolder, model, this.getRef(position));
                        break;
                    default:
                        viewHolder.setStatus(model.getStatus() + "%");
                        setupCard(viewHolder, model, this.getRef(position));
                        break;

                }

            }

        });
    }

    private void setupCard(ServiceHolder viewHolder, final Service model, final DatabaseReference ref) {
        viewHolder.setDate(model.getFormatDate());
        viewHolder.setPlace(model.getPlace());

        viewHolder.getCardButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setUser("home-pi-android");
                model.setWorking(true);

                ref.setValue(model);
            }
        });
    }

    private void initMenu() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
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

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
