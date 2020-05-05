package com.BattleOfWits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button signUp, developerAccess;
    private EditText emailTextView, passwordTextView;
    private TextView  accountExists;
    private String email, password;
    private FirebaseAuth auth;
    List<String> emails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailTextView = findViewById(R.id.Email);
        passwordTextView = findViewById(R.id.Password);
        signUp = findViewById(R.id.SignUp);
        developerAccess = findViewById(R.id.DA);
        auth = FirebaseAuth.getInstance();
        developerAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quickAccess = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(quickAccess);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailTextView.getText().toString();
                password = passwordTextView.getText().toString();
                if (email.isEmpty() && !password.isEmpty()) {
                    emailTextView.setError("Please Enter Your Email Address");
                    emailTextView.requestFocus();
                } else if (password.isEmpty() && !email.isEmpty()) {
                    passwordTextView.setError("Please Enter Your Password");
                    passwordTextView.requestFocus();
                } else if (password.isEmpty() && email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Your Email and Password",
                            Toast.LENGTH_SHORT).show();
                } else if (!(password.isEmpty() && email.isEmpty())) {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "SignUp Unsuccessful, Please Try Again",
                                        Toast.LENGTH_SHORT).show();
                            } else if (task.isSuccessful()) {
                                Intent game  = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(game);
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Error, Please Try Again Later",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        accountExists = findViewById(R.id.AccountVrif);
        accountExists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(Login);
            }
        });
    }
}
