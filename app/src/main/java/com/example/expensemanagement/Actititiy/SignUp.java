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

import com.example.expensemanagement.Encoder.*;
import com.example.expensemanagement.Entity.User;
import com.example.expensemanagement.Model.UserManager;
import com.example.expensemanagement.R;
import com.example.expensemanagement.Interface.SetupProperty;

import javax.crypto.SecretKey;

public class SignUp extends AppCompatActivity implements SetupProperty {
    TextView txtToSignIn;
    EditText edtName,edtEmail,edtPassword;
    Button btnSignUp;
    UserManager userManager;
    private SecretKey existingKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        Manual();
        Event();

    }

    @Override
    public void Manual() {
        txtToSignIn = (TextView) findViewById(R.id.txt_to_sign_in);
        edtName = (EditText) findViewById(R.id.edt_name_sign_up);
        edtEmail = (EditText) findViewById(R.id.edt_email_sign_up);
        edtPassword = (EditText) findViewById(R.id.edt_password_sign_up);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        userManager = new UserManager(SignUp.this);

        try {
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
        txtToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmail() == false)
                    return;

                try {
                    User user = new User();
                    String password = edtPassword.getText().toString().trim();
                    String encryptedPassword = EncryptionUtils.encrypt(password, existingKey);
                    user.setUserName(edtName.getText().toString());
                    user.setEmail(edtEmail.getText().toString());
                    user.setPassWord(encryptedPassword);
                    user.setBalance(0);
                    boolean check = userManager.createUser(user);
                    if (check) {
                        Intent intent = new Intent(SignUp.this, SignIn.class);
                        startActivity(intent);
                        Toast.makeText(SignUp.this, "True", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignUp.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e ){
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
            Toast.makeText(SignUp.this, "Email is match", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(SignUp.this, "No match", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}