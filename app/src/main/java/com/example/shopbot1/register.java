package com.example.shopbot1;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbot1.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorResolver;
import com.google.firebase.auth.UserProfileChangeRequest;

public class register extends AppCompatActivity {


    Button submit;
    EditText name,phoneno,email,pass1,pass2;
    TextView signin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        submit=(Button)findViewById(R.id.btnSignUp);
        name=(EditText)findViewById(R.id.atvUsernameReg);
        email=(EditText)findViewById(R.id.atvEmailReg);
        phoneno=(EditText)findViewById(R.id.atvPhoneReg);
        pass1=(EditText)findViewById(R.id.atvPasswordReg);
        pass2=(EditText)findViewById(R.id.atvPasswordReg1);
        firebaseAuth=FirebaseAuth.getInstance();
//        if(firebaseAuth.getCurrentUser()!=null){
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            Toast.makeText(register.this,"Login successful!",Toast.LENGTH_SHORT).show();
//            startActivity(intent);
//            finish();
//        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                login.Credentials register=new login.Credentials();
                if(isvalid(name.getText().toString(),email.getText().toString(),phoneno.getText().toString(),pass1.getText().toString(),pass2.getText().toString())){
//                    register.name = name.getText().toString();
//                    register.email = email.getText().toString();
//                    register.phoneno = phoneno.getText().toString();
//                    register.password = pass1.getText().toString();
//                    login.arr.add(register);
//                    Toast.makeText(register.this,"Validation successful.",Toast.LENGTH_SHORT).show();
                    String em=email.getText().toString().trim();
                    String pass=pass1.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name.getText().toString()).build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("Firebase", "User profile updated.");
                                                }
                                            }
                                        });
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                Toast.makeText(register.this,"Registration successful!",Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }else{
                                Toast.makeText(register.this,"Registration failed.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });
        signin=(TextView) findViewById(R.id.tvSignIn);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(register.this, login.class));
            }
        });
    }
    public boolean isvalid(String n,String e, String p,String p1, String p2) {
        if (name.getText().toString().equals("") || email.getText().toString().equals("") || phoneno.getText().toString().equals("") || pass1.getText().toString().equals("")
                || pass2.getText().toString().equals("")) {
            Toast.makeText(register.this, "Please enter all the fields.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!name.getText().toString().matches("[A-Z][a-z]*")) {
            Toast.makeText(register.this, "Please enter a valid name.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!email.getText().toString().matches("^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$")) {
            Toast.makeText(register.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!phoneno.getText().toString().matches("^[7-9][0-9]{9}$")) {
            Toast.makeText(register.this, "Please enter a valid Phone number.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!pass1.getText().toString().equals(pass2.getText().toString())) {
            Toast.makeText(register.this, "Passwords don't match.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
    }
}

