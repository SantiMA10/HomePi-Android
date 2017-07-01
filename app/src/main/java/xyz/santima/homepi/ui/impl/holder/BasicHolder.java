package xyz.santima.homepi.ui.impl.holder;

import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;

import xyz.santima.homepi.model.Accessory;

/**
 * Created by GiantsV3 on 13/06/2017.
 */

public interface BasicHolder {

    /**
     * Metodo para cambiar la fecha en la card del recycleview
     * @param date String
     */
    public void setDate(String date);

    /**
     * Método para cambiar la habitación en la card del recycleview
     * @param place String
     */
    public void setPlace(String place);

    /**
     * Método para obtener el boton principal del card
     * @return Button
     */
    public Button getCardButton();

    /**
     * Metodo para crear la card especifico para cada accesorio
     * @param accessory Accessory
     */
    public void __populate(Accessory accessory);

    /**
     * Metodo para crear la card por defecto
     * @param accessory Accessory
     * @param ref DatabaseReference
     */
    public void populate(Accessory accessory, DatabaseReference ref);

    /**
     * Metodo para mostrar la configuracion
     * @param v View
     */
    public void showConfiguration(View v);

}
