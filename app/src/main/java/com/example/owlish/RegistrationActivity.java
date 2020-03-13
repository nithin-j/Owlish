package com.example.owlish;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {


    TextInputEditText etName, etUsername, etPassword, etConfirmPassword;
    MaterialButton btnRegister, btnCancel, btnLoadLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        initialize();
    }

    private void initialize() {

        etName = findViewById(R.id.etRegName);
        etUsername = findViewById(R.id.etRegUsername);
        etPassword = findViewById(R.id.etRegPassword);
        etConfirmPassword = findViewById(R.id.etRegConfPassword);

        btnRegister = findViewById(R.id.button_register);
        btnCancel = findViewById(R.id.button_cancel);
        btnLoadLogin = findViewById(R.id.button_load_login);

        btnRegister.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnLoadLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_cancel:
                clearScreen();
                break;
            case R.id.button_load_login:
                loadLogin();
                break;
            case R.id.button_register:
                register();
                break;
        }
    }

    private void register() {
        String name, email, password, confirmPassword;

        name = etName.getText().toString();
        email = etUsername.getText().toString();
        password = etPassword.getText().toString();
        confirmPassword = etConfirmPassword.getText().toString();

        validate(name,email);
        if (password.equals(confirmPassword)){
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(RegistrationActivity.this, "Registration unsuccessful.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(this, "Password and Confirm password is not matching!", Toast.LENGTH_SHORT).show();
        }
    }

    private void validate(String name, String username) {

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Please enter your name...", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "Please enter your email id as username...", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void loadLogin() {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
    }

    private void clearScreen() {
        etName.setText(null);
        etUsername.setText(null);
        etPassword.setText(null);
        etConfirmPassword.setText(null);
    }
}
