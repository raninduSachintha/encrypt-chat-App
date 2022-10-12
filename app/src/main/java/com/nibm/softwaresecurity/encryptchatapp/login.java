package com.nibm.softwaresecurity.encryptchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);

        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (username.getText().toString().equals("") || password.getText().toString().equals(""))
                    Toast.makeText(login.this, "Please Enter User credentials..!", Toast.LENGTH_SHORT).show();
                else {
// TODO --> create DB to users after encryption part
                    if (username.getText().toString().equals("sapna") && password.getText().toString().equals("123")) {
                        Toast.makeText(login.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getApplicationContext(), home.class);
                        i.putExtra("user", username.getText().toString());
                        startActivity(i);

                    }
                    else if (username.getText().toString().equals("nibm") && password.getText().toString().equals("123")) {
                        Toast.makeText(login.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getApplicationContext(), home.class);
                        i.putExtra("user", username.getText().toString());
                        startActivity(i);

                    }
                    else if (username.getText().toString().equals("test") && password.getText().toString().equals("123")) {
                        Toast.makeText(login.this, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getApplicationContext(), home.class);
                        i.putExtra("user", username.getText().toString());
                        startActivity(i);

                    }

                    else
                        Toast.makeText(login.this, "LOGIN UNSUCCESSFUL", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}