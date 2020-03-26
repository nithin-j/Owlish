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
            default:
                return new ExpenseFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
