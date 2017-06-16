package xyz.santima.homepi.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import io.realm.Realm;
import xyz.santima.homepi.R;
import xyz.santima.homepi.model.FirebaseConfiguration;
import xyz.santima.homepi.ui.impl.component.CustomMaterialDialogBuilder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccessoryConfigurationFragment extends PreferenceFragmentCompat {

    public static final String ACCESSORY_TYPE_KEY = "0";

    private int accessoryType;

    public static AccessoryConfigurationFragment newInstance(int accessoryType) {
        AccessoryConfigurationFragment fragment = new AccessoryConfigurationFragment();
        Bundle args = new Bundle();
        args.putInt(ACCESSORY_TYPE_KEY, accessoryType);
        fragment.setArguments(args);
        return fragment;
    }


    public AccessoryConfigurationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accessoryType = getArguments().getInt(ACCESSORY_TYPE_KEY);
        }
    }

    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.configuration, rootKey);

    }

}
