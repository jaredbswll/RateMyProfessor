package com.thisismyapp.ratemyprofessor;

import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.View;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Database{

    //public static ArrayList<Professor> database;
    public FirebaseFirestore _firestore;
    public ArrayList<String> _retVal;
    private PostSearch _page;
    public boolean accessed;

    public Database(){
        _firestore = FirebaseFirestore.getInstance();
        _retVal = new ArrayList<String>();
        accessed = false;

    }

//    public static ArrayList<Professor> getDatabase(){
//        return database;
//    }

    //Returns list of professor names as list of Strings
    public void setAllProfessors(){
        //Task<QuerySnapshot> test = _firestore.collection("professors").get();

    }

    public ArrayList<String> getRetval(){
        return _retVal;
    }

    public Professor getProfessor(String professorName){

        return null;
    }

    public ArrayList<String> getClassesForProfessor(String professor){

        return null;
    }

    public ArrayList<String> getAllClasses(){

        return null;
    }


}
