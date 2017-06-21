package xyz.santima.homepi.ui.impl.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.OnClick;
import xyz.santima.homepi.R;
import xyz.santima.homepi.model.Accessory;
import xyz.santima.homepi.ui.activity.AccessoryConfigurationActivity;

public abstract class AbstractBasicHolder extends RecyclerView.ViewHolder implements BasicHolder  {

    protected Context context;
    protected DatabaseReference ref;
    protected Accessory accessory;

    public AbstractBasicHolder(View itemView) {
        super(itemView);
    }

    public abstract void setDate(String date);
    public abstract void setPlace(String place);
    public abstract Button getCardButton();
    public abstract void __populate(Accessory accessory);

    @OnClick(R.id.card)
    public void showConfiguration(View v) {
        Intent i = new Intent(v.getContext(), AccessoryConfigurationActivity.class);
        i.putExtra(AccessoryConfigurationActivity.SERVICE_KEY, accessory);
        i.putExtra(AccessoryConfigurationActivity.REF_KEY, ref.getKey());
        v.getContext().startActivity(i);
    }

    public void populate(Accessory accessory, DatabaseReference ref){
        __populate(accessory);
        setupCard(accessory, ref);
    }

    private void setupCard(final Accessory model, final DatabaseReference ref) {
        this.accessory = model;
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

    }

}
