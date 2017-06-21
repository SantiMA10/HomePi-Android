package xyz.santima.homepi.ui.impl.holder;

import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;

import xyz.santima.homepi.model.Accessory;

/**
 * Created by GiantsV3 on 13/06/2017.
 */

public interface BasicHolder {

    public void setDate(String date);
    public void setPlace(String place);
    public Button getCardButton();
    public void __populate(Accessory accessory);
    public void populate(Accessory accessory, DatabaseReference ref);
    public void showConfiguration(View v);

}
