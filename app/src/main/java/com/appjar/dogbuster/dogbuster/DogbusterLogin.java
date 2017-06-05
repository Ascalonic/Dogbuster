package com.appjar.dogbuster.dogbuster;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DogbusterLogin extends AppCompatActivity{

    Button btnLogin; TextView textEmail,textPass;
    TextView labForgotPwd; ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogbuster_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), DogbusterRegister.class);
                startActivity(intent);
            }
        });

        textEmail=(TextView)findViewById(R.id.txtEmail);
        textPass=(TextView)findViewById(R.id.txtPassword);

        spinner=(ProgressBar)findViewById(R.id.progressForgot);
        spinner.setVisibility(View.GONE);

        labForgotPwd=(TextView)findViewById(R.id.txtForgotPwd);

        btnLogin=(Button)findViewById(R.id.cmdLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                btnLogin.setEnabled(false);

                String res="";
                ServerInterface SI=new ServerInterface(getBaseContext(), new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {

                        if(output.contains("Success"))
                        {
                            Toast.makeText(getBaseContext(), "Login Successful!",
                                    Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getBaseContext(), DogbusterCentral.class);
                            startActivity(intent);

                            finish();

                        }
                        else
                        {
                            Toast.makeText(getBaseContext(), "Invalid Credentials! Please try again...",
                                    Toast.LENGTH_LONG).show();

                            btnLogin.setEnabled(true);
                        }

                    }
                },1);

                String email=textEmail.getText().toString(); String pass=(String)textPass.getText().toString();
                SI.execute(email,pass);
            }
        });

        labForgotPwd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                labForgotPwd.setEnabled(false);

                String res="";
                ServerInterface SI=new ServerInterface(getBaseContext(), new AsyncResponse() {
                    @Override
                    public void processFinish(String output) {

                        spinner.setVisibility(View.GONE);

                        Log.d(output,"TEST REG");

                        if(output.contains("Sent"))
                        {
                            Intent i=new Intent(getBaseContext(),ForgotPassword.class);
                            startActivity(i);
                        }else
                        {
                            Toast.makeText(getBaseContext(), "An Error Occured! Please try again...",
                                    Toast.LENGTH_LONG).show();
                        }

                        finish();

                    }
                },6);

                String uname=textEmail.getText().toString();

                if(uname.isEmpty())
                {
                    Toast.makeText(getBaseContext(), "Please fill out the mail id inorder to continue!",
                            Toast.LENGTH_LONG).show();

                    labForgotPwd.setEnabled(true);
                }
                else {
                    spinner.setVisibility(View.VISIBLE);
                    SI.execute(uname);
                }


            }
        });
    }

}
