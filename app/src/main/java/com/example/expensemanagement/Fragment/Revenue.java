package com.example.expensemanagement.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.expensemanagement.Actititiy.SignIn;
import com.example.expensemanagement.Adapter.AdapterItem;
import com.example.expensemanagement.Entity.Expenses;
import com.example.expensemanagement.Interface.MyDate;
import com.example.expensemanagement.Model.ExpensesManager;
import com.example.expensemanagement.R;
import com.example.expensemanagement.Interface.SetupProperty;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Revenue extends Fragment implements SetupProperty {
    View view;
    ExpensesManager expensesManager;
    TextView edt_amount_revenue,edt_des_revenue;
    Button btnAdd, btnDelete, btnEdit;
    ListView list_revenue;
    SearchView svRevenue;
    ImageButton btnDate;
    SignIn signIn;
    private static int id;
    String userName;
    MyDate myDate = new MyDate(0,0,0);

    public int getDeleteId(){
        return id;
    }
    public void setDeleteId(int idDelete){
        id = idDelete;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_revenua, container, false);
        Manual();
        Event();
        return view;
    }

    @Override
    public void Manual() {
        edt_amount_revenue = (EditText) view.findViewById(R.id.edt_amount_revenue);
        edt_des_revenue = (EditText) view.findViewById(R.id.edt_des_revenue);
        expensesManager = new ExpensesManager(getContext());
        btnAdd = (Button) view.findViewById(R.id.btn_add_revenue);
        btnEdit = (Button) view.findViewById(R.id.btn_edit_revenue);
        btnDelete = (Button) view.findViewById(R.id.btn_delete_revenue);
        list_revenue =(ListView) view.findViewById(R.id.list_revenue);
        svRevenue = (SearchView) view.findViewById(R.id.sv_search_revenue);
        btnDate = (ImageButton) view.findViewById(R.id.ibtn_date);
        svRevenue.clearFocus();
        signIn = new SignIn();
        userName = signIn.getSaveUserName();
        loadItem();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadItem();
    }

    @Override
    public void Event() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!EDTNotEmpty())
                {
                    Toast.makeText(getContext(), "Don't enough info", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean check = createRevenue();
                if (check){
                    Toast.makeText(getContext(), "Add Ok", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
                loadItem();
                TextEmpty();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
                loadItem();
                TextEmpty();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = deleteRevenue();
                if(check ){
                    Toast.makeText(getContext(), "Delete successful", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Delete error", Toast.LENGTH_SHORT).show();
                }
                loadItem();
                TextEmpty();
            }
        });
        list_revenue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), getListExpenses().get(i).getId() + "",Toast.LENGTH_SHORT).show();
                edt_amount_revenue.setText(getListExpenses().get(i).getAmount() + "");
                edt_des_revenue.setText(getListExpenses().get(i).getDescription() + "");
                setDeleteId(getListExpenses().get(i).getId());

            }
        });
        svRevenue.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Expenses> listFind = expensesManager.listExpensesFindThu(userName,newText);
                AdapterItem adapterItem = new AdapterItem(requireContext(), listFind);
                list_revenue.setAdapter(adapterItem);
                return false;
            }
        });
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetupDate();

            }
        });

    }

    public boolean createRevenue(){
        Expenses expenses = new Expenses();
        expenses.setAmount(Integer.parseInt(edt_amount_revenue.getText().toString()));
        expenses.setDescription(edt_des_revenue.getText().toString());
        expenses.setCategory("Thu");
        if(myDate.getYear() == 0)
            expenses.setDate(getDateNow());
        else
            expenses.setDate(myDate.GetDateAll());
        expenses.setUserName(signIn.getSaveUserName());
        return expensesManager.addNew(expenses);
    }
    public boolean deleteRevenue(){
        return expensesManager.deleteExpenses(getDeleteId());
    }
    public void update(){
        expensesManager.updateExpense(getDeleteId(),Integer.parseInt(edt_amount_revenue.getText().toString()),edt_des_revenue.getText().toString());
    }

    private String getDateNow() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Toast.makeText(getContext(), date.toString(), Toast.LENGTH_LONG).show();
        return dateFormat.format(date);
    }
    // load item
    public ArrayList<Expenses> getListExpenses(){
        ArrayList<Expenses> expenses = expensesManager.getAllThu(userName);
        return expenses;
    }

    public void loadItem() {
        if (getContext() == null) {
            Toast.makeText(getContext(), "GetContext null", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<Expenses> expensesList = getListExpenses();
        if (expensesList == null) {
            Toast.makeText(getContext(), "list null", Toast.LENGTH_SHORT).show();
            expensesList = new ArrayList<>(); // Initialize to avoid null pointer exception
        }

        if (list_revenue == null) {
            Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
            return;
        }

        AdapterItem adapterItem = new AdapterItem(requireContext(), expensesList);
        list_revenue.setAdapter(adapterItem);
    }
    private void SetupDate() {
        // Lấy ngày hiện tại từ Calendar
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // Lưu ý: Tháng bắt đầu từ 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        myDate.setYear(year);
        myDate.setMonth(month);
        myDate.setDate(day);
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                myDate.setYear(i);
                myDate.setMonth(i1+1);
                myDate.setDate(i2);
                Toast.makeText(getContext(), String.format("Select: %d/%d/%d",myDate.getDate(),myDate.getMonth(),myDate.getYear())
                        , Toast.LENGTH_LONG).show();
            }
        },year,month,day);
        dialog.show();


    }
    private void TextEmpty()
    {
        edt_des_revenue.setText("");
        edt_amount_revenue.setText("");
        myDate.setEmpty();
    }
    private boolean EDTNotEmpty()
    {
        return !(edt_des_revenue.getText().toString().isEmpty() || edt_amount_revenue.getText().toString().isEmpty());
    }

}