package xyz.santima.homepi.model;

import android.util.Log;

import java.util.Date;
import java.util.Objects;

/**
 * Created by GiantsV3 on 12/06/2017.
 */

public class Service {

    private String date;
    private String status;
    private String type;
    private String place;
    private String user;
    private boolean working;

    public Service() {}

    public Service(String date, String status, String type, String user, boolean working, String place) {
        this.date = date;
        this.status = status;
        this.type = type;
        this.user = user;
        this.working = working;
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        Log.d("setDate", date);
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status.toString();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
}
