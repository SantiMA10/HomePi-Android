package xyz.santima.homepi.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;

import com.afollestad.materialdialogs.MaterialDialog;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import io.realm.Realm;
import xyz.santima.homepi.R;
import xyz.santima.homepi.model.FirebaseConfiguration;
import xyz.santima.homepi.ui.impl.component.CustomMaterialDialogBuilder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationFragment extends PreferenceFragmentCompat {

    public static ConfigurationFragment newInstance() {
        ConfigurationFragment fragment = new ConfigurationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public ConfigurationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.configuration, rootKey);

        try {
            setupLegal();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setupFirebase();

    }

    private void setupLegal() throws IOException {

        PreferenceScreen dialog = (PreferenceScreen) getPreferenceManager().findPreference("legal");
        String licencias = "";

        InputStream input = getResources().openRawResource(R.raw.licencias_app);
        BufferedReader bf = new BufferedReader(new InputStreamReader(input));

        while(bf.ready()){
            licencias += bf.readLine();
        }

        bf.close();
        input.close();

        final String finalLicencias = licencias;
        dialog.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                new MaterialDialog.Builder(getContext())
                        .title("Legal")
                        .content(finalLicencias)
                        .positiveText("Cerrar")
                        .show();

                return false;
            }
        });

    }

    private void setupFirebase(){

        PreferenceScreen dialog = (PreferenceScreen) getPreferenceManager().findPreference("firebase");
        dialog.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                FirebaseConfiguration initialConfiguration = Realm.getDefaultInstance()
                        .where(FirebaseConfiguration.class).findFirst();

                new CustomMaterialDialogBuilder(getContext())
                        .addInput(initialConfiguration.getApiKey(),"API Key")
                        .addInput(initialConfiguration.getApplicationId(),"Application Id")
                        .addInput(initialConfiguration.getDatabaseUrl(),"Database URL")
                        .addInput(initialConfiguration.getGcmSenderId(),"GCM Sender Id")
                        .addInput(initialConfiguration.getStorageBucket(),"Storage Bucket")
                        .inputs(new CustomMaterialDialogBuilder.CustomInputsCallback() {
                            @Override
                            public void onInputs(MaterialDialog dialog, List<CharSequence> inputs, boolean allInputsValidated) {
                                FirebaseConfiguration configuration = new FirebaseConfiguration();
                                configuration.setApiKey(inputs.get(0)+"");
                                configuration.setApplicationId(inputs.get(1)+"");
                                configuration.setDatabaseUrl(inputs.get(2)+"");
                                configuration.setGcmSenderId(inputs.get(3)+"");
                                configuration.setStorageBucket(inputs.get(4)+"");

                                if(!configuration.isCorrect()){
                                    Snackbar.make(getView(), R.string.all_fields_message, Snackbar.LENGTH_LONG).show();
                                }
                                else if(configuration.isCorrect()){
                                    Realm realm = Realm.getDefaultInstance();
                                    realm.beginTransaction();
                                    realm.deleteAll();
                                    realm.copyToRealmOrUpdate(configuration);
                                    realm.commitTransaction();
                                    realm.close();

                                    Snackbar.make(getView(), R.string.restart_message, Snackbar.LENGTH_LONG).show();
                                }

                            }
                        })
                        .title(R.string.firebase_configuration)
                        .positiveText(R.string.save)
                        .build().show();

                return false;
            }
        });


    }

}
