package com.example.laura.cyclingtracker.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laura.cyclingtracker.R;
import com.example.laura.cyclingtracker.data.Profile;
import com.example.laura.cyclingtracker.helper.GlobalState;
import com.parse.ParseException;
import com.parse.SignUpCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by laura on 16/11/15.
 */
public class SignUpActivity extends AppCompatActivity{

    private static final String TAG = "SignupActivity";

    GlobalState gs;
    private Profile user;

    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;


    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        gs = (GlobalState) getApplication();
        setContentView(R.layout.signup_activity);
        ButterKnife.bind(this);

        user = new Profile();


        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }


        });

        _loginLink.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void signUp(){


        if(!validate()){

            onSignupFailed();
            return;
        }

          registerUser();


    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));

        setResult(RESULT_OK, null);
        finish();
    }


    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


    public void registerUser(){


        if(gs.connectedToInternet(SignUpActivity.this)){

            Toast.makeText(SignUpActivity.this,"Check your internet connection and try again",Toast.LENGTH_LONG).show();
        }else{

            user.setUsername(_nameText.getText().toString());
            user.setEmail(_emailText.getText().toString());
            user.setPassword(_passwordText.getText().toString());

            user.pinInBackground();
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(!(e==null)){

                        onSignupFailed();

                    }else{


                        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Creating Account...");
                        progressDialog.show();


                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        onSignupSuccess();
                                        progressDialog.dismiss();
                                    }
                                }, 3000);



                    }



                }
            });


        }

    }
}
