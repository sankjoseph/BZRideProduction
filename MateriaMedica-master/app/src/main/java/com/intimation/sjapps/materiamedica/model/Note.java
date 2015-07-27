package com.intimation.sjapps.materiamedica.model;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gorillalogic on 1/23/15.
 */
public class Note {
    private static final String LOCAL_STORE = "notes_prefs";
    private static final String NOTES = "notes_store";

    int id;
    String title, content;

    public Note() {}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public static List<Note> getExistingNotes(Context c) {
        ArrayList<Note> existingNotes = new ArrayList<>();
        SharedPreferences prefs = c.getSharedPreferences(LOCAL_STORE, Context.MODE_PRIVATE);
        if (prefs.contains(NOTES)) {
            JSONObject obj = null;
            try {
                obj = new JSONObject(prefs.getString(NOTES, "{}"));
                Iterator<String> keys = obj.keys();
                String id = "0";
                while (keys.hasNext()) {
                    id = keys.next();
                    existingNotes.add(Note.parseNote(obj.getJSONObject("" + id), id));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return existingNotes;
    }

    private static Note parseNote(JSONObject obj, String id) {
        Note n = new Note();
        n.id = Integer.parseInt(id);
        n.title = obj.optString("title");
        n.content = obj.optString("content");

        return n;
    }

    public static Note getNote(Context c, int id) {
        if (id != -1) {
            SharedPreferences prefs = c.getSharedPreferences(LOCAL_STORE, Context.MODE_PRIVATE);
            JSONObject obj = null;
            try {
                obj = new JSONObject(prefs.getString(NOTES, "{}"));
                obj = obj.getJSONObject("" + id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Note n = new Note();
            n.title = obj.optString("title");
            n.content = obj.optString("content");
            return n;
        }
        return null;
    }

    public static void putNote(Context c, int id, String title, String content) {
        Note n = new Note();
        n.id = id;
        n.title = title;
        n.content = content;

        SharedPreferences prefs = c.getSharedPreferences(LOCAL_STORE, Context.MODE_PRIVATE);
        try {
            JSONObject notes = prefs.contains(NOTES)
                    ? new JSONObject(prefs.getString(NOTES, "{}")) : new JSONObject();
            notes.put(n.getId() == -1 ? "" + n.createId(notes, 0) : "" + n.getId()
                    , new JSONObject()
                    .put("title", n.getTitle())
                    .put("content", n.getContent()
                    ));
            prefs.edit().putString(NOTES, notes.toString()).commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void deleteNote(Context c, int id) {
        SharedPreferences prefs = c.getSharedPreferences(LOCAL_STORE, Context.MODE_PRIVATE);
        if (prefs.contains(NOTES)) {
            try {
                JSONObject notes = new JSONObject(prefs.getString(NOTES, "{}"));
                if (notes.has("" + id)) {
                    notes.remove("" + id);
                }
                prefs.edit().putString(NOTES, notes.toString()).commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private int createId(JSONObject obj, int i) {
        while (obj.has("" + i)) {
            ++i;
        }
        return i;
    }

}
