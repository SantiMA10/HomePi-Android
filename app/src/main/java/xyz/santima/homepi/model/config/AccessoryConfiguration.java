package xyz.santima.homepi.model.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by GiantsV3 on 17/06/2017.
 */

public interface AccessoryConfiguration {

    /**
     * Metodo para obtener el nombre del sensor o actuador a configurar
     * @return
     */
    public String getName();

    /**
     * Método para obtener el tipo del sensor o actuador a configurar
     * @return
     */
    public int getType();

    /**
     * Metodo para obtener una lista de los campos necesarios para configurar el sensor o actuador
     * @param object
     * @return
     */
    public Map<String, String> getInputs(JSONObject object);

    /**
     * Metodo para guardar la configuración del sensor o actuador
     * @param mode
     * @param inputs
     * @param object
     * @param param
     * @return
     * @throws JSONException
     */
    public JSONObject addConfig(int mode, List<CharSequence> inputs, JSONObject object, String param) throws JSONException;

}
