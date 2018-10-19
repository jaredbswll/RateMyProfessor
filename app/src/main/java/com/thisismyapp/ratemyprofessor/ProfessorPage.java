package com.thisismyapp.ratemyprofessor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ProfessorPage extends AppCompatActivity {

    private String[] profNameArray;
    private String[] profRatingArray;       //String arrays to hold professors values retrieved from strings.xml file
    private String[] profClassArray;

    private String profSearch;              //Variable to be used later the name of the professor that the user searched for

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_page);

        //Initializing arrays (getting them from strings.xml file)
        profClassArray = getResources().getStringArray(R.array. array_professor_class);
        profRatingArray = getResources().getStringArray(R.array. array_professor_rating);
        profNameArray = getResources().getStringArray(R.array. array_professor);

        //This will work:

        //RANDOM VALUE FOR profSearch TO TEST:
        profSearch = "Gao, Jing";

        //Search to get professors correct index to be used by each array
        int index = 0;
        for (int i = 0; i < profNameArray.length; i++){
            if (profSearch.equals(profNameArray[i])){
                index = i;
            }
        }

        //Get values to update the UI with:
        String name = profNameArray[index];
        String rating = profRatingArray[index];
        String profClass = profClassArray[index];

        //Making professor object(do not know if this is needed):
        Professor newProf = new Professor(name, rating, profClass);

        //Update the UI:
        TextView tv1 = (TextView) findViewById(R.id.professor_name);
        tv1.setText(name);
        TextView tv2 = (TextView) findViewById(R.id.professor_rating);
        tv2.setText(rating);
        TextView tv3 = (TextView) findViewById(R.id.professor_class);
        tv3.setText(profClass);

        //TODO: Find out how to store and do this same process for submitted comments

    }
}
