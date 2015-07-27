package com.intimation.sjapps.materiamedica;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.intimation.sjapps.materiamedica.controller.Data;
import com.intimation.sjapps.materiamedica.model.RemediesData;

import java.util.List;

/**
 * Created by Anil Kumar on 12/31/14.
 */
public class SearchFragment extends PlaceholderFragment {

    ListView remediesListView;
    View rootView;
    RemediesListAdapter adapter;
    List<RemediesData> list;
    CharSequence search_str;
    boolean mIsExactMatch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        list = Data.getInstance().getData();

        remediesListView = (ListView) rootView.findViewById(R.id.list_remedies);
        adapter = new RemediesListAdapter(getActivity(), list);
        remediesListView.setAdapter(adapter);
        remediesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetailedScreen(list.indexOf((RemediesData)parent.getAdapter().getItem(position)));
            }
        });

        ((Switch)rootView.findViewById(R.id.search_exact_match_switch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapter.setExactMatchFilter(isChecked);
                mIsExactMatch = isChecked;
                filter();
            }
        });

        ((EditText)rootView.findViewById(R.id.search_editbox)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search_str = s;
                if (search_str != null) {
                    filter();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
    }

    private void filter() {
        adapter.getFilter().filter(search_str);
    }

    private void showDetailedScreen(int position) {
        Intent i = new Intent(getActivity(), DetailedActivity.class);
        i.putExtra("pos", position);
        if (search_str != null){
            i.putExtra("search_str", search_str);
        }
        i.putExtra("exact_match", mIsExactMatch);
        startActivity(i);
    }
}

