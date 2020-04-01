package com.example.owlish;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CategoryActivity extends AppCompatActivity {

    Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initialize();
    }

    private void initialize() {
        mtoolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mtoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
            case R.id.menuAccount:
                Toast.makeText(this, "Coming soon enough", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuCategory:
                startActivity(new Intent(CategoryActivity.this, CategoryActivity.class));
                break;

            case R.id.menuReports:


            case R.id.menuHelp:


            case R.id.menuLogout:
                Toast.makeText(this, "Coming soon enough", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
