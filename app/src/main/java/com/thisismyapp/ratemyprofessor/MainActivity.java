package com.thisismyapp.ratemyprofessor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import android.widget.Spinner;
import android.widget.Adapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    private DatabaseReference database;             //Instance variable to access database
    private static Database tempDatabase;
    private DatabaseReference ref;

    /*
    finish - jared
     */
    private Spinner dropDown;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView)findViewById(R.id.listViewProfessors);
        final ArrayList<String> arrayProfessor = new ArrayList<>();
        arrayProfessor.addAll(Arrays.asList(getResources().getStringArray(R.array.search_letters)));
/* finish - jared
        addItemsOnSpinner();
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
*/
        adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                arrayProfessor);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String letter = arrayProfessor.get(i);
                postSearchPage(letter);
            }
        });


        //TESTING WITH DATABASE:
        //Initializing database
        // Write a message to the database
        database = FirebaseDatabase.getInstance().getReference();

//        tempDatabase = new Database(findViewById(R.id.main_layout));
        //testing creating value in database
/*
        database.setValue("NEW TEST PROFESSOR!!!!!!").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Got Value", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error with getting value", Toast.LENGTH_LONG).show();
                }

            }
        });
*/
    }
    public void postSearchPage(String letters){
        Intent postSearchPage = new Intent(this, PostSearch.class);
        postSearchPage.putExtra("professor", letters);
        startActivity(postSearchPage);
    }

    //finish spinner - jared
   /* public void addItemsOnSpinner(){
        dropDown = (Spinner) findViewById(R.id.dropDown);
        List<String> list = new ArrayList<String>();
        list.add();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDown.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection(){
        dropDown = (Spinner) findViewById(R.id.dropDown);
        dropDown.setOnItemSelectedListener(new CustomOnItemSelectedListener);
    }

    public void addListenerOnButton(){
        dropDown = (Spinner) findViewById(R.id.dropDown);
        submitButton = (Button) findViewById(R.id.dropDown);

        submitButton =
    } */

    public void Search(View view){
        Intent i = new Intent(this, PostSearch.class);
        startActivity(i);
    }

    public static Database getDatabase(){
        return tempDatabase;
    }

    public static void setDatabase(Database db){
        tempDatabase = db;
    }

}
