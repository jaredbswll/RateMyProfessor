package com.thisismyapp.ratemyprofessor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;

public class SignInPage extends AppCompatActivity implements View.OnClickListener {

    private EditText emailInput, passwordInput, usernameInput;
    private ProgressBar progressBar, circleBar;
    private FirebaseAuth auth;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            Intent intent = new Intent(this, SelectSearch.class);
            startActivity(intent);
            finish();
        }

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        //usernameInput = findViewById(R.id.sign_up_username_input);

        progressBar = findViewById(R.id.sign_in_progress_bar);
        //circleBar = findViewById(R.id.circle_bar);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_up_button).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_up_button:
                signUp();
                break;
        }
    }

    private void signUp(){
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        //final String username = usernameInput.getText().toString().trim();

        if(email.isEmpty()){
            emailInput.setError("Email is required");
            emailInput.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailInput.setError("Valid email is required");
            emailInput.requestFocus();
        }
        else if(password.isEmpty()){
            passwordInput.setError("Password is required");
            passwordInput.requestFocus();
        }
        else if(password.length() < 6){
            passwordInput.setError("Password must be at least 6 characters");
            passwordInput.requestFocus();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            final SignInPage currentPage = this;
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if(task.isSuccessful()){
                        //bring to profile page OR search select
                        Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(currentPage, SelectSearch.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    private void signIn(){
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        //final String username = usernameInput.getText().toString().trim();

        if(email.isEmpty()){
            emailInput.setError("Email is required");
            emailInput.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailInput.setError("Valid email is required");
            emailInput.requestFocus();
        }
        else if(password.isEmpty()){
            passwordInput.setError("Password is required");
            passwordInput.requestFocus();
        }
        else if(password.length() < 6){
            passwordInput.setError("Password must be at least 6 characters");
            passwordInput.requestFocus();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            final SignInPage currentPage = this;
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.INVISIBLE);
                    if(task.isSuccessful()){
                        //bring to profile page OR search select
                        Toast.makeText(getApplicationContext(), "Sign In Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(currentPage, SelectSearch.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Exception exc = task.getException();
                        if(exc instanceof FirebaseAuthEmailException){
                            emailInput.setError("Incorrect email address");
                            emailInput.requestFocus();
                        }
                        else if(exc instanceof FirebaseAuthInvalidCredentialsException){
                            passwordInput.setError("Incorrect password");
                            passwordInput.requestFocus();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

}
