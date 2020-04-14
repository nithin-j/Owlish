package com.example.owlish;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "chartActivity";

    PieChart mainPieChart, incomePieChart, expensePieChart;

    float price;
    int i = 0;
    float[] incomeObjects = new float[20];
    float[] expenseObjects = new float[20];

    Toolbar mtoolbar;
    TextView textViewIncome,textViewExpanse,textViewRemaining;
    FloatingActionButton fab_load_new_entry;
    String uid;
    FirebaseUser user;
    private static float totalIncome = 0, totalExpense = 0;
    DatabaseReference incomeReference,expenseReference, incomePieReference,expensePieReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        totalIncome = 0;
        totalExpense = 0;

        incomeReference = FirebaseDatabase.getInstance().getReference(uid).child("Income");
        expenseReference = FirebaseDatabase.getInstance().getReference(uid).child("Expense");
        incomeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String val=ds.child("amount").getValue().toString();
                    float income = Float.parseFloat(val);
                    totalIncome+=income;
                }
                textViewIncome.setText("Total Income: $"+totalIncome);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        expenseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String val=ds.child("amount").getValue().toString();
                    float expanse=Float.parseFloat(val);
                    totalExpense+=expanse;
                }
                textViewExpanse.setText("Total Expanse: "+totalExpense);
                float remaining=totalIncome-totalExpense;
                textViewRemaining.setText("Total remaining: "+remaining);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initialize() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        incomePieReference = FirebaseDatabase.getInstance().getReference(uid).child("Income");
        expensePieReference = FirebaseDatabase.getInstance().getReference(uid).child("Expense");

        mtoolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mtoolbar);

        fab_load_new_entry = findViewById(R.id.btn_load_addnew_entry);
        fab_load_new_entry.setOnClickListener(this);

        textViewExpanse=findViewById(R.id.tvExpense);
        textViewIncome=findViewById(R.id.tvIncome);
        textViewRemaining=findViewById(R.id.tvRemaining);


        mainPieChart = findViewById(R.id.PieChartMain);
        incomePieChart = findViewById(R.id.PieChartIncome);
        expensePieChart = findViewById(R.id.PieChartExpense);

        loadPieChartData();
    }

    private void loadPieChartData() {
        mainPieChart.setRotationEnabled(true);
        mainPieChart.setHoleRadius(2f);
        mainPieChart.setTransparentCircleAlpha(0);
        mainPieChart.setCenterTextSize(10);

        Log.d(TAG, "Add Data Started");
        ArrayList<PieEntry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();

        yValues.add(new PieEntry(totalIncome));
        yValues.add(new PieEntry(totalExpense));

        xValues.add(String.valueOf(totalIncome));
        xValues.add(String.valueOf(totalExpense));

        PieDataSet pieDataSet = new PieDataSet(yValues, "Overall");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(18);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        mainPieChart.setData(pieData);
        mainPieChart.invalidate();

        incomePieChart.setRotationEnabled(true);
        incomePieChart.setHoleRadius(25f);
        incomePieChart.setTransparentCircleAlpha(0);
        incomePieChart.setCenterText("I");
        incomePieChart.setCenterTextSize(10);
        incomePieReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String val=ds.child("amount").getValue().toString();
                    price=Float.parseFloat(val);
                    incomeObjects[i]=price;
                    i++;
                }
                addIncomeData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        expensePieChart.setRotationEnabled(true);
        expensePieChart.setHoleRadius(25f);
        expensePieChart.setTransparentCircleAlpha(0);
        expensePieChart.setCenterText("E");
        expensePieChart.setCenterTextSize(10);
        expensePieReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String val=ds.child("amount").getValue().toString();
                    price=Float.parseFloat(val);
                    expenseObjects[i]=price;
                    i++;
                }
                addExpenseData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void addIncomeData() {

        Log.d(TAG, "addData started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        for(int i = 0; i < incomeObjects.length; i++){
            yEntrys.add(new PieEntry(incomeObjects[i],i));
        }

        ArrayList<String> xEntrys = new ArrayList<>();
        for(int i = 1; i < incomeObjects.length; i++){
            xEntrys.add(String.valueOf(incomeObjects[i]));
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Income");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(18);

//        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        incomePieChart.setData(pieData);
        incomePieChart.invalidate();

    }

    private void addExpenseData(){
        Log.d(TAG, "Add Data Started");
        ArrayList<PieEntry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i<expenseObjects.length;i++){
            yValues.add(new PieEntry(expenseObjects[i],i));
        }
        for(int i = 1; i < expenseObjects.length; i++){
            xValues.add(String.valueOf(expenseObjects[i]));
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yValues, "Expense");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(18);

//        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        expensePieChart.setData(pieData);
        expensePieChart.invalidate();

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

            case R.id.menuCategory:
                startActivity(new Intent(MainActivity.this, CategoryActivity.class));
                break;

            case R.id.menuReports:
                startActivity(new Intent(MainActivity.this, ReportsActivity.class));
                break;


            case R.id.menuHelp:
                Toast.makeText(this, "Coming soon enough", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
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
