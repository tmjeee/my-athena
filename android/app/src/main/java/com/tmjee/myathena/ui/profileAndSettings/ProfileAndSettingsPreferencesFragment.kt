package com.tmjee.myathena.ui.profileAndSettings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.tmjee.myathena.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileAndSettingsPreferencesFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.profile_and_settings, rootKey)
    }

}