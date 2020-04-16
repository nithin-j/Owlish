package com.example.owlish;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

public class NewEntryActivity extends AppCompatActivity {

    Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        initialize();
    }

    private void initialize() {
        mtoolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mtoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new NewEntryPagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tabLatout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Income");
                        tab.setIcon(R.drawable.tab_income);
                        break;
                    case 1:
                        tab.setText("Expense");
                        tab.setIcon(R.drawable.tab_expense);
                        break;
                    case 2:
                        tab.setText("Recurring");
                        tab.setIcon(R.drawable.tab_recurring);
                }
            }
        });
        tabLayoutMediator.attach();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menuCategory:
                startActivity(new Intent(NewEntryActivity.this, CategoryActivity.class));
                break;

            case R.id.menuReports:
                startActivity(new Intent(NewEntryActivity.this, ReportsActivity.class));
                break;

            case R.id.menuHelp:
                Toast.makeText(this, "Coming soon enough", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(NewEntryActivity.this,LoginActivity.class));
                finish();
                break;

            default:
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
