package com.intimation.sjapps.materiamedica;

import android.app.Fragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int position) {
        PlaceholderFragment fragment = null;
        switch (position) {
            case 0:
                fragment = new RemediesFragment();
                break;
            case 1:
                fragment = new SearchFragment();
                break;
            case 2:
                fragment = new NotesFragment();
                break;
            case 3:
                fragment = new FAQFragment();
                break;
            case 4:
                fragment = new InfoFragment();
                break;
            default:
                fragment = new PlaceholderFragment();
                break;
        }

        return fragment;
    }

    public PlaceholderFragment() {
    }
}
