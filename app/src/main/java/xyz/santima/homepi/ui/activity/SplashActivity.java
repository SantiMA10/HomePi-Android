package xyz.santima.homepi.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import xyz.santima.homepi.R;
import xyz.santima.homepi.model.FirebaseConfiguration;
import xyz.santima.homepi.ui.impl.component.CustomMaterialDialogBuilder;

public class SplashActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private static boolean firebase = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Realm.init(this);
        final Realm realm = Realm.getDefaultInstance();
        FirebaseConfiguration configuration = realm.where(FirebaseConfiguration.class).findFirst();

        if(configuration == null){

            new CustomMaterialDialogBuilder(this)
                    .addInput("","API Key")
                    .addInput("","Application Id")
                    .addInput("","Database URL")
                    .addInput("","GCM Sender Id")
                    .addInput("","Storage Bucket")
                    .inputs(new CustomMaterialDialogBuilder.CustomInputsCallback() {
                        @Override
                        public void onInputs(MaterialDialog dialog, List<CharSequence> inputs, boolean allInputsValidated) {
                            FirebaseConfiguration configuration = new FirebaseConfiguration();
                            configuration.setApiKey(inputs.get(0)+"");
                            configuration.setApplicationId(inputs.get(1)+"");
                            configuration.setDatabaseUrl(inputs.get(2)+"");
                            configuration.setGcmSenderId(inputs.get(3)+"");
                            configuration.setStorageBucket(inputs.get(4)+"");

                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(configuration);
                            realm.commitTransaction();
                            configurateFirebase(configuration, getApplicationContext());
                            realm.close();
                        }
                    })
                    .title(R.string.firebase_configuration)
                    .positiveText(R.string.save)
                    .build().show();

        }
        else{
            configurateFirebase(configuration, getApplicationContext());
            realm.close();
        }


    }

    private void configurateFirebase(FirebaseConfiguration configuration, Context context){
        if(!firebase){
            FirebaseApp.initializeApp(context, new FirebaseOptions.Builder()
                    .setApiKey(configuration.getApiKey())
                    .setApplicationId(configuration.getApplicationId())
                    .setDatabaseUrl(configuration.getDatabaseUrl())
                    .setGcmSenderId(configuration.getGcmSenderId())
                    .setStorageBucket(configuration.getStorageBucket())
                    .build());
            firebase = true;
        }

        auth();
    }

    private void auth(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            startActivityForResult(
                AuthUI.getInstance()
                      .createSignInIntentBuilder()
                      .setLogo(R.mipmap.ic_launcher)
                      .setTheme(R.style.AuthThme)
                      .setAllowNewEmailAccounts(false)
                      .build(),
                      RC_SIGN_IN);
        }
        else{
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCodes.OK) {
            IdpResponse idpResponse = IdpResponse.fromResultIntent(data);
            startActivity(new Intent(this, MainActivity.class)
                    .putExtra("email", idpResponse.getEmail()));
        }
    }

    @OnClick(R.id.iniciar_sesion)
    public void login(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.mipmap.ic_launcher)
                        .setTheme(R.style.AuthThme)
                        .setAllowNewEmailAccounts(false)
                        .build(),
                RC_SIGN_IN);
    }
}
