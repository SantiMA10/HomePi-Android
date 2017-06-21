package xyz.santima.homepi.model.config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by GiantsV3 on 17/06/2017.
 */

public abstract class AbstractAccessoryConfiguration implements AccessoryConfiguration {

    public static final int MODE_ARRAY = 0;
    public static final int MODE_OBJECT = 1;

    protected String name;
    protected int type;

    public AbstractAccessoryConfiguration(String name, int type){
        this.name = name;
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getType() {
        return type;
    }

    public abstract JSONObject prepareJSON(List<CharSequence> inputs) throws JSONException;

    @Override
    public JSONObject addConfig(int mode, List<CharSequence> inputs, JSONObject object, String param) throws JSONException {

        object.put(param, prepareJSON(inputs));
        return object;

    }
}
