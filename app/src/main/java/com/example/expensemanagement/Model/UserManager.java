package com.example.expensemanagement.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.expensemanagement.Entity.User;

public class UserManager extends SQLiteOpenHelper {
    public UserManager(@Nullable Context context) {
        super(context, "Db_project1.db", null, 34);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE user (" +
                "userId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "userName TEXT, " +
                "passWord TEXT, " +
                "balance INTEGER, " +
                "email TEXT" +
                ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists user");
        onCreate(sqLiteDatabase);
    }

    // custom design
    public boolean checkLogin(String email, String passWord) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Truy vấn để lấy thông tin từ database dựa trên userName và passWord
        String sql = "SELECT * FROM user WHERE email = ? AND passWord = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{email, passWord});
        boolean isMatch = false;
        // Kiểm tra xem có dòng nào khớp với kết quả truy vấn không
        if (cursor.moveToFirst()) {
            isMatch = true;
        }
        cursor.close();
        db.close();
        return isMatch;
    }

    //  register new user
    public boolean createUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName",user.getUserName());
        values.put("passWord",user.getPassWord());
        values.put("balance",user.getBalance());
        values.put("email",user.getEmail());
        long result = db.insert("user",null,values);
        db.close();
        return result != -1;
    }

    // check userName phone if true accept user update new passWord
    public boolean updateUser(String userName, String email, String passWord){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("passWord",passWord);
        int result = db.update("user", values, "userName = ? AND email = ?", new String[]{userName,email});
        return result >0;
    }

    public String queryUser(String email, String passWord) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userName = "";
        // Corrected SQL query
        String sql = "SELECT userName FROM user WHERE email = ? AND passWord = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{email, passWord});
        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndexOrThrow("userName"));
        }
        cursor.close();
        db.close();
        return userName;
    }


}
