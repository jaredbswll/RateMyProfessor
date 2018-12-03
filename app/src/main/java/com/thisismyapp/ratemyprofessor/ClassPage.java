package com.thisismyapp.ratemyprofessor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ClassPage extends AppCompatActivity {

    private ArrayList<String> professors;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_page);
        professors = new ArrayList<String>();
        professors.add("Jesse Hartloff");
        professors.add("Carl Alphonce");
        professors.add("Jennifer Winikus");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                professors);

        ListView lv = (ListView) findViewById(R.id.professorList);
        lv.setAdapter(adapter);

        TextView tv = (TextView) findViewById(R.id.className);
        tv.setText("CSE 115");

        TextView difficulty = (TextView) findViewById(R.id.difficultyTag);
        TextView hoursPerWeek = (TextView) findViewById(R.id.hoursPerWeekTag);
        TextView textbook = (TextView) findViewById(R.id.textbookTag);

        difficulty.append("  Fairly Easy");
        hoursPerWeek.append("  8-10");
        textbook.append("  None");
    }
}
