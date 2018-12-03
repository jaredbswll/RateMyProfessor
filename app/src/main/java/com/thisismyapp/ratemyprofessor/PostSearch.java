package com.thisismyapp.ratemyprofessor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PostSearch extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_search);
        ListView lv = (ListView)findViewById(R.id.results_view);
        final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        final ArrayList<String> resultsArray = new ArrayList<>();
        //String[] names = getResources().getStringArray(R.array.array_professor);
        Database database = new Database();
        final String search = this.getIntent().getStringExtra("professor");

        firestore.collection("professors").get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc: task.getResult()) {
                        if (doc.getId().toLowerCase().charAt(0) == search.toLowerCase().charAt(0)) {
                            resultsArray.add(doc.getId());
                        }
                        //This is how you add a new comment to the database
//                        DocumentReference docRef = doc.getReference();
//                        Map<String, Object> comment = new HashMap<String, Object>();
//
//                        comment.put("classDifficulty", "Near Impossible");
//                        comment.put("classTaken", "CSE 888");
//                        comment.put("hpw", 21);
//                        comment.put("rating", 2);
//                        comment.put("user", "NickJack");
//                        comment.put("comment", "This is another test comment");
//                        ArrayList<Map<String, Object>> commentsList =  (ArrayList<Map<String, Object>>) doc.get("comments");
//                        //ArrayList<Map<String, Object>> comments = new ArrayList<Map<String, Object>>();
//                        commentsList.add(comment);
//
//                        docRef.update("comments", commentsList);

                    }
                    finishMe(search, resultsArray);
                }
            }
        });

    }

    public void finishMe(String search, final ArrayList<String> resultsArray){
        ListView lv = (ListView)findViewById(R.id.results_view);
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
                final String professor = resultsArray.get(i);
                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                firestore.collection("professors").document(professor).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot doc = task.getResult();
                                String[] test = new String[3];
                                //0=name, 1=rating, 2=class
                                test[0] = professor;
                                test[1] = doc.get("rating").toString();
                                //ArrayList<String> classes = (ArrayList<String>) doc.get("classes");

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
                                openProfessorPage(test, commentsList);

                            }
                        });
                //openProfessorPage(professor);
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

    private void openProfessorPage(String[] professor, String[] comments){
        Intent professorPage = new Intent(this, ProfessorPage.class);
        professorPage.putExtra("professor", professor);
        professorPage.putExtra("comments", comments);
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
