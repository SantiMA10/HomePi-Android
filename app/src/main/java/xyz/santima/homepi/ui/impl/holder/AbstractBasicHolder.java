package xyz.santima.homepi.ui.impl.holder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;

import xyz.santima.homepi.model.Service;

public abstract class AbstractBasicHolder extends RecyclerView.ViewHolder implements BasicHolder  {

    protected Context context;
    protected DatabaseReference ref;
    protected Service service;

    public AbstractBasicHolder(View itemView) {
        super(itemView);
    }

    public abstract void setDate(String date);
    public abstract void setPlace(String place);
    public abstract Button getCardButton();
    public abstract CardView getCard();
    public abstract void showConfiguration(View v);
    public abstract void __populate(Service service);

    public void populate(Service service, DatabaseReference ref){
        __populate(service);
        setupCard(service, ref);
    }

    private void setupCard(final Service model, final DatabaseReference ref) {
        this.service = model;
        this.ref = ref;
        setDate(model.getFormatDate());
        setPlace(model.getRoom());

        getCardButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setUser(FirebaseInstanceId.getInstance().getToken());
                model.setWorking(true);

                ref.setValue(model);
            }
        });

        getCard().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfiguration(v);
            }
        });
    }

}
