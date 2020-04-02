package com.example.owlish;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class NewEntryPagerAdapter extends FragmentStateAdapter {

    public NewEntryPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new IncomeFragment();
            case 1:
                return new ExpenseFragment();
            default:
                return new ReccurringFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
