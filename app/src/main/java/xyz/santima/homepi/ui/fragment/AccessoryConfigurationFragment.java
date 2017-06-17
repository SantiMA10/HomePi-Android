package xyz.santima.homepi.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

import xyz.santima.homepi.R;

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
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {

        if (getArguments() != null) {
            accessoryType = getArguments().getInt(ACCESSORY_TYPE_KEY);
        }

        if(accessoryType == 0){
            setPreferencesFromResource(R.xml.configuration_garage, rootKey);
        }
        else{
            setPreferencesFromResource(R.xml.configuration, rootKey);
        }

    }

}
