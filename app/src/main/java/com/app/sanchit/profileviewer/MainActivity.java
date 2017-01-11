package com.app.sanchit.profileviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView email = (TextView) findViewById(R.id.editEmail);
        final TextView password = (TextView) findViewById(R.id.editPassword);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(email.getText().length()>0){
                    if(!Users.isValidEmail(email.getText().toString())){
                    email.setError("Enter a valid email address");
                    }
                    else
                        email.setError(null);
                }
                else
                    email.setError("Email cannot be empty");
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(password.getText().length()==0) {
                    password.setError("Password cannot be empty");
                }
                else
                    password.setError(null);
            }
        });
    }
    public void loginCheck(View Main){
        TextView Email = ((TextView)findViewById(R.id.editEmail));
        Email.setText(Email.getText().toString());
        TextView Password = ((TextView)findViewById(R.id.editPassword));
        Password.setText(Password.getText().toString());
        if((Password.getError()!=null)||(Email.getError()!=null))
            return;
        DatabaseCreate db = new DatabaseCreate(this);
        ProgressBar p = (ProgressBar)findViewById(R.id.progressBar);
        p.setVisibility(View.VISIBLE);
        Users user = (db.login(Email.getText().toString(),Password.getText().toString()));
        if(user.email!=null){
            findViewById(R.id.loggedIn).setVisibility(View.VISIBLE);
            findViewById(R.id.error).setVisibility(View.INVISIBLE);
            Intent intent = new Intent(this,LoggedInActivity.class);
            intent.putExtra("UserDetails",user);
            startActivity(intent);
        }
        else{
            findViewById(R.id.loggedIn).setVisibility(View.INVISIBLE);
            findViewById(R.id.error).setVisibility(View.VISIBLE);
        }
        p.setVisibility(View.INVISIBLE);

    }
    public void signUp(View Main){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

}
