package xyz.santima.homepi.ui.impl.holder.impl;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.santima.homepi.R;
import xyz.santima.homepi.model.Service;
import xyz.santima.homepi.ui.impl.component.CustomMaterialDialogBuilder;
import xyz.santima.homepi.ui.impl.holder.AbstractBasicHolder;

public class GarageHolder extends AbstractBasicHolder {

    @BindView(R.id.garage_card_img) ImageView card_status;
    @BindView(R.id.card_update_date) TextView card_date;
    @BindView(R.id.card_place) TextView card_place;
    @BindView(R.id.card_update_button) Button card_button;
    @BindView(R.id.card) CardView card;

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
    public CardView getCard() {
        return card;
    }

    @Override
    public void showConfiguration(View v) {
        new CustomMaterialDialogBuilder(v.getContext())
                .title(R.string.firebase_configuration)
                .positiveText(R.string.save)
                .build().show();
    }

    @Override
    public void __populate(Service model) {
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
