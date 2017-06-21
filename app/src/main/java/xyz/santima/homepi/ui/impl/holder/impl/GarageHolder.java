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

public class GarageHolder extends AbstractBasicHolder {

    @BindView(R.id.garage_card_img) ImageView card_status;
    @BindView(R.id.card_update_date) TextView card_date;
    @BindView(R.id.card_place) TextView card_place;
    @BindView(R.id.card_update_button) Button card_button;

    public GarageHolder(LayoutInflater inflater, ViewGroup parent){
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
        String target = "";
        boolean enabled = true;
        switch (model.getStatus()){
            case "0":
                card_status.setImageResource(R.drawable.garage_open);
                target = context.getString(R.string.cerrar);
                enabled = true;
                break;
            case "1":
                card_status.setImageResource(R.drawable.garage_close);
                target = context.getString(R.string.abrir);
                enabled = true;
                break;
            case "2":
                target = context.getString(R.string.abriendo);
                enabled = false;
                break;
            case "3":
                target = context.getString(R.string.cerrando);
                enabled = false;
                break;
        }
        getCardButton().setText(target);
        getCardButton().setEnabled(enabled);
    }


}
