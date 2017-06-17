package xyz.santima.homepi.model.config.impl;

import android.support.design.widget.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.santima.homepi.R;
import xyz.santima.homepi.model.config.AbstractConfig;

/**
 * Created by GiantsV3 on 17/06/2017.
 */

public class RestSwitchConfig extends AbstractConfig {

    public RestSwitchConfig() {
        super("Interruptor Rest", 0);
    }

    @Override
    public Map<String, String> getInputs(JSONObject object) {

        JSONObject paths;
        if(object != null){
            paths = object.optJSONObject("paths");
        }
        else{
            object = new JSONObject();
            paths = new JSONObject();
        }

        Map<String, String> inputs = new HashMap<>();
        inputs.put("URL", object.optString("url", ""));
        inputs.put("Path para activar el interruptor", paths.optString("on", ""));
        inputs.put("Path para desactivar el interruptor", paths.optString("off", ""));
        inputs.put("Tiempo para el parpadeo del interruptor", object.optString("blinkTime", "0"));

        return inputs;
    }

    @Override
    public JSONObject prepareJSON(List<CharSequence> inputs) throws JSONException {

        String url = inputs.get(2)+"";
        String on = inputs.get(1)+"";
        String off = inputs.get(3)+"";
        String blink = inputs.get(0)+"";

        if(url.isEmpty() || on.isEmpty() || off.isEmpty() || blink.isEmpty()){
            return null;
        }

        JSONObject object = new JSONObject();
        object.put("url", url);
        object.put("blinkTime", Integer.parseInt(blink));
        JSONObject paths = new JSONObject();
        paths.put("on", on);
        paths.put("off", off);
        object.put("paths", paths);

        return object;

    }
}
