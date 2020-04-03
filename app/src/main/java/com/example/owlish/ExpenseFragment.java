package com.example.owlish;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.example.owlish.Data.Categories;
import com.example.owlish.Data.Expense;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment implements View.OnClickListener {


    AppCompatSpinner spinner_categories;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference categoriesReference, expenseReference;

    MaterialButton btnExpenseAdd, btnExpenseReset;
    TextInputEditText etExpenseTitle, etExpenseDesc, etExpenseAmount, etExpenseDate;


    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expense, container, false);


        categoriesReference = FirebaseDatabase.getInstance().getReference("Categories");
        expenseReference = FirebaseDatabase.getInstance().getReference("Expense");

        arrayAdapter = new ArrayAdapter<String>(inflater.getContext(),android.R.layout.simple_spinner_item,arrayList);

        etExpenseTitle = view.findViewById(R.id.etExpenseTitle);
        etExpenseDesc = view.findViewById(R.id.etExpenseDesc);
        etExpenseAmount = view.findViewById(R.id.etExpenseAmount);
        etExpenseDate = view.findViewById(R.id.etExpenseDate);
        spinner_categories = view.findViewById(R.id.spinner_expense_categories);
        btnExpenseAdd = view.findViewById(R.id.button_ExpenseAdd);
        btnExpenseReset = view.findViewById(R.id.button_ExpenseReset);

        btnExpenseAdd.setOnClickListener(this);
        btnExpenseReset.setOnClickListener(this);

        loadCategories();

        return view;
    }

    private void loadCategories() {

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_categories.setAdapter(arrayAdapter);

        categoriesReference.addChildEventListener(new ChildEventListener() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_ExpenseAdd:
                addExpense();
                break;
            case R.id.button_ExpenseReset:
                reset();
                break;
        }
    }

    private void addExpense() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = database.getReference("Expense").push().getKey();
        String title = etExpenseTitle.getText().toString();
        String desc = etExpenseDesc.getText().toString();
        String type = spinner_categories.getSelectedItem().toString();
        String expenseDate = etExpenseDate.getText().toString();
        float amount = Float.valueOf(etExpenseAmount.getText().toString());

        Expense expense = new Expense(id, title, desc, amount, expenseDate, type);
        expenseReference.child(String.valueOf(id)).setValue(expense);

        reset();

    }

    private void reset() {

        etExpenseTitle.setText(null);
        etExpenseDesc.setText(null);
        etExpenseDate.setText(null);
        etExpenseAmount.setText(null);

        etExpenseTitle.requestFocus();
    }
}
