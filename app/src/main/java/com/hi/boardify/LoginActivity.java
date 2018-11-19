package com.hi.boardify;



import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText studentid = null;
    private EditText password = null;private Button login;
    private int counter = 0;
    // can implement counter for login


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        studentid = (EditText)findViewById(R.id.studentid);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        //onclick for login should bring us to the homepage after validation
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
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


