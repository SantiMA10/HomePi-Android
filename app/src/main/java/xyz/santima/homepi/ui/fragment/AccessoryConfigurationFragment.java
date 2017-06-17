package xyz.santima.homepi.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;

import com.afollestad.materialdialogs.MaterialDialog;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import xyz.santima.homepi.R;
import xyz.santima.homepi.model.Service;
import xyz.santima.homepi.ui.activity.AccessoryConfigurationActivity;
import xyz.santima.homepi.ui.impl.component.CustomMaterialDialogBuilder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccessoryConfigurationFragment extends PreferenceFragmentCompat {

    public static final String ACCESSORY_TYPE_KEY = "0";

    private int accessoryType;
    JSONObject config;
    private AccessoryConfigurationActivity activity;

    public static AccessoryConfigurationFragment newInstance(int accessoryType) {
        AccessoryConfigurationFragment fragment = new AccessoryConfigurationFragment();
        Bundle args = new Bundle();
        args.putInt(ACCESSORY_TYPE_KEY, accessoryType);
        fragment.setArguments(args);
        return fragment;
    }


    public AccessoryConfigurationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {

        if (getArguments() != null) {
            accessoryType = getArguments().getInt(ACCESSORY_TYPE_KEY);
        }
        activity = ((AccessoryConfigurationActivity)getActivity());
        config = activity.getConfig();

        switch (accessoryType){
            case Service.GARAGE:
                setPreferencesFromResource(R.xml.configuration_garage, rootKey);
                break;
            case Service.LIGHT:
                setPreferencesFromResource(R.xml.configuration_light, rootKey);
                break;
            case Service.SENSOR_HUMIDITY:
                setPreferencesFromResource(R.xml.configuration_sensor, rootKey);
                break;
            case Service.SENSOR_TEMPERATURE:
                setPreferencesFromResource(R.xml.configuration_sensor, rootKey);
                break;
            case Service.THERMOSTAT:
                setPreferencesFromResource(R.xml.configuration_thermostat, rootKey);
                break;
        }

        initCommonConfiguration();

    }

    private void initCommonConfiguration(){

        PreferenceScreen dialog = (PreferenceScreen) getPreferenceManager().findPreference("name");
        dialog.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                new CustomMaterialDialogBuilder(getContext())
                        .addInput(config.optString("name", ""),"Nombre")
                        .inputs(new CustomMaterialDialogBuilder.CustomInputsCallback() {
                            @Override
                            public void onInputs(MaterialDialog dialog, List<CharSequence> inputs, boolean allInputsValidated) {
                                if(inputs.size() == 1){
                                    try {
                                        config.put("name", inputs.get(0));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                activity.setConfig(config);
                            }
                        })
                        .title("Nombre")
                        .positiveText(R.string.save)
                        .build().show();

                return false;
            }
        });

        dialog = (PreferenceScreen) getPreferenceManager().findPreference("room");
        dialog.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                new CustomMaterialDialogBuilder(getContext())
                        .addInput(config.optString("room", ""),"Habitación")
                        .inputs(new CustomMaterialDialogBuilder.CustomInputsCallback() {
                            @Override
                            public void onInputs(MaterialDialog dialog, List<CharSequence> inputs, boolean allInputsValidated) {
                                if(inputs.size() == 1){
                                    try {
                                        config.put("room", inputs.get(0));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                activity.setConfig(config);
                            }
                        })
                        .title("Habitación")
                        .positiveText(R.string.save)
                        .build().show();

                return false;
            }
        });

    }

}
