package com.thisismyapp.ratemyprofessor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView)findViewById(R.id.listViewProfessors);
        ArrayList<String> arrayProfessor = new ArrayList<>();
        arrayProfessor.addAll(Arrays.asList(getResources().getStringArray(R.array.array_professor)));

        adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                arrayProfessor);
        lv.setAdapter(adapter);
    }
}
