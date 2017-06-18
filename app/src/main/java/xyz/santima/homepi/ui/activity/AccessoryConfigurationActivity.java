package xyz.santima.homepi.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
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
    public static final String TYPE = "type";
    public static final String REF_KEY = "ref";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;

    Service service;
    String key;
    JSONObject config;
    MaterialDialog dialog;
    int type;

    boolean unsaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessory_configuration);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        service = (Service) getIntent().getSerializableExtra(SERVICE_KEY);
        key = getIntent().getStringExtra(REF_KEY);

        if(service != null){
            type = -1;
            setTitle(service.getName() + " - " + service.getRoom());
            dialog = new MaterialDialog.Builder(this)
                    .content(R.string.loading)
                    .progress(true, 0)
                    .show();

            FirebaseDatabase.getInstance().getReference("services/"+service.getKey()+"/config").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dialog.dismiss();
                    try {
                        config = new JSONObject(new Gson().toJson(dataSnapshot.getValue()));
                        System.out.println(config);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    initPreferences(service.getType());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else{
            setTitle("Nuevo");
            config = new JSONObject();
            type = getIntent().getIntExtra(TYPE, 0);
            initPreferences(type);
        }


    }

    private void initPreferences(int type) {

        //Setup fragment preference
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, AccessoryConfigurationFragment.newInstance(type))
                .commit();

        //Setup fab
        if(service != null){
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
        else{
            fab.setVisibility(View.INVISIBLE);
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
            fab.setImageResource(R.drawable.ic_action_bell);
        }
        else{
            fab.setImageResource(R.drawable.ic_action_bell_off);
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

    public void save(){
        if(service != null){
            FirebaseDatabase.getInstance().getReference("services/"+service.getKey()+"/config").setValue(new Gson().fromJson(config.toString(), new TypeToken<HashMap<String, Object>>() {}.getType()));
        }
        else{
            JSONObject base = new JSONObject();
            try {
                base.put("config", config);
                base.put("type", type);
                FirebaseDatabase.getInstance().getReference("services").push().setValue(new Gson().fromJson(base.toString(), new TypeToken<HashMap<String, Object>>() {}.getType()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        unsaved = false;
    }

    public JSONObject getConfig() {
        return config;
    }

    public void setConfig(JSONObject config) {
        unsaved();
        this.config = config;
    }

    public void unsaved() {
        this.unsaved = true;
    }

    public boolean isNew() {
        return type > -1;
    }

    public void delete() {
        System.out.print("borrando...");
        FirebaseDatabase.getInstance().getReference("services/"+service.getKey()).removeValue();
        FirebaseDatabase.getInstance().getReference("service/"+service.getKey()).removeValue();
        this.finish();
    }
}
