package com.example.owlish;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class NewEntryActivity extends AppCompatActivity implements IncomeFragment.OnFragmentInteractionListener, ExpenseFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ExpenseFragment expenseFragment;
    private IncomeFragment incomeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        toolbar = findViewById(R.id.new_entry_toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.new_entry_view_pager);
        tabLayout = findViewById(R.id.new_entry_tab_layout);

        expenseFragment = new ExpenseFragment();
        incomeFragment = new IncomeFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragment(incomeFragment,"Income");
        viewPagerAdapter.addFragment(expenseFragment, "Expense");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.tab_income);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab_expense);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        uri.getFragment();
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}
