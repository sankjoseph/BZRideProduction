package com.intimation.sjapps.materiamedica;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by gorillalogic on 1/19/15.
 */
public class FAQFragment extends PlaceholderFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_faq, container, false);
        ((WebView)rootView.findViewById(R.id.faq)).loadUrl("file:///android_asset/FAQ.htm");
        return rootView;
    }
}
