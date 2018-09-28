package com.thisismyapp.ratemyprofessor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;

public class IntroCard extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_card);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                advance();
            }
        }, 5000);
    }

    private void advance(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
