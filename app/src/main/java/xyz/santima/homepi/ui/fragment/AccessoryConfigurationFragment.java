package xyz.santima.homepi.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.view.View;

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
                initGarageConfiguration();
                break;
            case Service.LIGHT:
                setPreferencesFromResource(R.xml.configuration_light, rootKey);
                initLightConfiguration();
                break;
            case Service.SENSOR_HUMIDITY:
                setPreferencesFromResource(R.xml.configuration_sensor, rootKey);
                initHumidityConfiguration();
                break;
            case Service.SENSOR_TEMPERATURE:
                setPreferencesFromResource(R.xml.configuration_sensor, rootKey);
                initTemperatureConfiguration();
                break;
            case Service.THERMOSTAT:
                setPreferencesFromResource(R.xml.configuration_thermostat, rootKey);
                initThermostatConfiguration();
                break;
        }

        initCommonConfiguration();

    }

    private void initThermostatConfiguration() {
    }

    private void initTemperatureConfiguration() {
    }

    private void initHumidityConfiguration() {
    }

    private void initLightConfiguration() {
        initSwitchService();
        PreferenceScreen dialog = (PreferenceScreen) getPreferenceManager().findPreference("status");
        dialog.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.status)
                        .items(new String[]{"Encendida", "Apagada"})
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                try {
                                    config.put("status", dialog.getItems().indexOf(text));
                                    activity.setConfig(config);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                return true;
                            }
                        })
                        .positiveText(R.string.create)
                        .show();

                return false;
            }
        });
    }

    private void initGarageConfiguration() {
        initSwitchService();
        PreferenceScreen dialog = (PreferenceScreen) getPreferenceManager().findPreference("status");
        dialog.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.status)
                        .items(new String[]{"Abierta", "Cerrada"})
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                try {
                                    config.put("status", dialog.getItems().indexOf(text));
                                    activity.setConfig(config);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                return true;
                            }
                        })
                        .positiveText(R.string.create)
                        .show();

                return false;
            }
        });
    }

    private void initSwitchService(){

        PreferenceScreen dialog = (PreferenceScreen) getPreferenceManager().findPreference("actuator");
        dialog.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.status)
                        .items(new String[]{"Rest"})
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                                try {
                                    config.put("actuatorType", dialog.getItems().indexOf(text));
                                    activity.setConfig(config);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                return true;
                            }
                        })
                        .positiveText(R.string.create)
                        .show();

                return false;
            }
        });

        dialog = (PreferenceScreen) getPreferenceManager().findPreference("actuator_config");
        dialog.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                JSONObject actuatorConfig = new JSONObject();
                JSONObject paths = new JSONObject();

                try {
                    actuatorConfig = config.getJSONObject("actuatorConfig");
                    if(actuatorConfig != null){
                        paths = actuatorConfig.optJSONObject("paths");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final JSONObject finalActuatorConfig = actuatorConfig;
                final JSONObject finalPaths = paths;
                new CustomMaterialDialogBuilder(getContext())
                        .addInput(actuatorConfig.optString("url", ""),"URL")
                        .addInput(paths.optString("on", ""),"Path para on")
                        .addInput(paths.optString("off", ""),"Path para off")
                        .addInput(actuatorConfig.optString("blinkTime", "0"),"Tiempo de parpadeo")
                        .inputs(new CustomMaterialDialogBuilder.CustomInputsCallback() {
                            @Override
                            public void onInputs(MaterialDialog dialog, List<CharSequence> inputs, boolean allInputsValidated) {
                                String url = inputs.get(0)+"";
                                String on = inputs.get(1)+"";
                                String off = inputs.get(2)+"";
                                String blink = inputs.get(3)+"";

                                if(url.isEmpty() || on.isEmpty() || off.isEmpty() || blink.isEmpty()){
                                    Snackbar.make(getView(), R.string.not_empty, Snackbar.LENGTH_LONG).show();
                                }
                                else{
                                    try {
                                        finalActuatorConfig.put("url", url);
                                        finalActuatorConfig.put("blinkTime", Integer.parseInt(blink));
                                        finalPaths.put("on", on);
                                        finalPaths.put("off", off);
                                        finalActuatorConfig.put("paths", finalPaths);
                                        config.put("actuatorConfig", finalActuatorConfig);
                                        activity.setConfig(config);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

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
