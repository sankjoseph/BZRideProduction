package com.intimation.sjapps.materiamedica;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intimation.sjapps.materiamedica.controller.Data;
import com.intimation.sjapps.materiamedica.model.RemediesData;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by Anil Kumar on 1/3/15.
 */
public class DetailedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        RemediesData data = Data.getInstance().getData().get(getIntent().getIntExtra("pos", 0));
        setTitle(data.getTitle().toUpperCase());
        ((TextView)findViewById(R.id.sub_title)).setText(data.getSubTitle());
        LinearLayout parent = (LinearLayout) findViewById(R.id.parent);
        LinkedHashMap<String, String> desc = data.getDescription();
        Set<String> keys = desc.keySet();
        if (keys.contains("Generaldesc")) {
            findViewById(R.id.general_desc).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.general_desc)).setText(desc.get("Generaldesc"));
            desc.remove("Generaldesc");
        }
        Iterator<String> keys_iter = desc.keySet().iterator();
        String key;
        boolean isSearch = getIntent().hasExtra("search_str");
        String match = null;
        if (isSearch) {
            String test = getIntent().getCharSequenceExtra("search_str").toString();
            match = getIntent().getBooleanExtra("exact_match", false)
                    ? " " + getIntent().getCharSequenceExtra("search_str").toString() + " "
                    : getIntent().getCharSequenceExtra("search_str").toString();
        }
        do {
            key = keys_iter.next();
            // Characteristic
            TextView k = new TextView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 10, 0, 0);
            lp.gravity = Gravity.CENTER_HORIZONTAL;
            k.setLayoutParams(lp);
            k.setText(key.equals("Rgeneral") ? "Relationship" : key);
            k.setTextColor(getResources().getColor(R.color.orange));
            k.setTypeface(null, Typeface.BOLD);
            if (!isRelationshipPara(key))
                parent.addView(k);

            // Value
            TextView v = new TextView(this);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 10, 0, 0);
            v.setLayoutParams(lp);
            String text = "";
            if (isRelationshipPara(key))
                text = getRelationshipSubHeader(key);
            text = text + desc.get(key);
            if (isSearch) {
                Spannable coloredText = new SpannableString(text);
                int index =0;
                while((index=(text.indexOf(match,index)+1))>0) {
                    coloredText.setSpan(new ForegroundColorSpan(Color.RED)
                            , index - 1
                            , index+match.length() - 1
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                v.setText(coloredText);
            } else {
                v.setText(text);
            }
            v.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Small);
            v.setTextColor(getResources().getColor(android.R.color.black));
            v.setLineSpacing(0, 1.2f);
            parent.addView(v);
        } while (keys_iter.hasNext());
    }

    private boolean isRelationshipPara(String key) {
        if (key.equals("Rcompare")
                || key.equals("Rantidote")
                || key.equals("Rcompliment")
                || key.equals("Rincompatible")
                || key.equals("Rcompatible")
                || key.equals("Rinimical"))
            return true;
        return false;
    }

    private String getRelationshipSubHeader(String key) {
        if (key.equals("Rcompare"))
            return "Compare: ";
        if (key.equals("Rcompliment"))
            return "Complementary: ";
        if (key.equals("Rantidote"))
            return "Antidotes: ";
        if (key.equals("Rcompatible"))
            return "Compatiblity: ";
        if (key.equals("Rincompatible"))
            return "Incompatiblity: ";
        if (key.equals("Rinimical"))
            return "Inimical: ";
        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
