package com.example.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Login extends AppCompatActivity {
    Button login;
    Button signup;
    EditText username;
    EditText password;
    DBHelper DB;
    Button google;
    Button github;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DB = new DBHelper(this);
        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);
        google = (Button) findViewById(R.id.google);
        github = (Button) findViewById(R.id.github);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.getUser(username.getText().toString());
                if(res.getCount() == 0) {
                    Toast.makeText(Login.this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
                }else {
                    res.moveToNext();
                    if (!res.getString(2).equals(password.getText().toString())){
                        Toast.makeText(Login.this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(v.getContext(),  MainActivity.class));
                    }
                }

            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = gsc.getSignInIntent();
                startActivityForResult(intent,1000);

            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),  GitHubLogin.class));
            }
        });



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),  Signup.class));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                startActivity(new Intent(getApplicationContext(),  MainActivity.class));
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something Went wrong", Toast.LENGTH_SHORT).show();
            }

        }
    }
}