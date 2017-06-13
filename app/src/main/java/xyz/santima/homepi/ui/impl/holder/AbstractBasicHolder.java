package xyz.santima.homepi.ui.impl.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;

import xyz.santima.homepi.model.Service;

public abstract class AbstractBasicHolder extends RecyclerView.ViewHolder implements BasicHolder  {

    protected Context context;

    public AbstractBasicHolder(View itemView) {
        super(itemView);
    }

    public abstract void setDate(String date);
    public abstract void setPlace(String place);
    public abstract Button getCardButton();
    public abstract void __populate(Service service);

    public void populate(Service service, DatabaseReference ref){
        __populate(service);
        setupCard(service, ref);
    }

    private void setupCard(final Service model, final DatabaseReference ref) {
        setDate(model.getFormatDate());
        setPlace(model.getPlace());

        getCardButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setUser("home-pi-android");
                model.setWorking(true);

                ref.setValue(model);
            }
        });
    }

}
