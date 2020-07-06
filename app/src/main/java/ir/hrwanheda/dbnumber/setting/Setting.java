package ir.hrwanheda.dbnumber.setting;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

import ir.hrwanheda.dbnumber.R;


public class Setting extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settinglay);
        ListPreference colour = (ListPreference) findPreference ("callerInfo");
        colour.setValueIndex(2);
    }
}