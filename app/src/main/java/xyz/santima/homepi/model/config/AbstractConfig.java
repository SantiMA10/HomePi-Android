package xyz.santima.homepi.model.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import xyz.santima.homepi.model.config.Config;

/**
 * Created by GiantsV3 on 17/06/2017.
 */

public abstract class AbstractConfig implements Config {

    public static final int MODE_ARRAY = 0;
    public static final int MODE_OBJECT = 1;

    protected String name;
    protected int type;

    public AbstractConfig(String name, int type){
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

        if(mode == MODE_ARRAY){
            JSONArray array = object.getJSONArray(param);
            array.put(prepareJSON(inputs));
            object.put(param, array);
        }
        else if(mode == MODE_OBJECT){
            object.put(param, prepareJSON(inputs));
        }

        return object;

    }
}
