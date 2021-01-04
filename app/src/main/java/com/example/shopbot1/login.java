package com.example.shopbot1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbot1.MainActivity;

import java.util.ArrayList;
import com.example.shopbot1.resetpassword;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    /* Define the UI elements */
    private EditText eName;
    private EditText ePassword;
    private TextView eAttemptsInfo;
    private TextView reg;
    private Button eLogin;
    private TextView forgotpass;
    private int counter = 5;
    int flag=0;
    static ArrayList<Credentials> arr=new ArrayList<Credentials>();
    FirebaseAuth firebaseAuth;
    String userName = "";
    String userPassword = "";

    /* Class to hold credentials */
    public static class Credentials
    {
        String name ;
        String password ;
        String phoneno, email;
    }


    boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Bind the XML views to Java Code Elements */
        eName = findViewById(R.id.atvEmailLog);
        ePassword = findViewById(R.id.atvPasswordLog);
        eLogin = findViewById(R.id.btnSignIn);
        eAttemptsInfo = findViewById(R.id.atvattempts);
        reg=findViewById(R.id.tvSignIn);
        forgotpass=findViewById(R.id.tvForgotPass);
        firebaseAuth= FirebaseAuth.getInstance();

        /* Describe the logic when the login button is clicked */
        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Obtain user inputs */
                userName = eName.getText().toString().trim();
                userPassword = ePassword.getText().toString();

                /* Check if the user inputs are empty */
                if(userName.isEmpty() || userPassword.isEmpty())
                {
                    /* Display a message toast to user to enter the details */
                    Toast.makeText(login.this, "Please enter name and password!", Toast.LENGTH_LONG).show();

                }else {

                    /* Validate the user inputs */
//                    isValid = validate(userName, userPassword);
                    firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Toast.makeText(login.this,"Login successful! ="+user.getEmail()+" and "+user.getEmail().toString().contains(userName),Toast.LENGTH_SHORT).show();
                                if(user.getEmail().toString().contains(userName))
                                {
                                    Toast.makeText(login.this,"flagset",Toast.LENGTH_SHORT).show();
                                    flag=1;
                                    startActivity(new Intent(login.this, MainActivity.class));
                                }
                            }else{
                                Toast.makeText(login.this,"Login failed.",Toast.LENGTH_SHORT).show();
                                counter--;

                                /* Show the attempts remaining */
                                eAttemptsInfo.setText("Attempts Remaining: " + String.valueOf(counter));

                                /* Disable the login button if there are no attempts left */
                                if (counter == 0) {
                                    eLogin.setEnabled(false);
                                    Toast.makeText(login.this, "You have used all your attempts try again later!", Toast.LENGTH_LONG).show();
                                }
                                /* Display error message */
                                else {
                                    Toast.makeText(login.this, "Incorrect credentials, please try again!", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    });
                }
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, register.class));
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, resetpassword.class));
            }
        });
    }


}