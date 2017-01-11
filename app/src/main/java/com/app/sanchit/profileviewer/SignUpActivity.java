package com.app.sanchit.profileviewer;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.jar.Manifest;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if((ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE))== PackageManager.PERMISSION_GRANTED){
            ImageView image = (ImageView)findViewById(R.id.userImage);
            image.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (!(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))) {
                        Log.d("Media", "No");
                        Snackbar.make(findViewById(R.id.loggedIn), "Storage permission not granted", Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(i, 1);
                    }
                }
            });
        }
        else {
            Log.d("Media", "No");
            ImageView image = (ImageView)findViewById(R.id.userImage);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Snackbar.make(arg0, "Storage permission not granted", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
        final TextView Name = (TextView) findViewById(R.id.textName);
        final TextView Email = (TextView) findViewById(R.id.textEmail);
        final TextView Password = (TextView) findViewById(R.id.textPassword);
        final TextView PasswordRepeat = (TextView) findViewById(R.id.textPasswordRepeat);
        final TextView Role = (TextView) findViewById(R.id.textRole);
        Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Name.getText().length()==0)
                    Name.setError("Name cannot be empty");
                else
                    Name.setError(null);
            }
        });
        Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Password.getText().length()==0)
                    Password.setError("Password cannot be empty");
                else if(!Users.isValidPassword(Password.getText().toString()))
                    Password.setError("Password must have at least one upper or lower case letter one digit and must be 8 characters or more");
                else
                    Password.setError(null);
                if(!Password.getText().toString().equals(PasswordRepeat.getText().toString()))
                    PasswordRepeat.setError("Passwords do not match");
                else
                    PasswordRepeat.setError(null);
            }
        });
        PasswordRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Password.getText().toString().equals(PasswordRepeat.getText().toString()))
                    PasswordRepeat.setError("Passwords do not match");
                else
                    PasswordRepeat.setError(null);
            }
        });
        Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Email.getText().length()==0)
                    Email.setError("Email cannot be empty");
                else if(!Users.isValidEmail(Email.getText().toString()))
                    Email.setError("Enter a valid email address");
                else
                    Email.setError(null);
            }
        });
        Role.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(Role.getText().length()==0)
                    Role.setError("Role cannot be empty");
                else
                    Role.setError(null);
            }
        });

    }
    public void userAdd(View view){
        ImageView img = (ImageView)findViewById(R.id.userImage);
        byte[] Image = Users.toByteArray(((BitmapDrawable)img.getDrawable()).getBitmap());
        final TextView Name = (TextView) findViewById(R.id.textName);
        final TextView Email = (TextView) findViewById(R.id.textEmail);
        final TextView Password = (TextView) findViewById(R.id.textPassword);
        final TextView Role = (TextView) findViewById(R.id.textRole);
        final TextView PasswordRepeat = (TextView) findViewById(R.id.textPasswordRepeat);
        Name.setText(Name.getText().toString());
        Email.setText(Email.getText().toString());
        Password.setText(Password.getText().toString());
        PasswordRepeat.setText(PasswordRepeat.getText().toString());
        Role.setText(Role.getText().toString());
        if(Name.getError()!=null||Email.getError()!=null||Password.getError()!=null||PasswordRepeat.getError()!=null||Role.getError()!=null)
            return;
        String email = Email.getText().toString();
        DatabaseCreate myDB = new DatabaseCreate(this);
        ArrayList<String> mail = myDB.getEmail();
        if(mail.contains(email)){
            Email.setError("Email already registered");
            return;
        }
        else{
            Email.setError(null);
        }
        Users user = new Users(Name.getText().toString(),Email.getText().toString(),Password.getText().toString(),Role.getText().toString(),Image);
        myDB.addUser(user);
        Toast.makeText(this,"Successfully registered",Toast.LENGTH_SHORT).show();
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(Toast.LENGTH_SHORT);
                    Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.userImage);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }

}
