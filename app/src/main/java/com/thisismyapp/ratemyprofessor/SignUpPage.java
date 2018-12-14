package com.thisismyapp.ratemyprofessor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpPage extends AppCompatActivity implements View.OnClickListener {

    private EditText emailInput, passwordInput, passwordConfirm;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        auth = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.email_input_sign_up);
        passwordInput = findViewById(R.id.password_input_sign_up);
        passwordConfirm = findViewById(R.id.password_confirm_sign_up);

        progressBar = findViewById(R.id.sign_up_progress_bar);

        findViewById(R.id.sign_me_up_button).setOnClickListener(this);
    }

    @Override
    public void onBackPressed(){
        //Go back to sign in page
        Intent intent = new Intent(this, SignInPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = passwordConfirm.getText().toString().trim();
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
        else if(!password.equals(confirmPassword)){
            passwordConfirm.setError("Does not match original password");
            passwordConfirm.requestFocus();
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            final SignUpPage currentPage = this;
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
}
