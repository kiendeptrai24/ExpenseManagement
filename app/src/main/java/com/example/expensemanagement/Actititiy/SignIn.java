package com.example.expensemanagement.Actititiy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanagement.Encoder.*;
import com.example.expensemanagement.Model.UserManager;
import com.example.expensemanagement.R;
import com.example.expensemanagement.Interface.SetupProperty;

import javax.crypto.SecretKey;

public class SignIn extends AppCompatActivity implements SetupProperty {
    TextView txtForgotPW, txtToSignUp;
    EditText edtEmail,edtPassword;
    CheckBox cbRemember;
    Button btnSign;
    UserManager userManager;
    private SecretKey existingKey;
    private static String saveUserName;

    public String getSaveUserName(){
        return saveUserName;
    }

    public void setSaveUserName(String name){
        saveUserName = name;
    }

    public static String saveEmail;
    public String getSaveEmail(){
        return saveEmail;
    }
    public void setSaveEmail(String email){
        saveEmail = email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);

        Manual();
        Event();

    }

    @Override
    public void Manual() {
        txtForgotPW =(TextView) findViewById(R.id.to_forgot_pw);
        txtToSignUp =(TextView) findViewById(R.id.txt_to_sign_up);
        edtEmail = (EditText) findViewById(R.id.edt_email_sign_in);
        edtPassword = (EditText) findViewById(R.id.edt_password_sign_in);
        cbRemember = (CheckBox) findViewById(R.id.cb_sign_in);
        btnSign = (Button) findViewById(R.id.btn_sign_in);
        userManager = new UserManager(SignIn.this);
        try {
            // Use the same key from registration (load securely in production)
            // Lấy SecretKey từ SharedPreferences (nếu có)
            existingKey = Function.getSecretKey(this);

            if (existingKey == null) {
                // Nếu chưa có SecretKey, tạo mới
                SecretKey newKey = EncryptionUtils.generateKey();
                // Lưu SecretKey vào SharedPreferences
                Function.saveSecretKeyIfNotExists(this, newKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Event() {
        txtForgotPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
        txtToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmail() == false)
                    return;
                try {
                    String password = edtPassword.getText().toString().trim();
                    String encryptedPassword = EncryptionUtils.encrypt(password, existingKey);
                    boolean check = userManager.checkLogin(edtEmail.getText().toString(),encryptedPassword);
                    setSaveUserName(userManager.queryUser(edtEmail.getText().toString(),encryptedPassword));

                    setSaveEmail(edtEmail.getText().toString());
                    if (check){
                        Intent intent = new Intent(SignIn.this, MainActivity.class);
                        intent.putExtra("userName",getSaveUserName());
                        startActivity(intent);
                        Toast.makeText(SignIn.this,"True Information",Toast.LENGTH_SHORT).show();
                    }else {
                        txtToSignUp.setTextColor(Color.RED);
                        txtForgotPW.setTextColor(Color.RED);
                        Toast.makeText(SignIn.this,"Error PassWord or Email",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
    public boolean isValidEmail(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean checkEmail()
    {
        String email = edtEmail.getText().toString().trim();
        if (isValidEmail(email))
        {
            //Toast.makeText(SignIn.this, "Email is match", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(SignIn.this, "Not match", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}