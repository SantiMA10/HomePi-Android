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
import java.util.Map;
import java.util.Set;

import xyz.santima.homepi.R;
import xyz.santima.homepi.business.factory.ConfigFactory;
import xyz.santima.homepi.model.Service;
import xyz.santima.homepi.model.config.AbstractConfig;
import xyz.santima.homepi.model.config.Config;
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
                try {
                    config.put("sensorServiceType", accessoryType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                initHumidityConfiguration();
                break;
            case Service.SENSOR_TEMPERATURE:
                setPreferencesFromResource(R.xml.configuration_sensor, rootKey);
                initTemperatureConfiguration();
                try {
                    config.put("sensorServiceType", accessoryType);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Service.THERMOSTAT:
                setPreferencesFromResource(R.xml.configuration_thermostat, rootKey);
                initThermostatConfiguration();
                break;
        }

        initCommonConfiguration();

    }

    private void initThermostatConfiguration() {
        initConfigSelectorDialogs("actuator", ConfigFactory.getActuatorConfigs(), AbstractConfig.MODE_OBJECT, "actuatorConfig", "actuatorType");
    }

    private void initTemperatureConfiguration() {
        initConfigSelectorDialogs("sensor", ConfigFactory.getSensorConfigs(), AbstractConfig.MODE_OBJECT, "sensorConfig", "sensorType");
    }

    private void initHumidityConfiguration() {
        initConfigSelectorDialogs("sensor", ConfigFactory.getSensorConfigs(), AbstractConfig.MODE_OBJECT, "sensorConfig", "sensorType");
    }

    private void initLightConfiguration() {
        initConfigSelectorDialogs("actuator", ConfigFactory.getActuatorConfigs(), AbstractConfig.MODE_OBJECT, "actuatorConfig", "actuatorType");
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
        initConfigSelectorDialogs("actuator", ConfigFactory.getActuatorConfigs(), AbstractConfig.MODE_OBJECT, "actuatorConfig", "actuatorType");
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

    private void initConfigSelectorDialogs(String preference, final Config[] configs, final int mode, final String paramConfig, final String paramType){

        PreferenceScreen dialog = (PreferenceScreen) getPreferenceManager().findPreference(preference);
        dialog.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.status)
                        .items(ConfigFactory.getConfigsNames(configs))
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                initConfigSelectorDialogs(configs[dialog.getItems().indexOf(text)], mode, paramConfig, paramType);

                                return true;
                            }
                        })
                        .positiveText(R.string.choose)
                        .show();

                return false;
            }
        });

    }

    private void initConfigSelectorDialogs(final Config config, final int mode, final String paramConfig, final String paramType){

        CustomMaterialDialogBuilder builder =  new CustomMaterialDialogBuilder(getContext());
        Map<String, String> inputs = config.getInputs(this.config.optJSONObject(paramConfig));
        Set<String> keys = inputs.keySet();

        final JSONObject object = this.config.optInt(paramType, -1) == config.getType() ? this.config : new JSONObject();

        for(String key : keys){
            builder.addInput(inputs.get(key), key);
        }

        builder.inputs(new CustomMaterialDialogBuilder.CustomInputsCallback() {
            @Override
            public void onInputs(MaterialDialog dialog, List<CharSequence> inputs, boolean allInputsValidated) {
                try {
                    JSONObject config_object = config.addConfig(mode, inputs, object, paramConfig);
                    config_object.put(paramType, config.getType());
                    if(config_object == null){
                        Snackbar.make(getView(), R.string.not_empty, Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        System.out.println(config_object);
                        activity.setConfig(config_object);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        builder.positiveText(R.string.save).title(getString(R.string.configure) + config.getName()).build().show();

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

        dialog = (PreferenceScreen) getPreferenceManager().findPreference("delete");
        if(activity.isNew()){
            dialog.setTitle("Salir");
            dialog.setSummary("Salir y no crear este accesorio");
            dialog.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    activity.finish();

                    return false;
                }
            });
        }
        else{
            dialog.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    activity.delete();

                    return false;
                }
            });
        }



    }

}
