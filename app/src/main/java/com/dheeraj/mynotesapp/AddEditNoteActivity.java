package com.dheeraj.mynotesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class AddEditNoteActivity extends AppCompatActivity {
    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);
        initViews();
    }

    private void initViews() {
        EditText mEditText = findViewById(R.id.add_edit_note_text);
        Intent getIntent = getIntent();
        noteId = getIntent.getIntExtra("noteId", -1);
        if (noteId != -1) {
            mEditText.setText(MainActivity.notesList.get(noteId));
        } else {
            MainActivity.notesList.add("");
            noteId = MainActivity.notesList.size() - 1;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.notesList.set(noteId, String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences mSharedPreferences = getApplicationContext().getSharedPreferences("com.dheeraj.mynotesapp", Context.MODE_PRIVATE);
                HashSet<String> hashSet = new HashSet<>(MainActivity.notesList);
                mSharedPreferences.edit().putStringSet("my_shared_prefs", hashSet).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
