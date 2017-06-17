package xyz.santima.homepi.model.config.impl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.santima.homepi.model.config.AbstractConfig;

/**
 * Created by GiantsV3 on 17/06/2017.
 */

public class RestSensorConfig extends AbstractConfig {

    public RestSensorConfig() {
        super("Sensor Rest", 0);
    }

    @Override
    public Map<String, String> getInputs(JSONObject object) {

        JSONObject param;
        if(object != null){
            param = object.optJSONObject("param");
        }
        else{
            object = new JSONObject();
            param = new JSONObject();
        }

        Map<String, String> inputs = new HashMap<>();
        inputs.put("URL", object.optString("url", ""));
        inputs.put("Parametro para obtener el valor del sensor", param.optString("ok", ""));
        inputs.put("Parametro para obtener el error del sensor", param.optString("error", ""));

        return inputs;
    }

    @Override
    public JSONObject prepareJSON(List<CharSequence> inputs) throws JSONException {

        String url = inputs.get(2)+"";
        String ok = inputs.get(0)+"";
        String error = inputs.get(1)+"";

        if(url.isEmpty() || ok.isEmpty() || error.isEmpty()){
            return null;
        }

        JSONObject object = new JSONObject();
        object.put("url", url);
        JSONObject param = new JSONObject();
        param.put("ok", ok);
        param.put("error", error);
        object.put("param", param);

        return object;

    }
}
