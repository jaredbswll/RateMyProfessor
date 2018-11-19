package com.thisismyapp.ratemyprofessor;

import android.text.Layout;
import android.view.View;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class Database{

    public static ArrayList<Professor> database;

    public Database(View v){

        database = new ArrayList<Professor>();

        String[] profClassArray = v.getResources().getStringArray(R.array. array_professor_class);
        String[] profRatingArray = v.getResources().getStringArray(R.array. array_professor_rating);
        String[] profNameArray = v.getResources().getStringArray(R.array. array_professor);

        for (int i = 0; i < profClassArray.length; i++){
            database.add(new Professor(profNameArray[i], profRatingArray[i], profClassArray[i]));
        }
    }

//    public static ArrayList<Professor> getDatabase(){
//        return database;
//    }

}
