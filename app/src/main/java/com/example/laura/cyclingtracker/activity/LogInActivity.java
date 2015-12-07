package com.example.laura.cyclingtracker.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laura.cyclingtracker.R;
import com.example.laura.cyclingtracker.data.Profile;
import com.example.laura.cyclingtracker.helper.GlobalState;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LogInActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    GlobalState gs;

    @Bind(R.id.input_email)
    EditText _usernameText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        gs = (GlobalState) getApplication();

        if(ParseUser.getCurrentUser()!=null){


            Intent mainIntent = new Intent(LogInActivity.this, MainActivity.class);
            Toast.makeText(getApplicationContext(), "Logging In...", Toast.LENGTH_SHORT).show();
            startActivity(mainIntent);


        }

        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();



    }


    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LogInActivity.this);;
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (gs.connectedToInternet(this) == false) {
            Toast.makeText(this, "Check your internet connection and try again", Toast.LENGTH_LONG).show();
        } else {

            Profile.logInInBackground(username,password, new LogInCallback() {

                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {

                        new android.os.Handler().

                                postDelayed(
                                        new Runnable() {
                                            public void run() {
                                                // On complete call either onLoginSuccess or onLoginFailed
                                                onLoginSuccess();
                                                // onLoginFailed();
                                                progressDialog.dismiss();
                                            }
                                        }

                                        , 3000);


                    }else{
                        e.printStackTrace();
                        onLoginFailed();
                        progressDialog.dismiss();

                    }
                }

            });


        }
    }

        public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String email = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();


        if (password.matches("") || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}

