package xyz.santima.homepi.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.santima.homepi.R;
import xyz.santima.homepi.model.Accessory;
import xyz.santima.homepi.ui.activity.AccessoryConfigurationActivity;
import xyz.santima.homepi.ui.impl.adapter.OwnFirebaseRecyclerAdapter;
import xyz.santima.homepi.ui.impl.holder.impl.SensorHolder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    @BindView(R.id.home_recycler_view) RecyclerView recyclerView;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        initRecycleView();

        return view;
    }

    @OnClick(R.id.floatingActionButton)
    protected void onClickFAB() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.new_accessory)
                .items(new String[]{"Garaje", "Temperatura", "Humedad", "Luz", "Termostato"})
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        Intent i = new Intent(getContext(), AccessoryConfigurationActivity.class);
                        i.putExtra(AccessoryConfigurationActivity.TYPE, dialog.getItems().indexOf(text));
                        startActivity(i);

                        return true;
                    }
                })
                .positiveText(R.string.create)
                .show();
    }

    private void initRecycleView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/service");

        recyclerView.setAdapter(new OwnFirebaseRecyclerAdapter(Accessory.class, R.layout.text_status_card, SensorHolder.class, ref));
    }

}
