package com.example.expensemanagement.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.expensemanagement.Actititiy.MainActivity;
import com.example.expensemanagement.Actititiy.SignIn;
import com.example.expensemanagement.R;
import com.example.expensemanagement.Interface.SetupProperty;

public class Profile extends Fragment implements SetupProperty {
    View view;
    TextView txtUserNameAbove,txtEmailAbove,txtUserNameBelow,txtEmailBelow,txtPasswordBelow;
    Button btnProfile;
    SignIn signIn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Manual();
        Event();
        return view;
    }

    @Override
    public void Manual() {
        txtUserNameAbove =(TextView) view.findViewById(R.id.txt_name_above);
        txtEmailAbove =(TextView) view.findViewById(R.id.txt_email_above);
        txtUserNameBelow =(TextView) view.findViewById(R.id.txt_name_below);
        txtEmailBelow =(TextView) view.findViewById(R.id.txt_email_below);
        txtPasswordBelow =(TextView) view.findViewById(R.id.txt_password_below);
        btnProfile =(Button) view.findViewById(R.id.btn_edt_sign_out);
        signIn = new SignIn();
        txtUserNameAbove.setText(signIn.getSaveUserName());
        txtEmailAbove.setText(signIn.getSaveEmail());
        txtUserNameBelow.setText(signIn.getSaveUserName());
        txtEmailBelow.setText(signIn.getSaveEmail());
    }

    @Override
    public void Event() {
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),SignIn.class);
                startActivity(intent);
            }
        });
    }

}