package com.thisismyapp.ratemyprofessor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SelectSearch extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_search);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        findViewById(R.id.logout_button).setOnClickListener(this);

        TextView curuser = (TextView) findViewById(R.id.current_user_display);
        curuser.setText((CharSequence) auth.getCurrentUser().getEmail());
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

    @Override
    public void onClick(View v) {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this, SignInPage.class);
        startActivity(i);
        finish();
    }
}
