package com.example.expensemanagement.Actititiy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanagement.Encoder.EncryptionUtils;
import com.example.expensemanagement.Encoder.Function;
import com.example.expensemanagement.Model.UserManager;
import com.example.expensemanagement.R;
import com.example.expensemanagement.Interface.SetupProperty;

import javax.crypto.SecretKey;

public class ForgotPassword extends AppCompatActivity implements SetupProperty {
    EditText edtName, edtEmail, edtNewPassword;
    Button btnSubmit;
    TextView txtToSignIn;
    UserManager userManager;
    private SecretKey existingKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        Manual();
        Event();

    }

    public void Manual() {
        edtName = (EditText) findViewById(R.id.edt_name_forgot);
        edtEmail = (EditText) findViewById(R.id.edt_email_forgot);
        edtNewPassword = (EditText) findViewById(R.id.edt_new_password);
        btnSubmit =(Button) findViewById(R.id.btn_submit);
        txtToSignIn =(TextView) findViewById(R.id.txt_to_sign_in);
        userManager = new UserManager(ForgotPassword.this);
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
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmail() == false)
                        return;
                try {
                    String password = edtNewPassword.getText().toString().trim();
                    String encryptedPassword = EncryptionUtils.encrypt(password, existingKey);

                    boolean check = userManager.updateUser(edtName.getText().toString(),edtEmail.getText().toString(),encryptedPassword);

                    if(check){
                        Intent intent = new Intent(ForgotPassword.this, SignIn.class);
                        startActivity(intent);
                        Toast.makeText(ForgotPassword.this,"Set New Password Successfully",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ForgotPassword.this,"Forgot password",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        txtToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPassword.this, SignIn.class);
                startActivity(intent);
                Toast.makeText(ForgotPassword.this,"Sign up",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(ForgotPassword.this, "Email is match", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(ForgotPassword.this, "No match", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}