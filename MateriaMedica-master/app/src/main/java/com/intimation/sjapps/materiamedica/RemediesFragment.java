package com.intimation.sjapps.materiamedica;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.intimation.sjapps.materiamedica.controller.Data;
import com.intimation.sjapps.materiamedica.model.RemediesData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anil Kumar on 11/15/14.
 */
public class RemediesFragment extends PlaceholderFragment implements View.OnClickListener {

    Map<String, Integer> mapIndex;
    ListView remediesListView;
    RemediesListAdapter adapter;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_remedies, container, false);
        Data.getInstance().loadData(getActivity());
        remediesListView = (ListView) rootView.findViewById(R.id.list_remedies);
        adapter = new RemediesListAdapter(getActivity(), Data.getInstance().getData());
        remediesListView.setAdapter(adapter);
        remediesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetailedScreen(Data.getInstance().getData().indexOf((RemediesData)parent.getAdapter().getItem(position)));
            }
        });

        getIndexList(Data.getInstance().getData());

        displayIndex(inflater);

        return rootView;
    }

    private void showDetailedScreen(int position) {
        Intent i = new Intent(getActivity(), DetailedActivity.class);
        i.putExtra("pos", position);
        startActivity(i);
    }

    private void getIndexList(List<RemediesData> data) {
        mapIndex = new LinkedHashMap<String, Integer>();
        String index = null;
        for (RemediesData d : data) {
            index = d.getTitle().substring(0, 1);

            if (mapIndex.get(index) == null)
                mapIndex.put(index, data.indexOf(d));
        }
    }

    private void displayIndex(LayoutInflater inflater) {
        LinearLayout indexLayout = (LinearLayout) rootView.findViewById(R.id.side_index);

        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            textView = (TextView) inflater.inflate(
                    R.layout.side_index_item, null);
            textView.setText(index);
            textView.setTextColor(getResources().getColor(R.color.alpha_color));
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            textView.setOnClickListener(this);
            indexLayout.addView(textView);
        }
    }

    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        remediesListView.setSelection(mapIndex.get(selectedIndex.getText()));
    }
}
