package xyz.santima.homepi.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.santima.homepi.R;
import xyz.santima.homepi.model.Service;
import xyz.santima.homepi.ui.fragment.AccessoryConfigurationFragment;

public class AccessoryConfigurationActivity extends AppCompatActivity {

    public static final String SERVICE_KEY = "service";
    public static final String REF_KEY = "ref";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;

    Service service;
    String key;

    boolean unsaved = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessory_configuration);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        service = (Service) getIntent().getSerializableExtra(SERVICE_KEY);
        key = getIntent().getStringExtra(REF_KEY);

        setTitle(key + " - " + service.getRoom());

        initPreferences();
    }

    private void initPreferences() {

        //Setup fragment preference
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, AccessoryConfigurationFragment.newInstance(service.getType()))
                .commit();

        //Setup fab
        if(service.getType() != Service.GARAGE && service.getType() != Service.LIGHT
                && service.getType() != Service.THERMOSTAT){
            fab.setVisibility(View.INVISIBLE);
        }
        else{
            boolean contains = false;
            Map<String, Object> config = service.getConfig();
            if(config.containsKey("notification")){
                contains = ((List<String>)config.get("notification"))
                        .contains(FirebaseInstanceId.getInstance().getToken());
            }
            changeFABIcon(contains);

        }
    }

    @OnClick(R.id.fab)
    public void onClickFab(View v){
        Map<String, Object> config = service.getConfig();
        if(config.containsKey("notification")){
            List<String> notification = ((List<String>)config.get("notification"));
            if(!notification.contains(FirebaseInstanceId.getInstance().getToken())){
                notification.add(FirebaseInstanceId.getInstance().getToken());
                changeFABIcon(true);
            }
            else {
                changeFABIcon(false);
                ((List<String>)config.get("notification"))
                        .remove(FirebaseInstanceId.getInstance().getToken());
            }

        }
        else{
            changeFABIcon(true);
            List<String> notification = new ArrayList<>();
            notification.add(FirebaseInstanceId.getInstance().getToken());
            config.put("notification", notification);
        }

        service.setConfig(config);
        FirebaseDatabase.getInstance().getReference("service/" + key).setValue(service);
    }

    private void changeFABIcon(boolean notification){
        if(notification){
            fab.setImageResource(R.drawable.ic_action_bell_off);
        }
        else{
            fab.setImageResource(R.drawable.ic_action_bell);
        }
    }

    @Override
    public void onBackPressed() {
        if (unsaved) {
            new MaterialDialog.Builder(this)
                    .title(R.string.changes_not_saved)
                    .positiveText(R.string.save)
                    .negativeText(R.string.dismiss)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            save();
                            AccessoryConfigurationActivity.super.onBackPressed();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            AccessoryConfigurationActivity.super.onBackPressed();
                        }
                    })
                    .show();
        } else {
            super.onBackPressed();
        }
    }

    protected void save(){
        unsaved = false;
    }

}
