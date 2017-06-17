package xyz.santima.homepi.business.factory;

import java.util.ArrayList;
import java.util.List;

import xyz.santima.homepi.model.config.Config;
import xyz.santima.homepi.model.config.impl.RestSensorConfig;
import xyz.santima.homepi.model.config.impl.RestSwitchConfig;

/**
 * Created by GiantsV3 on 17/06/2017.
 */

public class ConfigFactory {

    private static final Config[] sensor_configs = { new RestSensorConfig() };
    private static final Config[] actuator_configs = { new RestSwitchConfig() };

    public static Config[] getActuatorConfigs(){
        return actuator_configs;
    }

    public static Config[] getSensorConfigs(){
        return sensor_configs;
    }

    public static CharSequence[] getConfigsNames(Config[] configs){
        List<String> names = new ArrayList<>();
        for (Config config : configs) {
            names.add(config.getName());
        }

        return names.toArray(new CharSequence[names.size()]);
    }

}
