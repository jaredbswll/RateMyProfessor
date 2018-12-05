package com.thisismyapp.ratemyprofessor;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    private static Database tempDatabase;
    private FirebaseFirestore fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchView sv = (SearchView)findViewById(R.id.search_view);
        ListView lv = (ListView)findViewById(R.id.results_view);
        final ArrayList<String> arrayProfessor = new ArrayList<>();
        arrayProfessor.addAll(Arrays.asList(getResources().getStringArray(R.array.array_professor)));

        adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                arrayProfessor);
        lv.setAdapter(adapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (list.contains(query)){
                    adapter.getFilter().filter(query);
                }
                else{
                    Toast.makeText(MainActivity.this,"No match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //This block is different from my previous version - Jared
        /*
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String letter = arrayProfessor.get(i);
                postSearchPage(letter);
            }
        });
         */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String letter = arrayProfessor.get(i);
                postSearchPage(letter);
            }
        });

        fs = FirebaseFirestore.getInstance();

//        CollectionReference profRef = fs.collection("professors");
//        com.google.firebase.firestore.Query query = profRef.whereEqualTo("rating", 10);
        int i = 0;
//        ArrayList<DocumentReference> professors = new ArrayList<DocumentReference>();
//
//        String cse = "CSE ";
//
//        for(int inc = 0; inc < 400; inc++){
//            int number = 100 + inc;
//            String num = Integer.toString(number);
//
//            while(num.length() < 3){
//                num = "0" + num;
//            }
//
//            String addMe = "CSE " + num;
//
//            Map<String, Object> maps = new HashMap<String, Object>();
//            maps.put("professors", professors);
//            fs.collection("classes").document(addMe).set(maps);
//
//        }
        //fs.collection("professors").get();
//        fs.collection("professors").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()) {
//                    for (QueryDocumentSnapshot doc : task.getResult()) {
//                        //DocumentSnapshot doc = task.getResult();
//                        final DocumentReference docRef = doc.getReference();
//
//                        ArrayList<String> classes = (ArrayList<String>) doc.get("classes");
//                        final ArrayList<DocumentReference> updatedClasses = new ArrayList<DocumentReference>();
//
////                    Map<String, Object> maps = new HashMap<String, Object>();
////                    maps.put("classesRef", updatedClasses);
//
//                        //docRef.update("classesRef", updatedClasses);
//
//                        for (String clas : classes) {
//                            //String filename = "/classes/" + clas;
//                            fs.collection("classes").document(clas).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        DocumentSnapshot doc2 = task.getResult();
//                                        DocumentReference docRef2 = doc2.getReference();
//
//                                        updatedClasses.add(doc2.getReference());
//                                        docRef.update("classesRef", updatedClasses);
//
//                                        ArrayList<DocumentReference> profs = (ArrayList<DocumentReference>) doc2.get("professors");
//
//                                        profs.add(docRef);
//                                        docRef2.update("professors", profs);
//
//                                    }
//                                }
//                            });
//
//                        }
//                    }
//                }
//            }
//        });






        //TESTING WITH DATABASE:

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
    public void postSearchPage(String professors){
        Intent postSearchPage = new Intent(this, PostSearch.class);
        postSearchPage.putExtra("professor", professors);
        startActivity(postSearchPage);
    }

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
