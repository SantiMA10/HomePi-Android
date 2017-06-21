package xyz.santima.homepi.business.factory;

import java.util.ArrayList;
import java.util.List;

import xyz.santima.homepi.model.config.AccessoryConfiguration;
import xyz.santima.homepi.model.config.impl.RestSensorAccessoryConfiguration;
import xyz.santima.homepi.model.config.impl.RestSwitchAccessoryConfiguration;

/**
 * Created by GiantsV3 on 17/06/2017.
 */

public class ConfigFactory {

    private static final AccessoryConfiguration[] SENSOR___ACCESSORY_CONFIGURATIONs = { new RestSensorAccessoryConfiguration() };
    private static final AccessoryConfiguration[] ACTUATOR___ACCESSORY_CONFIGURATIONs = { new RestSwitchAccessoryConfiguration() };

    public static AccessoryConfiguration[] getActuatorConfigs(){
        return ACTUATOR___ACCESSORY_CONFIGURATIONs;
    }

    public static AccessoryConfiguration[] getSensorConfigs(){
        return SENSOR___ACCESSORY_CONFIGURATIONs;
    }

    public static CharSequence[] getConfigsNames(AccessoryConfiguration[] accessoryConfigurations){
        List<String> names = new ArrayList<>();
        for (AccessoryConfiguration accessoryConfiguration : accessoryConfigurations) {
            names.add(accessoryConfiguration.getName());
        }

        return names.toArray(new CharSequence[names.size()]);
    }

}
