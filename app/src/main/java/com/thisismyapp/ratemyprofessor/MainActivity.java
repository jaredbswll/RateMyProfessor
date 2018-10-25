package com.thisismyapp.ratemyprofessor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    private DatabaseReference database;             //Instance variable to access database
    private DatabaseReference ref;

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

        //Initializing database:
        database = FirebaseDatabase.getInstance().getReference();
        //testing creating value in database

        /*database.setValue("This is a test adding value to child").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Got Value", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error with getting value", Toast.LENGTH_LONG).show();
                }

            }
        });*/
    }

    public void Search(View view){
        Intent i = new Intent(this, PostSearch.class);
        startActivity(i);
    }

}
