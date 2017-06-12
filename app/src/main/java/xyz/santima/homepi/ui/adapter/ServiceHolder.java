package xyz.santima.homepi.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import xyz.santima.homepi.R;

/**
 * Created by GiantsV3 on 12/06/2017.
 */

public class ServiceHolder extends RecyclerView.ViewHolder {

    private TextView card_status;
    private TextView card_date;
    private TextView card_place;
    private Button card_button;

    public ServiceHolder(View itemView) {
        super(itemView);

        card_status = (TextView) itemView.findViewById(R.id.card_status);
        card_date = (TextView) itemView.findViewById(R.id.card_update_date);
        card_place = (TextView) itemView.findViewById(R.id.card_place);
        card_button = (Button) itemView.findViewById(R.id.card_update_button);
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

}
