package com.BattleOfWits;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private String email, password;
    private EditText emailSignIn, passSignIn;
    private Button signIn;
    private FirebaseAuth AuthSignIn;
    private FirebaseAuth.AuthStateListener authSignInListener;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailSignIn = findViewById(R.id.EmailSignIn);
        passSignIn = findViewById(R.id.PassSignIn);
        AuthSignIn = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.SingIn);
        authSignInListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = AuthSignIn.getCurrentUser();
                if (user != null) {
                    Toast.makeText(LoginActivity.this, "Login Successful",
                            Toast.LENGTH_SHORT).show();
                    Intent game  = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(game);
                } else {
                    Toast.makeText(LoginActivity.this, "Please Login",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailSignIn.getText().toString();
                password = passSignIn.getText().toString();
                if (email.isEmpty() && !password.isEmpty()) {
                    emailSignIn.setError("Please Enter Your Email Address");
                    emailSignIn.requestFocus();
                } else if (password.isEmpty() && !email.isEmpty()) {
                    passSignIn.setError("Please Enter Your Password");
                    passSignIn.requestFocus();
                } else if (password.isEmpty() && email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Enter Your Email and Password",
                            Toast.LENGTH_SHORT).show();
                } else if (!(password.isEmpty() && email.isEmpty())) {
                    AuthSignIn.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                            } else if (task.isSuccessful()) {
                                Intent game = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(game);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error, Please Try Again Later",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
