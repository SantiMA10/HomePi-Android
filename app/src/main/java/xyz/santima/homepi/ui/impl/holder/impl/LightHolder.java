package xyz.santima.homepi.ui.impl.holder.impl;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.santima.homepi.R;
import xyz.santima.homepi.model.Accessory;
import xyz.santima.homepi.ui.impl.holder.AbstractBasicHolder;

public class LightHolder extends AbstractBasicHolder {

    @BindView(R.id.garage_card_img) ImageView card_status;
    @BindView(R.id.card_update_date) TextView card_date;
    @BindView(R.id.card_place) TextView card_place;
    @BindView(R.id.card_update_button) Button card_button;

    public LightHolder(LayoutInflater inflater, ViewGroup parent){
        super(inflater.inflate(R.layout.img_status_card, parent, false));
        context = parent.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void setDate(String date) {
        card_date.setText(date);
    }
    public void setPlace(String place) {
        card_place.setText(place);
    }

    public Button getCardButton(){
        return card_button;
    }

    @Override
    public void __populate(Accessory model) {
        boolean encendida = Boolean.parseBoolean(model.getStatus());
        if(encendida){
            card_status.setImageResource(R.drawable.light_on);
        }
        else{
            card_status.setImageResource(R.drawable.light_off);
        }
        getCardButton().setText(encendida ? context.getString(R.string.apagar) : context.getString(R.string.encender));
        getCardButton().setEnabled(true);
    }


}
