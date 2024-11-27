package com.example.expensemanagement.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.expensemanagement.Actititiy.SignIn;
import com.example.expensemanagement.Adapter.AdapterItem;
import com.example.expensemanagement.Entity.Expenses;
import com.example.expensemanagement.Model.ExpensesManager;
import com.example.expensemanagement.R;
import com.example.expensemanagement.Interface.SetupProperty;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment implements SetupProperty {
    EditText edtBalance,edtExpense,edtRevenue;
    ListView lvItem;
    View view;
    ExpensesManager expensesManager;
    int thu, chi , balance;
    SignIn signIn;
    String userName;
    SearchView svHome;
    private static boolean hello = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        Manual();
        HelloUser();
        Event();
        return view;
    }

    private void HelloUser() {
        if(hello)
        {
            Toast toast = Toast.makeText(getContext(), "Hello " + userName +" (>_<) ", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
            toast.show();
            hello = false;
        }
    }

    @Override
    public void Manual() {
        edtExpense = (EditText) view.findViewById(R.id.edt_expense);
        edtRevenue = (EditText) view.findViewById(R.id.edt_revenue);
        edtBalance = (EditText) view.findViewById(R.id.edt_balance);
        lvItem = (ListView) view.findViewById(R.id.lvItem);
        svHome =(SearchView) view.findViewById(R.id.sv_search_home);
        svHome.clearFocus();

        expensesManager = new ExpensesManager(requireContext());

        signIn = new SignIn();
        userName = signIn.getSaveUserName();
        getThu();
        getChi();
        getBalance();
        loadItem();
    }

    @Override
    public void onResume() {
        super.onResume();
        getThu();
        getChi();
        getBalance();
        loadItem();

    }

    @Override
    public void Event() {
        svHome.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(getContext(), "onQueryTextSubmit", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(getContext(), "onQueryTextChange", Toast.LENGTH_SHORT).show();
                ArrayList<Expenses> listFind = expensesManager.listExpensesFind(userName,newText);
                AdapterItem adapterItem = new AdapterItem(requireContext(), listFind);
                lvItem.setAdapter(adapterItem);
                return false;
            }
        });
    }
    public ArrayList<Expenses> getListExpenses(){
        return expensesManager.getAll(userName);
    }
    public void getChi(){



        chi = (expensesManager.getTotalAmountForChi(userName));
        edtExpense.setText(chi+"");
    }

    public void getBalance(){
        balance = thu-chi;
        edtBalance.setText(balance+"");
    }

    public void getThu(){
        //Toast.makeText(getContext(), userName, Toast.LENGTH_SHORT).show();
        thu = (expensesManager.getTotalAmountForThu(userName));
        edtRevenue.setText(thu+"");
    }

    public void loadItem() {
        if (getContext() == null) {
           // Toast.makeText(getContext(), "GetContext null", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<Expenses> expensesList = getListExpenses();
        if (expensesList == null) {
           // Toast.makeText(getContext(), "list null", Toast.LENGTH_SHORT).show();
            expensesList = new ArrayList<>(); // Initialize to avoid null pointer exception
        }

        if (lvItem == null) {
           // Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getContext(), expensesList.stream().count() +"", Toast.LENGTH_SHORT).show();
        AdapterItem adapterItem = new AdapterItem(requireContext(), expensesList);
        lvItem.setAdapter(adapterItem);
    }
}