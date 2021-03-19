package com.example.womensskill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {
EditText email , password;
Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        login = findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("admin@gmail.com") && password.getText().toString().equals("admin")){
                    startActivity(new Intent(AdminLogin.this, AdminMain.class));
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "Check email and password!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}