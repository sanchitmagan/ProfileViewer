package com.app.sanchit.profileviewer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseCreate extends SQLiteOpenHelper {
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("Create Table Users(Name text, Email text primary key, Password text, Role text, Image blob);");
        db.execSQL("Insert into Users values('Admin','Boss@Admin.com','BossIsAdmin','AdminBoss', null);");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ArrayList<Users> users = getAllUsers(db);
        db.execSQL("Drop Table IF EXISTS Users;");
        onCreate(db);
        for(Users user : users){
            if(user.email.equals("Boss@Admin.com"))
                continue;
            addUser(user);
        }
    }
    DatabaseCreate(Context context){
        super(context, "Database.db", null, 1);
    }
    public void addUser(Users user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues User = new ContentValues();
        User.put("Name",user.name);
        User.put("Email",user.email);
        User.put("Password",user.password);
        User.put("Role",user.role);
        User.put("Image",user.imageByte);
        db.insert("Users",null,User);
        db.close();
    }
    private ArrayList<Users> getAllUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList <Users> users = new ArrayList<>();
        Cursor c = db.rawQuery("Select * from Users;",null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            Users user = new Users(c.getString(c.getColumnIndex("Name")),c.getString(c.getColumnIndex("Email")),c.getString(c.getColumnIndex("Password")),c.getString(c.getColumnIndex("Role")),c.getBlob(c.getColumnIndex("Image")));
            users.add(user);
            c.moveToNext();
        }
        c.close();
        return users;
    }
    private ArrayList<Users> getAllUsers(SQLiteDatabase db){
        //SQLiteDatabase db = this.getReadableDatabase();
        ArrayList <Users> users = new ArrayList<>();
        Cursor c = db.rawQuery("Select * from Users;",null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            Users user = new Users(c.getString(c.getColumnIndex("Name")),c.getString(c.getColumnIndex("Email")),c.getString(c.getColumnIndex("Password")),c.getString(c.getColumnIndex("Role")),c.getBlob(c.getColumnIndex("Image")));
            users.add(user);
            c.moveToNext();
        }
        c.close();
        return users;
    }

    public ArrayList<String> getEmail(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList <String> Email = new ArrayList<>();
        Cursor c = db.rawQuery("Select Email from Users",null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            Email.add(c.getString(c.getColumnIndex("Email")));
            c.moveToNext();
        }
        c.close();
        return Email;
    }
    public Users login(String email, String password){
        ArrayList<Users> users = getAllUsers();
        for(Users user : users){
            if(email.equals(user.email)&&(password.equals(user.password))){
                return user;
            }
        }
        return new Users();
    }
}
