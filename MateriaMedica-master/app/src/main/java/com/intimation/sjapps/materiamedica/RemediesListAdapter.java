package com.intimation.sjapps.materiamedica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.intimation.sjapps.materiamedica.controller.Data;
import com.intimation.sjapps.materiamedica.model.RemediesData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Anil Kumar on 12/31/14.
 */
public class RemediesListAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private List<RemediesData> mItems;
    private List<RemediesData> arrayList;
    private Filter mFilter;
    private boolean mIsExactMatch;

    public RemediesListAdapter(Context c, List<RemediesData> items) {
        mContext = c;
        mItems = items;
        arrayList = new ArrayList<>();
        arrayList.addAll(mItems);
    }

    public void setExactMatchFilter(boolean exactMatch) {
        mIsExactMatch = exactMatch;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.remedies_list_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.divider = convertView.findViewById(R.id.div);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(mItems.get(position).getTitle());
        holder.divider.setVisibility(View.VISIBLE);
        if (position == 0)
            holder.divider.setVisibility(View.INVISIBLE);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    String search_str = constraint.toString().toLowerCase(Locale.getDefault());
                    FilterResults results = new FilterResults();
                    if (constraint == null || constraint.length() < 2) {
                        results.count = arrayList.size();
                        results.values = arrayList;
                    } else {
                        List<RemediesData> filteredList = Data.getInstance()
                                .filterData(mContext, search_str, mIsExactMatch);
                        results.count = filteredList.size();
                        results.values = filteredList;
                    }
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null) {
                        if(results.values!= null) {
                            mItems = (List<RemediesData>) results.values;
                            notifyDataSetChanged();
                        }
                    }
                }
            };
        }
        return mFilter;
    }

    private class ViewHolder {
        TextView title;
        View divider;
    }
}
