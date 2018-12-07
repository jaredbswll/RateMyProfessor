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

    ArrayAdapter<String> adapter;
    private static Database tempDatabase;

    /*
    finish - jared
     */
    private Spinner dropDown;
    private Button submitButton;
    private FirebaseFirestore fs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView)findViewById(R.id.listViewProfessors);
        final ArrayList<String> arrayProfessor = new ArrayList<>();
        arrayProfessor.addAll(Arrays.asList(getResources().getStringArray(R.array.search_letters)));

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
        //fs = FirebaseFirestore.getInstance();

//        CollectionReference profRef = fs.collection("professors");
//        com.google.firebase.firestore.Query query = profRef.whereEqualTo("rating", 10);
//        int i = 0;
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
