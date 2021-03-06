package com.example.owlish;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.owlish.Data.Expense;
import com.example.owlish.Data.Income;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mtoolbar;
    MaterialButton btnView;
    DatabaseReference incomeReference;
    DatabaseReference expenseReference;
    ListView lvReports;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    Spinner spinner_report_type;


    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        initialize();
        loadAllReport();
    }

    private void loadAllReport() {

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        lvReports.setAdapter(arrayAdapter);
        arrayList.clear();
        arrayAdapter.notifyDataSetChanged();

        expenseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String expenses = dataSnapshot.getValue(Expense.class).toString();
                arrayList.add(expenses);
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

        incomeReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String incomes = dataSnapshot.getValue(Income.class).toString();
                arrayList.add(incomes);
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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        btnView = findViewById(R.id.btn_view_report);
        btnView.setOnClickListener(this);

        lvReports = findViewById(R.id.lvReports);
        spinner_report_type = findViewById(R.id.spinner_report_type);

        incomeReference = FirebaseDatabase.getInstance().getReference(uid).child("Income");
        expenseReference = FirebaseDatabase.getInstance().getReference(uid).child("Expense");



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_view_report:
                viewReport();
        }
    }

    private void viewReport() {
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        lvReports.setAdapter(arrayAdapter);
        arrayList.clear();
        arrayAdapter.notifyDataSetChanged();

        String userChoice = spinner_report_type.getSelectedItem().toString();

        //Toast.makeText(this, userChoice.toLowerCase(), Toast.LENGTH_SHORT).show();
        if (userChoice.toLowerCase().equals("all")){loadAllReport();}
        else if (userChoice.toLowerCase().equals("expense")){
            expenseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String expenses = dataSnapshot.getValue(Expense.class).toString();
                    arrayList.add(expenses);
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
        else if (userChoice.toLowerCase().equals("income")){
            incomeReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String incomes = dataSnapshot.getValue(Income.class).toString();
                    arrayList.add(incomes);
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
                startActivity(new Intent(ReportsActivity.this, CategoryActivity.class));
                break;

            case R.id.menuReports:
                startActivity(new Intent(ReportsActivity.this, ReportsActivity.class));
                break;

            case R.id.menuHelp:
                Toast.makeText(this, "Coming soon enough", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ReportsActivity.this,LoginActivity.class));
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
