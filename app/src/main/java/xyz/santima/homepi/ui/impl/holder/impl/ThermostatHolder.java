package xyz.santima.homepi.ui.impl.holder.impl;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.santima.homepi.R;
import xyz.santima.homepi.model.Service;
import xyz.santima.homepi.ui.impl.component.CustomMaterialDialogBuilder;
import xyz.santima.homepi.ui.impl.holder.AbstractBasicHolder;

public class ThermostatHolder extends AbstractBasicHolder {

    @BindView(R.id.card_status) TextView card_status;
    @BindView(R.id.card_place) TextView card_place;
    @BindView(R.id.card_update_date) TextView card_date;
    @BindView(R.id.card_update_button) Button card_button;
    @BindView(R.id.card_button_plus) Button card_button_plus;
    @BindView(R.id.card_button_less) Button card_button_less;
    @BindView(R.id.card) CardView card;

    DatabaseReference ref;
    Service service;

    public ThermostatHolder(LayoutInflater inflater, ViewGroup parent){
        super(inflater.inflate(R.layout.thermostat_card, parent, false));
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
    public void __populate(Service service) {
        setDate(service.getFormatDate());
        setPlace(service.getPlace());

        card_status.setText(service.getStatus() + "º");

        if(service.isWorking()){
            card_button.setText(context.getString(R.string.apagar));
        }
        else{
            card_button.setText(context.getString(R.string.encender));
        }
    }

    @Override
    public void populate(Service model, DatabaseReference ref) {
        this.ref = ref;
        this.service = model;

        __populate(service);
        //super.populate(model, ref);
    }

    @OnClick(R.id.card_update_button)
    public void changeWorking(View view){
        service.setUser(FirebaseAuth.getInstance().getCurrentUser().getUid());
        service.setWorking(!service.isWorking());
        ref.setValue(service);
    }

    @OnClick(R.id.card_button_plus)
    public void plus(View view){
        service.setStatus(Integer.parseInt(service.getStatus()) + 1);
        ref.setValue(service);
    }

    @OnClick(R.id.card_button_less)
    public void less(View view){
        service.setStatus(Integer.parseInt(service.getStatus()) - 1);
        ref.setValue(service);
    }


}
