package xyz.santima.homepi.ui.impl.holder.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.santima.homepi.R;
import xyz.santima.homepi.model.Accessory;
import xyz.santima.homepi.ui.impl.holder.AbstractBasicHolder;

public class ThermostatHolder extends AbstractBasicHolder {

    @BindView(R.id.card_status) TextView card_status;
    @BindView(R.id.card_place) TextView card_place;
    @BindView(R.id.card_update_date) TextView card_date;
    @BindView(R.id.card_update_button) Button card_button;
    @BindView(R.id.card_button_plus) Button card_button_plus;
    @BindView(R.id.card_button_less) Button card_button_less;

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
    public void __populate(Accessory accessory) {
        setDate(accessory.getFormatDate());
        setPlace(accessory.getRoom());

        card_status.setText(accessory.getStatus() + "º");

        if(accessory.isWorking()){
            card_button.setText(context.getString(R.string.apagar));
        }
        else{
            card_button.setText(context.getString(R.string.encender));
        }
    }

    @Override
    public void populate(Accessory model, DatabaseReference ref) {
        this.ref = ref;
        this.accessory = model;

        __populate(accessory);
        //super.populate(model, ref);
    }

    @OnClick(R.id.card_update_button)
    public void changeWorking(View view){
        accessory.setUser(FirebaseInstanceId.getInstance().getToken());
        accessory.setWorking(!accessory.isWorking());
        ref.setValue(accessory);
    }

    @OnClick(R.id.card_button_plus)
    public void plus(View view){
        accessory.setStatus(Integer.parseInt(accessory.getStatus()) + 1);
        ref.setValue(accessory);
    }

    @OnClick(R.id.card_button_less)
    public void less(View view){
        accessory.setStatus(Integer.parseInt(accessory.getStatus()) - 1);
        ref.setValue(accessory);
    }


}
