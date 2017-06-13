package xyz.santima.homepi.ui.impl.holder;

import android.widget.Button;

import com.google.firebase.database.DatabaseReference;

import xyz.santima.homepi.model.Service;

/**
 * Created by GiantsV3 on 13/06/2017.
 */

public interface BasicHolder {

    public void setDate(String date);
    public void setPlace(String place);
    public Button getCardButton();
    public void __populate(Service service);
    public void populate(Service service, DatabaseReference ref);

}
