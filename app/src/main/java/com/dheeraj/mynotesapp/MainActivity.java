package com.dheeraj.mynotesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notesList = new ArrayList<>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initList();
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
            startActivity(intent);
        });
    }

    private void initList() {
        final ListView listView = findViewById(R.id.list_view);

        SharedPreferences mSharedPreferences = getApplicationContext().getSharedPreferences("com.dheeraj.mynotesapp", Context.MODE_PRIVATE);
        HashSet<String> mHashSet = (HashSet<String>)mSharedPreferences.getStringSet("my_shared_prefs", null);

        if(mHashSet != null){
            notesList = new ArrayList<>(mHashSet);
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notesList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
            intent.putExtra("noteId", i);
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            final int itemToDelete = i;

            new AlertDialog.Builder(MainActivity.this)
                    .setMessage(R.string.delete_title)
                    .setPositiveButton(R.string.yes, (dialogInterface, i1) -> {
                        notesList.remove(itemToDelete);
                        arrayAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();

            return true;
        });
    }
}
