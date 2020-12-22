package com.example.shopbot1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbot1.MainActivity;

import java.util.ArrayList;
import com.example.shopbot1.resetpassword;

public class login extends AppCompatActivity {

    /* Define the UI elements */
    private EditText eName;
    private EditText ePassword;
    private TextView eAttemptsInfo;
    private TextView reg;
    private Button eLogin;
    private TextView forgotpass;
    private int counter = 5;
    static ArrayList<Credentials> arr=new ArrayList<Credentials>();

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

        /* Describe the logic when the login button is clicked */
        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Obtain user inputs */
                userName = eName.getText().toString();
                userPassword = ePassword.getText().toString();

                /* Check if the user inputs are empty */
                if(userName.isEmpty() || userPassword.isEmpty())
                {
                    /* Display a message toast to user to enter the details */
                    Toast.makeText(login.this, "Please enter name and password!", Toast.LENGTH_LONG).show();

                }else {

                    /* Validate the user inputs */
                    isValid = validate(userName, userPassword);

                    /* Validate the user inputs */

                    /* If not valid */
                    if (!isValid) {

                        /* Decrement the counter */
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
                    /* If valid */
                    else {

                        /* Allow the user in to your app by going into the next activity */
                        startActivity(new Intent(login.this, MainActivity.class));
                    }

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

    /* Validate the credentials */
    private boolean validate(String userName, String userPassword)
    {
        /* Get the object of Credentials class */
        Credentials credentials1 = new Credentials();
        credentials1.name="admin";
        credentials1.password="123";
        credentials1.phoneno="9999999999";
        credentials1.email="admin@gmail.com";

        arr.add(credentials1);
        /* Check the credentials */
        if((credentials1.name.contains(userName))&& credentials1.password.contains(userPassword))
        {
            return true;
        }

        return false;
    }
}