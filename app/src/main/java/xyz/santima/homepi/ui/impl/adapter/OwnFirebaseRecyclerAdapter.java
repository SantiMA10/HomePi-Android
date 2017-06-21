package xyz.santima.homepi.ui.impl.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import xyz.santima.homepi.model.Accessory;
import xyz.santima.homepi.ui.impl.holder.AbstractBasicHolder;
import xyz.santima.homepi.ui.impl.holder.impl.GarageHolder;
import xyz.santima.homepi.ui.impl.holder.impl.LightHolder;
import xyz.santima.homepi.ui.impl.holder.impl.SensorHolder;
import xyz.santima.homepi.ui.impl.holder.impl.ThermostatHolder;

public class OwnFirebaseRecyclerAdapter extends FirebaseRecyclerAdapter {

    public OwnFirebaseRecyclerAdapter(Class modelClass, int modelLayout, Class viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(RecyclerView.ViewHolder viewHolder, Object object, int position) {

        Accessory model = (Accessory) object;
        AbstractBasicHolder holder = (AbstractBasicHolder)viewHolder;
        holder.populate(model, this.getRef(position));

    }

    public final int getItemViewType(int position) {
        return ((Accessory)getItem(position)).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case Accessory.GARAGE:
                return new GarageHolder(LayoutInflater.from(parent.getContext()), parent);
            case Accessory.LIGHT:
                return new LightHolder(LayoutInflater.from(parent.getContext()), parent);
            case Accessory.THERMOSTAT:
                return new ThermostatHolder(LayoutInflater.from(parent.getContext()), parent);
            default:
                return new SensorHolder(LayoutInflater.from(parent.getContext()), parent);
        }

    }

}
