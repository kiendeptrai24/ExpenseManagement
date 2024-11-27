package com.example.expensemanagement.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.expensemanagement.Actititiy.SignIn;
import com.example.expensemanagement.Interface.*;
import com.example.expensemanagement.Interface.MyDate;
import com.example.expensemanagement.Model.ExpensesManager;
import com.example.expensemanagement.R;
import com.example.expensemanagement.Interface.SetupProperty;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Statistics extends Fragment implements SetupProperty {
    View view;
    TextView tvTitle,txtStartDate,txtEndDate,txtStartMonth,txtEndMonth;
    PieChart pieChart;
    BarChart barChart;
    ImageButton btnStartDate,btnEndDate,btnStartMonth,btnEndMonth;
    MyDate startDate= new MyDate();
    MyDate endDate= new MyDate();
    MyDate startMonth= new MyDate();
    MyDate endMonth= new MyDate();
    ExpensesManager expensesManager;
    float saveChi, saveThu;
    SignIn signIn;
    String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_statistics, container, false);
        Manual();
        Event();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        pieChartData();
        chart();
    }

    @Override
    public void Manual() {
        signIn = new SignIn();
        userName = signIn.getSaveUserName();

        tvTitle=(TextView) view.findViewById(R.id.txt_title);
        pieChart = (PieChart) view.findViewById(R.id.pie_chart);
        barChart = (BarChart) view.findViewById(R.id.bar_chart);

        btnStartDate = (ImageButton) view.findViewById(R.id.ibtn_start_date);
        btnEndDate = (ImageButton) view.findViewById(R.id.ibtn_end_date);
        btnStartMonth =(ImageButton) view.findViewById(R.id.ibtn_start_month);
        btnEndMonth =(ImageButton) view.findViewById(R.id.ibtn_end_month);
        txtStartDate = (TextView) view.findViewById(R.id.txt_start_date);
        txtEndDate = (TextView) view.findViewById(R.id.txt_end_date);
        txtStartMonth = (TextView) view.findViewById(R.id.txt_start_month);
        txtEndMonth = (TextView) view.findViewById(R.id.txt_end_month);
        expensesManager = new ExpensesManager(getContext());
        pieChartData();

        chart();
    }

    public List<MonthlySummary> showThuChiData(){
        return expensesManager.getMonthlySummary(userName);
    }
    public void pieChartData(){
        saveThu = expensesManager.getTotalAmountForThu(userName);
        saveChi = expensesManager.getTotalAmountForChi(userName);
    }

    @Override
    public void Event() {
        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetupDate(startDate,txtStartDate);
            }
        });
        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetupDate(endDate,txtEndDate);
            }
        });
        btnStartMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetupMonth(startMonth,txtStartMonth);
                // test show data at pie

            }
        });
        btnEndMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetupMonth(endMonth,txtEndMonth);
            }
        });

    }
    private void SetupDate(MyDate myDate,TextView txt) {
        // Lấy ngày hiện tại từ Calendar
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // Lưu ý: Tháng bắt đầu từ 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                myDate.setYear(i);
                myDate.setMonth(i1+1);
                myDate.setDate(i2);
                txt.setText(String.format("%d/%d/%d",myDate.getDate(),myDate.getMonth(),myDate.getYear()));
                Toast.makeText(getContext(), String.format("Select: %d/%d/%d",myDate.getDate(),myDate.getMonth(),myDate.getYear())
                        , Toast.LENGTH_LONG).show();

            }
        },year,month,day);
        dialog.show();


    }


    public void SetupMonth(MyDate myDate,TextView txt)
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int i, int i1, int i2) {
                        myDate.setYear(i);
                        myDate.setMonth(i1+1);
                        myDate.setDate(1);
                        txt.setText(String.format("%d/%d/%d",myDate.getDate(),myDate.getMonth(),myDate.getYear()));
                        Toast.makeText(getContext(), String.format("Select: %d/%d/%d",myDate.getDate(),myDate.getMonth(),myDate.getYear())
                                , Toast.LENGTH_LONG).show();

                    }
                }, year, month, 1);

        datePickerDialog.show(); // Hiển thị dialog
    }
    public void chart()
    {


        // Initialize the PieChart


        // Data for the PieChart
        ArrayList<PieEntry> entriesPie = new ArrayList<>();
        entriesPie.add(new PieEntry(saveThu, "Revenue")); // Replace 60f with actual data
        entriesPie.add(new PieEntry(saveChi, "Expenditure")); // Replace 40f with actual data

        // Create a PieDataSet
        PieDataSet dataSet = new PieDataSet(entriesPie, "Ratio of Revenue and Expenditure");
        dataSet.setColors(Color.GREEN, Color.RED); // Example colors for "Thu" and "Chi"
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(14f);

        // Create PieData
        PieData pieData = new PieData(dataSet);

        // Configure the PieChart
        pieChart.setData(pieData);
        pieChart.setCenterText("Revenue and Expenditure");
        pieChart.setCenterTextSize(18f);
        pieChart.setEntryLabelTextSize(14f);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false); // Disable the description

        // Refresh the chart
        pieChart.invalidate();


        ArrayList<Float> totalRevenue = new ArrayList<>();
        ArrayList<Float> totalExpend = new ArrayList<>();
        List <String> months = new ArrayList<>(); // fix here --------
        // month thu chi
        for(MonthlySummary s : showThuChiData()){
            months.add(s.getMonth());
            totalRevenue.add(s.getTotalIncome());
            totalExpend.add(s.getTotalExpense());

        }
        //-------------------------
//        totalRevenue.add(1000f);//1
//        totalRevenue.add(4000f);//2
//        totalRevenue.add(3400f);//3
//        totalRevenue.add(6300f);//4
//        totalRevenue.add(6100f);//5
//        totalRevenue.add(1200f);//6
//        totalRevenue.add(3800f);//7
//        totalRevenue.add(7200f);//8
//        totalRevenue.add(6500f);//9
//        totalRevenue.add(3200f);//10
//        totalRevenue.add(5600f);//11
//        totalRevenue.add(5900f);//12
//
//        totalExpend.add(1f);
//        totalExpend.add(1f);
//        totalExpend.add(1f);
//        totalExpend.add(1f);
//        totalExpend.add(6100f);
//        totalExpend.add(1200f);
//        totalExpend.add(3800f);
//        totalExpend.add(7200f);
//        totalExpend.add(6500f);
//        totalExpend.add(3200f);
//        totalExpend.add(5600f);
//        totalExpend.add(5900f);

        ArrayList<BarEntry> colunmChart = new ArrayList<>();
        for (int i = 0;i<totalRevenue.size();i++){
            colunmChart.add(new BarEntry(i, new float[]{(float) totalRevenue.get(i), (float) totalExpend.get(i)}));
        }

        BarDataSet colunmBarDataSet = new BarDataSet(colunmChart,"Monthly Totals (Revenue/Expend)");
        colunmBarDataSet.setColors(Color.GREEN, Color.RED);
        colunmBarDataSet.setStackLabels(new String[]{"Revenue","Expend"});

        BarData culunmBarData = new BarData(colunmBarDataSet);
        culunmBarData.setBarWidth(.7f);



        // Configure the BarChart
        barChart.setData(culunmBarData);
        barChart.setFitBars(true);
        barChart.getDescription().setEnabled(false); // Disable chart description
        barChart.animateY(1000); // Animate Y-axis

        // Configure the X-Axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months)); // Set month labels
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // Ensure intervals match entries
        xAxis.setGranularityEnabled(true);

        // Refresh the chart
        barChart.invalidate();
    }
}