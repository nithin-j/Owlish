package com.example.owlish;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mtoolbar;
    FloatingActionButton fab_load_new_entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        mtoolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mtoolbar);

        fab_load_new_entry = findViewById(R.id.btn_load_addnew_entry);
        fab_load_new_entry.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        
        switch (item.getItemId()){
            case R.id.menuAccount:
                Toast.makeText(this, "Coming soon enough", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuCategory:
                startActivity(new Intent(MainActivity.this, CategoryActivity.class));
                break;

            case R.id.menuReports:


            case R.id.menuHelp:


            case R.id.menuLogout:
                Toast.makeText(this, "Coming soon enough", Toast.LENGTH_SHORT).show();
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_load_addnew_entry:
                loadNewEntry();
                break;
        }
    }

    private void loadNewEntry() {
        startActivity(new Intent(MainActivity.this, NewEntryActivity.class));
    }

}
