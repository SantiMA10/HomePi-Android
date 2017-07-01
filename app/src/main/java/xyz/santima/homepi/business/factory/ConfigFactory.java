package xyz.santima.homepi.business.factory;

import java.util.ArrayList;
import java.util.List;

import xyz.santima.homepi.model.config.AccessoryConfiguration;
import xyz.santima.homepi.model.config.impl.RestSensorAccessoryConfiguration;
import xyz.santima.homepi.model.config.impl.RestSwitchAccessoryConfiguration;

public class ConfigFactory {

    private static final AccessoryConfiguration[] SENSOR___ACCESSORY_CONFIGURATIONs = { new RestSensorAccessoryConfiguration() };
    private static final AccessoryConfiguration[] ACTUATOR___ACCESSORY_CONFIGURATIONs = { new RestSwitchAccessoryConfiguration() };

    /**
     * Método para obtener la lista de actuadores disponibles
     * @return AccessoryConfiguration[]
     */
    public static AccessoryConfiguration[] getActuatorConfigs(){
        return ACTUATOR___ACCESSORY_CONFIGURATIONs;
    }

    /**
     * Método para obtener la lista de sensores disponibles
     * @return AccessoryConfiguration[]
     */
    public static AccessoryConfiguration[] getSensorConfigs(){
        return SENSOR___ACCESSORY_CONFIGURATIONs;
    }

    /**
     * Método para obtener una lista con los nombres de la lista de configuraciones pasada como parametro
     * @param accessoryConfigurations
     * @return
     */
    public static CharSequence[] getConfigsNames(AccessoryConfiguration[] accessoryConfigurations){
        List<String> names = new ArrayList<>();
        for (AccessoryConfiguration accessoryConfiguration : accessoryConfigurations) {
            names.add(accessoryConfiguration.getName());
        }

        return names.toArray(new CharSequence[names.size()]);
    }

}
