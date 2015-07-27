package com.intimation.sjapps.materiamedica;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gorillalogic on 1/3/15.
 */
public class InfoFragment extends PlaceholderFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_info, container, false);
        return rootView;
    }
}
