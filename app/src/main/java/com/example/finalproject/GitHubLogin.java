package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import java.util.ArrayList;
import java.util.List;

public class GitHubLogin extends AppCompatActivity {

    EditText email;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button login;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git_hub_login);
        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String emailInput = email.getText().toString();
            if(!emailInput.matches(emailPattern)){
                Toast.makeText(GitHubLogin.this, "Invalid email", Toast.LENGTH_SHORT).show();
            }else {
                OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
                // Target specific email with login hint.
                provider.addCustomParameter("login", emailInput);

                List<String> scopes =
                        new ArrayList<String>() {
                            {
                                add("user:email");
                            }
                        };
                provider.setScopes(scopes);

                Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
                if (pendingResultTask != null) {
                    // There's something already here! Finish the sign-in for your user.
                    pendingResultTask
                            .addOnSuccessListener(
                                    new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            // User is signed in.
                                            // IdP data available in
                                            // authResult.getAdditionalUserInfo().getProfile().
                                            // The OAuth access token can also be retrieved:
                                            // authResult.getCredential().getAccessToken().
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(GitHubLogin.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            Log.e("AAAAAAAAA",e.getMessage());
                                        }
                                    });
                } else {
                    mAuth
                            .startActivityForSignInWithProvider(/* activity= */ GitHubLogin.this, provider.build())
                            .addOnSuccessListener(
                                    new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            startActivity(new Intent(v.getContext(),  MainActivity.class));
                                        }
                                    })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(GitHubLogin.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            Log.e("AAAAAAAAA",e.getMessage());
                                        }
                                    });
                }
            }

            }
        });

    }
}