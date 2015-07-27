package com.intimation.sjapps.materiamedica;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.intimation.sjapps.materiamedica.model.Note;

import java.util.List;

/**
 * Created by gorillalogic on 1/23/15.
 */
public class NotesListAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<Note> mItems;

    public NotesListAdapter(Activity a, List<Note> items) {
        mActivity = a;
        setItems(items);
    }

    public void setItems(List<Note> items) {
        mItems = items;
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
            convertView = ((LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.remedies_list_item, null);
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

        convertView.setId(mItems.get(position).getId());

        return convertView;
    }

    private class ViewHolder {
        TextView title;
        View divider;
    }
}
