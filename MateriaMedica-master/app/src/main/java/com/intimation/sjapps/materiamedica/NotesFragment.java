package com.intimation.sjapps.materiamedica;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.intimation.sjapps.materiamedica.model.Note;

/**
 * Created by gorillalogic on 1/19/15.
 */
public class NotesFragment extends PlaceholderFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private NotesListAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notes, container, false);

        ListView lv = (ListView) rootView.findViewById(R.id.list_notes);
        mAdapter = new NotesListAdapter(getActivity(), Note.getExistingNotes(getActivity()));
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(this);
        registerForContextMenu(lv);

        LinearLayout addNote = (LinearLayout) rootView.findViewById(R.id.add_note);
        addNote.setOnClickListener(this);
        addNote.getChildAt(0).setOnClickListener(this);
        addNote.getChildAt(1).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_options, menu);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        MenuItem deleteOption = menu.findItem(R.id.delete);
        deleteOption.setIntent(new Intent().putExtra("id", info.targetView.getId()));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                Note.deleteNote(getActivity(), item.getIntent().getIntExtra("id", -1));
                refresh();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void refresh() {
        mAdapter.setItems(Note.getExistingNotes(getActivity()));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        startActivityForResult(new Intent(getActivity(), NotesEditViewActivity.class)
                .putExtra("id", -1)
                , 200);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivityForResult(new Intent(getActivity(), NotesEditViewActivity.class)
                .putExtra("id", view.getId())
                , 200);
    }
}
