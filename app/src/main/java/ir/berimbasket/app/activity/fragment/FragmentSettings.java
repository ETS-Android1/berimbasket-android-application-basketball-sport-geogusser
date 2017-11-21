package ir.berimbasket.app.activity.fragment;


import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import ir.berimbasket.app.R;
import ir.berimbasket.app.util.ApplicationLoader;
import ir.berimbasket.app.util.Redirect;

public class FragmentSettings extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {

    private final static String URL_PREFERENCE_HELP = "http://berimbasket.ir/help";
    private final static String URL_PREFERENCE_TERMS_AND_SERVICES = "http://berimbasket.ir/terms";
    private final static String URL_PREFERENCE_ABOUT_US = "http://berimbasket.ir/about";
    private final static String URL_PREFERENCE_CHANGE_LOG = "http://berimbasket.ir/changelog";
    private Preference help, aboutUs, terms, changeLog;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preference_settings);

        help = findPreference(getString(R.string.key_pref_help));
        aboutUs = findPreference(getString(R.string.key_pref_about_us));
        terms = findPreference(getString(R.string.key_pref_terms_and_services));
        changeLog = findPreference(getString(R.string.key_pref_change_log));
        help.setOnPreferenceClickListener(this);
        aboutUs.setOnPreferenceClickListener(this);
        terms.setOnPreferenceClickListener(this);
        changeLog.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (key.equals(help.getKey())) {
            // Tracking Event (Analytics)
            ApplicationLoader.getInstance().trackEvent(getString(R.string.analytics_category_settings), getString(R.string.analytics_action_help), "");
            Redirect.sendToCustomTab(getActivity(), URL_PREFERENCE_HELP);
            return true;
        } else if (key.equals(aboutUs.getKey())) {
            // Tracking Event (Analytics)
            ApplicationLoader.getInstance().trackEvent(getString(R.string.analytics_category_settings), getString(R.string.analytics_action_about), "");
            Redirect.sendToCustomTab(getActivity(), URL_PREFERENCE_ABOUT_US);
            return true;
        } else if (key.equals(terms.getKey())) {
            // Tracking Event (Analytics)
            ApplicationLoader.getInstance().trackEvent(getString(R.string.analytics_category_settings), getString(R.string.analytics_action_terms), "");
            Redirect.sendToCustomTab(getActivity(), URL_PREFERENCE_TERMS_AND_SERVICES);
            return true;
        } else if (key.equals(changeLog.getKey())) {
            // Tracking Event (Analytics)
            ApplicationLoader.getInstance().trackEvent(getString(R.string.analytics_category_settings), getString(R.string.analytics_action_change_log), "");
            Redirect.sendToCustomTab(getActivity(), URL_PREFERENCE_CHANGE_LOG);
            return true;
        }
        return false;
    }
}