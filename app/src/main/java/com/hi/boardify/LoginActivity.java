package com.hi.boardify;



import android.app.TabActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseUser user;
    private EditText studentid, password;
    private Button login;
    //private int counter = 0;
    // can implement counter for login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        studentid = findViewById(R.id.studentid);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        //onclick for login should bring us to the homepage after validation
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginstudentid = studentid.getText().toString().trim();
                String loginpassword = password.getText().toString().trim();

                if(TextUtils.isEmpty(loginstudentid)) {
                    Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(loginpassword)) {
                    Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(loginstudentid, loginpassword)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    user = auth.getCurrentUser();
                                    finish();
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Login is unsuccessful",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }


// method to check for the validity of the username and password
//    private void validate(String studentid,String password){
//        if (studentid == "1002751" && password == "1234"){
//            Intent intent = new Intent(MainActivity.this,TabManager.class);
//            startActivity(intent);
//        }
    //if statement to track login counter
}


