package com.thisismyapp.ratemyprofessor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SelectSearch extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_search);
    }

    @Override
    public void onBackPressed(){

    }

    public void classSearch(View view){
        Intent i = new Intent(this, ClassSearch.class);
        startActivity(i);
    }

    public void profSearch(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
