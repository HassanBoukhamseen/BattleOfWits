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
    private Button SignUp;
    private EditText Email, Password;
    private TextView  AccountExists;
    private String email, password;
    private FirebaseAuth auth;
    List<String> emails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        SignUp = findViewById(R.id.SignUp);
        auth = FirebaseAuth.getInstance();
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = Email.getText().toString();
                password = Password.getText().toString();
                if (email.isEmpty() && !password.isEmpty()) {
                    Email.setError("Please Enter Your Email Address");
                    Email.requestFocus();
                } else if (password.isEmpty() && !email.isEmpty()) {
                    Password.setError("Please Enter Your Password");
                    Password.requestFocus();
                } else if (password.isEmpty() && email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter Your Email and Password",
                            Toast.LENGTH_SHORT).show();
                } else if (!(password.isEmpty() && email.isEmpty())) {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Signup Unsuccessful, Please Try Again",
                                        Toast.LENGTH_SHORT).show();
                            } else if (task.isSuccessful()) {
                                emails.add(email);
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
        AccountExists = findViewById(R.id.AccountVrif);
        AccountExists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(Login);
            }
        });
    }
}
