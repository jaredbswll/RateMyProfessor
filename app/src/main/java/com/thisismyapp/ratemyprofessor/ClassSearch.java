package com.thisismyapp.ratemyprofessor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ClassSearch extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_search);
        //ListView lv = (ListView)findViewById(R.id.listViewClasses);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("classes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<String> retVal = new ArrayList<String>();
                    for(QueryDocumentSnapshot doc: task.getResult()){
                        ArrayList<DocumentReference> profs = (ArrayList<DocumentReference>) doc.get("professors");
                        if(profs.size() > 0){
                            retVal.add(doc.getId());
                        }
                    }
                    finishMe(retVal);
                }
            }
        });
    }

    private void finishMe(final ArrayList<String> classes){
        ListView lv = (ListView)findViewById(R.id.listViewClasses);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                classes);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String classed = classes.get(position);
                openPage(classed);

            }
        });
    }

    private void openPage(String classed){
        Intent classPage = new Intent(this, ClassPage.class);
        classPage.putExtra("class", classed);
        startActivity(classPage);
    }
}
