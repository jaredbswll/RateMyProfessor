package com.thisismyapp.ratemyprofessor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class ClassPage extends AppCompatActivity {

    private ArrayList<String> professors;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_page);

        firestore = FirebaseFirestore.getInstance();
        String className = this.getIntent().getStringExtra("class");
        TextView tv = (TextView) findViewById(R.id.className);
        tv.setText(className);

        firestore.collection("classes").document(className)
        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ArrayList<String> profs = new ArrayList<String>();
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    //DocumentReference docRef = doc.getReference();
                    ArrayList<DocumentReference> teachers = (ArrayList<DocumentReference>) doc.get("professors");
                    for(DocumentReference teach: teachers){
                        profs.add(teach.getId());
                    }
                    finishMe(profs);
                }
            }
        });




        /*professors = new ArrayList<String>();
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
        textbook.append("  None");*/
    }

    private void finishMe(final ArrayList<String> professors){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                professors);

        ListView lv = (ListView) findViewById(R.id.professorList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String professor = professors.get(position);
                firestore.collection("professors").document(professor).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot doc = task.getResult();

                                    String[] test = new String[3];
                                    //0=name, 1=rating, 2=class
                                    test[0] = professor;
                                    test[1] = doc.get("rating").toString();
                                    ArrayList<String> classesList = (ArrayList<String>) doc.get("classes");

                                    ArrayList<DocumentReference> classes = (ArrayList<DocumentReference>) doc.get("classesRef");

                                    String classNames = "";
                                    for(DocumentReference dRef: classes){
                                        if(classNames.equals("")){
                                            classNames = dRef.getId();
                                        }
                                        else {
                                            classNames = classNames + ", " + dRef.getId();
                                        }
                                    }

                                    //test[2] = doc.getString("class");
                                    //test[2] = classes.toString();
                                    test[2] = classNames;


                                    ArrayList<Map<String, Object>> comments = (ArrayList<Map<String, Object>>) doc.get("comments");
                                    String[] commentsList = new String[comments.size() * 6];
                                    int index = 0;
                                    for(Map<String, Object> com: comments){
                                        for(Object elem: com.values()){
                                            String temp = elem.toString();
                                            commentsList[index] = temp;
                                            index += 1;
                                        }
                                    }

                                    openPage(test, commentsList, classesList);
                                }
                            }
                        });
            }
        });
    }

    private void openPage(String[] professor, String[] comments, ArrayList<String> classesList){
        Intent professorPage = new Intent(this, ProfessorPage.class);
        professorPage.putExtra("professor", professor);
        professorPage.putExtra("comments", comments);
        professorPage.putStringArrayListExtra("classesList", classesList);
        startActivity(professorPage);
    }
}
