package com.example.expensemanagement.Actititiy;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.expensemanagement.Adapter.ViewpagerAdapter;
import com.example.expensemanagement.R;
import com.example.expensemanagement.Interface.SetupProperty;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements SetupProperty {
    private TextView title;
    public ViewPager2 view;
    private BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Manual();
        Event();
    }

    @Override
    public void Manual() {
        title =(TextView) findViewById(R.id.txt_tb_title);
        view = (ViewPager2) findViewById(R.id.vp_group_view);
        bnv = (BottomNavigationView) findViewById(R.id.btv_model);
        ViewpagerAdapter adapter= new ViewpagerAdapter(MainActivity.this);
        view.setAdapter(adapter);
    }

    @Override
    public void Event() {
        view.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position)
                {
                    case 0:
                        bnv.getMenu().findItem(R.id.home).setChecked(true);
                        title.setText("Home");
                        break;
                    case 1:
                        bnv.getMenu().findItem(R.id.revenua).setChecked(true);
                        title.setText("Revenue");
                        break;
                    case 2:
                        bnv.getMenu().findItem(R.id.expenditure).setChecked(true);
                        title.setText("Expenditure");
                        break;
                    case 3:
                        bnv.getMenu().findItem(R.id.statistic).setChecked(true);
                        title.setText("Statistic");
                        break;
                    case 4:
                        bnv.getMenu().findItem(R.id.profile).setChecked(true);
                        title.setText("Profile");
                        break;
                    default:
                        bnv.getMenu().findItem(R.id.home).setChecked(true);
                        title.setText("home");
                        break;
                }

            }
        });
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home)
                    view.setCurrentItem(0);
                else if(item.getItemId() == R.id.revenua)
                    view.setCurrentItem(1);
                else if(item.getItemId() == R.id.expenditure)
                    view.setCurrentItem(2);
                else if(item.getItemId() == R.id.statistic)
                    view.setCurrentItem(3);
                else if(item.getItemId() == R.id.profile)
                    view.setCurrentItem(4);
                return true;
            }
        });
    }
}