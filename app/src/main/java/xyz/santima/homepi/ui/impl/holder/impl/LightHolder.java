package xyz.santima.homepi.ui.impl.holder.impl;

import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.santima.homepi.R;
import xyz.santima.homepi.model.Service;
import xyz.santima.homepi.ui.impl.component.CustomMaterialDialogBuilder;
import xyz.santima.homepi.ui.impl.holder.AbstractBasicHolder;

public class LightHolder extends AbstractBasicHolder {

    @BindView(R.id.garage_card_img) ImageView card_status;
    @BindView(R.id.card_update_date) TextView card_date;
    @BindView(R.id.card_place) TextView card_place;
    @BindView(R.id.card_update_button) Button card_button;
    @BindView(R.id.card) CardView card;

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
    public CardView getCard() {
        return card;
    }

    @Override
    public void showConfiguration(View v) {
        boolean contains = false;
        Map<String, Object> config = service.getConfig();
        if(config.containsKey("notification")){
            contains = ((List<String>)config.get("notification"))
                    .contains(FirebaseInstanceId.getInstance().getToken());
        }

        new CustomMaterialDialogBuilder(v.getContext())
                .addCheckbox("Notificaciones", contains)
                .checboxes(new CustomMaterialDialogBuilder.CustomCheckboxesCallback() {
                    @Override
                    public void onCheckBoxes(MaterialDialog dialog, List<Boolean> checkboxes) {
                        Map<String, Object> config = service.getConfig();
                        if(checkboxes.get(0)){
                            if(config.containsKey("notification")){
                                List<String> notification = ((List<String>)config.get("notification"));
                                if(!notification.contains(FirebaseInstanceId.getInstance().getToken())){
                                    notification.add(FirebaseInstanceId.getInstance().getToken());
                                }
                            }
                            else{
                                List<String> notification = new ArrayList<>();
                                notification.add(FirebaseInstanceId.getInstance().getToken());
                                config.put("notification", notification);
                            }
                        }
                        else{
                            if(config.containsKey("notification")){
                                ((List<String>)config.get("notification"))
                                        .remove(FirebaseInstanceId.getInstance().getToken());
                            }
                        }
                        service.setConfig(config);
                        ref.setValue(service);
                    }
                })
                .title("Configuración")
                .positiveText(R.string.save)
                .show();
    }

    @Override
    public void __populate(Service model) {
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
