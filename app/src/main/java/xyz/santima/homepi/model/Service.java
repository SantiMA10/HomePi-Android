package xyz.santima.homepi.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class Service implements Serializable {

    public static final int GARAGE = 0;
    public static final int SENSOR_TEMPERATURE = 1;
    public static final int SENSOR_HUMIDITY = 2;
    public static final int LIGHT = 3;
    public static final int THERMOSTAT = 4;

    private String date;
    private String status;
    private int type;
    private String room;
    private String user;
    private boolean working;

    private Map<String, Object> config;

    public Service() {
        config = new HashMap<>();
    }

    public Service(String date, String status, int type, String user, boolean working, String room) {
        this.date = date;
        this.status = status;
        this.type = type;
        this.user = user;
        this.working = working;
        this.room = room;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {

        this.date = date;

    }

    public String getFormatDate(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return dt1.format(sdf.parse(date));
        } catch (ParseException e){
            return "Error";
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status.toString();
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public Map<String, Object> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }
}
