package com.example.expensemanagement.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.expensemanagement.Fragment.Expenditure;
import com.example.expensemanagement.Fragment.Home;
import com.example.expensemanagement.Fragment.Profile;
import com.example.expensemanagement.Fragment.Revenue;
import com.example.expensemanagement.Fragment.Statistics;

public class ViewpagerAdapter extends FragmentStateAdapter {
    public ViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position)
        {
            case 0:
                return new Home();
            case 1:
                return new Revenue();
            case 2:
                return new Expenditure();
            case 3:
                return new Statistics();
            case 4:
                return new Profile();
            default:
                return new Home();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
