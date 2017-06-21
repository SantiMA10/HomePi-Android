package xyz.santima.homepi.model.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by GiantsV3 on 17/06/2017.
 */

public interface AccessoryConfiguration {

    public String getName();
    public int getType();
    public Map<String, String> getInputs(JSONObject object);
    public JSONObject addConfig(int mode, List<CharSequence> inputs, JSONObject object, String param) throws JSONException;

}
