package com.intimation.sjapps.materiamedica;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.intimation.sjapps.materiamedica.model.Note;

/**
 * Created by gorillalogic on 1/19/15.
 */
public class NotesEditViewActivity extends Activity {

    private boolean showSaveButton;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_edit_view);
        mId = getIntent().getIntExtra("id", -1);
        if (mId == -1) {
            // New Note
            edit();
        } else {
            view();
        }
    }

    private void edit() {
        showSaveButton = true;
        findViewById(R.id.view).setVisibility(View.GONE);
        findViewById(R.id.edit).setVisibility(View.VISIBLE);
        invalidateOptionsMenu();
        if (mId != -1) {
            Note n = Note.getNote(NotesEditViewActivity.this, mId);
            ((TextView)findViewById(R.id.titleEdit)).setText(n.getTitle());
            ((TextView)findViewById(R.id.contentEdit)).setText(n.getContent());
        }
    }

    private void view() {
        showSaveButton = false;
        findViewById(R.id.edit).setVisibility(View.GONE);
        findViewById(R.id.view).setVisibility(View.VISIBLE);
        invalidateOptionsMenu();
        if (mId != -1) {
            Note n = Note.getNote(NotesEditViewActivity.this, mId);
            ((TextView)findViewById(R.id.title)).setText(n.getTitle());
            ((TextView)findViewById(R.id.content)).setText(n.getContent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        menu.findItem(R.id.save).setVisible(showSaveButton);
        menu.findItem(R.id.edit).setVisible(!showSaveButton);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                edit();
                break;

            case R.id.save:
                Note.putNote(NotesEditViewActivity.this
                        , mId
                        , ((EditText)findViewById(R.id.titleEdit)).getText().toString()
                        , ((EditText)findViewById(R.id.contentEdit)).getText().toString());
                invalidateOptionsMenu();
                setResult(RESULT_OK);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
