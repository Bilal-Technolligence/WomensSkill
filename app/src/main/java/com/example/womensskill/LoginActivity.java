package com.example.womensskill;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button btn_Login;
    TextView txtSignUp,forgetPassword;
    EditText email , password;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_Login =(Button) findViewById(R.id.btn_login);
        txtSignUp = (TextView) findViewById(R.id.signUp);
        forgetPassword = (TextView) findViewById(R.id.forget);
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Logging In..... ");
        final FirbaseAuthenticationClass firbaseAuthenticationClass=new FirbaseAuthenticationClass();
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgetPassword.class));
            }
        });
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String EMAIL = email.getText().toString().trim();
                String PASSWORD = password.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()){
                    email.setError("Invalid email");
                    email.setFocusable(true);
                }
//                else if(EMAIL.equals("admin@gmail.com") && PASSWORD.equals("admin")){
//                    startActivity(new Intent(getApplicationContext(),AdminMain.class));
//                    finish();
//                }
                else {
                    progressDialog.show();
                    firbaseAuthenticationClass.LoginUser(EMAIL,PASSWORD, LoginActivity.this, progressDialog);

                }
            }
        });
    }
}