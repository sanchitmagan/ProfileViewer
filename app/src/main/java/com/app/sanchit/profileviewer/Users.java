package com.app.sanchit.profileviewer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sanchit on 7/1/17.
 */

public class Users implements Serializable{
    String name;
    String role;
    String email;
    String password;
    byte[] imageByte;
    Users(String Name, String Email, String Password, String Role, byte[] imgByte){
        name = Name;
        email = Email;
        password = Password;
        role = Role;
        if(imgByte==null) {
            imageByte = null;
        }
        else {
            imageByte = Arrays.copyOf(imgByte, imgByte.length);
        }
    }
    Users()
    {
        name = null;
        email = null;
        password = null;
        role = null;
        imageByte = null;
    }
    public static boolean isValidPassword(String password) {
        String Password_Pattern = "[A-Za-z0-9]+[0-9]+[A-Za-z0-9]+";
        Pattern pattern = Pattern.compile(Password_Pattern);
        Matcher matcher = pattern.matcher(password);
        return (password.length() > 8)&& matcher.matches();
    }
    public static boolean isValidEmail(String email) {
        String Email_Pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(Email_Pattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static byte[] toByteArray(Bitmap img){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    public static Bitmap toBitmap(byte[] image){
        if(image==null)
            return null;
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
