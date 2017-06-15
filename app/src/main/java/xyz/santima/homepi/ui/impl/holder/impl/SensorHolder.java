package xyz.santima.homepi.ui.impl.holder.impl;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.santima.homepi.R;
import xyz.santima.homepi.model.Service;
import xyz.santima.homepi.ui.impl.component.CustomMaterialDialogBuilder;
import xyz.santima.homepi.ui.impl.holder.AbstractBasicHolder;

public class SensorHolder extends AbstractBasicHolder {

    @BindView(R.id.card_status) TextView card_status;
    @BindView(R.id.card_update_date) TextView card_date;
    @BindView(R.id.card_place) TextView card_place;
    @BindView(R.id.card_update_button) Button card_button;
    @BindView(R.id.card) CardView card;

    public SensorHolder(LayoutInflater inflater, ViewGroup parent){
        super(inflater.inflate(R.layout.text_status_card, parent, false));
        context = parent.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void setStatus(String status) {
        card_status.setText(status);
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
                .addCheckbox("Autorefrsco")
                .addInput("", "Tiempo para autofresco")
                .all(new CustomMaterialDialogBuilder.CustomAllCallback() {
                    @Override
                    public void onAll(MaterialDialog dialog, List<Boolean> checkboxes, List<CharSequence> inputs, boolean allInputsValidated) {
                        for(Boolean checkbox : checkboxes){
                            System.out.println(checkbox);
                        }
                        for(CharSequence input : inputs){
                            System.out.println(input+"");
                        }
                    }
                })
                .title("Configuración")
                .positiveText(R.string.save)
                .show();
    }

    @Override
    public void __populate(Service model) {
        setStatus(model.getType() == Service.SENSOR_HUMIDITY ? model.getStatus() + "%" : model.getStatus() + "º");
        getCardButton().setText(context.getString(R.string.actualizar));
        getCardButton().setEnabled(true);
    }


}
