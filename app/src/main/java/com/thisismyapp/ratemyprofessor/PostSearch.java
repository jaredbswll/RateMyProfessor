package com.thisismyapp.ratemyprofessor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
        final ArrayList<String> resultsArray = new ArrayList<>();
        String[] names = getResources().getStringArray(R.array.array_professor);
        String search = this.getIntent().getStringExtra("professor");
        for(String name : names){
            if(name.toLowerCase().charAt(0) == search.toLowerCase().charAt(0)){
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String professor = resultsArray.get(i);
                openProfessorPage(professor);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void openProfessorPage(String professor){
        Intent professorPage = new Intent(this, ProfessorPage.class);
        professorPage.putExtra("professor", professor);
        startActivity(professorPage);
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
