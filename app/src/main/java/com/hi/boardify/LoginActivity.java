package com.hi.boardify;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private String storeusername,storepassword;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private EditText email, password;
    private Button login;
    public String userid;
    DataHolder dataHolder;
    RadioButton radioButton;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    //private int counter = 0;
    // can implement counter for login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);
        dataHolder = DataHolder.getInstance();
        email = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPassword);
        login = findViewById(R.id.loginButton);
        auth = FirebaseAuth.getInstance();
        loginPreferences = getSharedPreferences("loginPrefs",MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        radioButton = findViewById(R.id.radioButton);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        TextView needHelp = findViewById(R.id.needHelp);
        String help ="Need help? Click here.";
        SpannableString ss = new SpannableString(help);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        if (saveLogin == true) {
            email.setText(loginPreferences.getString("storeusername", ""));
            password.setText(loginPreferences.getString("storepassword", ""));
            radioButton.setChecked(true);
        }

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.i("LOGCAT", "help is clicked");
                Intent intent = new Intent(LoginActivity.this,WebViewActivity.class);
                startActivity(intent);
                Toast.makeText(LoginActivity.this,"hello",Toast.LENGTH_SHORT).show();
            }
        };
        ss.setSpan(clickableSpan,17,22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        needHelp.setText(ss);
        needHelp.setMovementMethod(LinkMovementMethod.getInstance());

        //onclick for login should bring us to the homepage after validation
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginstudentid = email.getText().toString().trim();
                String loginpassword = password.getText().toString().trim();

                if (TextUtils.isEmpty(loginstudentid)) {
                    Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(loginpassword)) {
                    Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (radioButton.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("storeusername", loginstudentid);
                    loginPrefsEditor.putString("storepassword", loginpassword);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }

                    auth.signInWithEmailAndPassword(loginstudentid, loginpassword)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        user = auth.getCurrentUser();
                                        userid = user.getUid();
                                        dataHolder.addUserID(userid);
                                        finish();
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Login is unsuccessful",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


            }
        });
    }

}


