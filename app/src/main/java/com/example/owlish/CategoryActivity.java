package com.example.owlish;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.owlish.Data.Categories;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mtoolbar;
    TextInputEditText etCategory;
    MaterialButton btnAddCatogory;
    DatabaseReference categoryReference;
    ListView lvCategories;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initialize();
        loadCategories();
    }

    private void loadCategories() {
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        lvCategories.setAdapter(arrayAdapter);

        categoryReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String categories= dataSnapshot.getValue(Categories.class).toString();
                arrayList.add(categories);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initialize() {
        mtoolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mtoolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etCategory = findViewById(R.id.etCategory);
        btnAddCatogory = findViewById(R.id.btn_add_category);

        btnAddCatogory.setOnClickListener(this);

        lvCategories = findViewById(R.id.lvCategories);

        categoryReference = FirebaseDatabase.getInstance().getReference("Categories");


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
            default:
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(myIntent, 0);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_add_category:
                addCategory();
        }

    }

    private void addCategory() {
        String name = etCategory.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = database.getReference("Categories").push().getKey();

        Categories categories = new Categories(id, name);

        //Toast.makeText(this, name +" "+id, Toast.LENGTH_SHORT).show();
        categoryReference.child(String.valueOf(id)).setValue(categories);

        etCategory.setText(null);
        arrayAdapter.notifyDataSetChanged();


    }
}
