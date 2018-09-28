package com.thisismyapp.ratemyprofessor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class PostSearch extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_search);
        ListView lv = (ListView)findViewById(R.id.results_view);
        ArrayList<String> resultsArray = new ArrayList<>();
        String[] names = getResources().getStringArray(R.array.array_professor);
        char search = 's';
        for(String name : names){
            if(name.toLowerCase().charAt(0) == search){
                resultsArray.add(name);
            }
        }
        TextView results_template = (TextView)findViewById(R.id.results_template);
        results_template.append("\"" + search + "\"");
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                resultsArray);
        lv.setAdapter(adapter);
    }

    public void returnToSearch(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void GoToProfessorPage(View view){
        Intent in = new Intent(this, ProfessorPage.class);
        startActivity(in);
    }
}
