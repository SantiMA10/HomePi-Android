package xyz.santima.homepi.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.santima.homepi.R;
import xyz.santima.homepi.model.Service;
import xyz.santima.homepi.ui.adapter.ServiceHolder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.home_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //Configurado el recycleview
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/service");

        recyclerView.setAdapter(new FirebaseRecyclerAdapter<Service, ServiceHolder>(Service.class, R.layout.sensor_card, ServiceHolder.class, ref) {
            @Override
            protected void populateViewHolder(ServiceHolder viewHolder, final Service model, final int position) {
                viewHolder.setStatus(model.getStatus() + " " + (model.getType().equals("temperature") ? "º" : "%") );
                viewHolder.setDate(model.getDate());
                viewHolder.setPlace(model.getPlace());

                final DatabaseReference ref = this.getRef(position);

                viewHolder.getCardButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model.setUser("home-pi-android");
                        model.setWorking(true);

                        ref.setValue(model);
                    }
                });

            }

        });
    }

}
