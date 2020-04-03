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
import com.example.owlish.Data.Income;
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
public class IncomeFragment extends Fragment implements View.OnClickListener {

    AppCompatSpinner spinner_categories;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference categoriesReference, incomeReference;

    MaterialButton btnIncomeAdd, btnIncomeReset;
    TextInputEditText etIncomeTitle, etIncomeDesc, etIncomeAmount, etIncomeDate;

    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_income, container, false);
        spinner_categories = view.findViewById(R.id.spinner_income_categories);

        categoriesReference = FirebaseDatabase.getInstance().getReference("Categories");
        incomeReference = FirebaseDatabase.getInstance().getReference("Income");

        arrayAdapter = new ArrayAdapter<String>(inflater.getContext(),android.R.layout.simple_spinner_item,arrayList);

        etIncomeTitle = view.findViewById(R.id.etIncomeTitle);
        etIncomeDesc = view.findViewById(R.id.etIncomeDesc);
        etIncomeAmount = view.findViewById(R.id.etIncomeAmount);
        etIncomeDate = view.findViewById(R.id.etIncomeDate);
        spinner_categories = view.findViewById(R.id.spinner_income_categories);
        btnIncomeAdd = view.findViewById(R.id.button_IncomeAdd);
        btnIncomeReset = view.findViewById(R.id.button_IncomeReset);

        btnIncomeAdd.setOnClickListener(this);
        btnIncomeReset.setOnClickListener(this);

        loadCategories();
        return  view;
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
            case R.id.button_IncomeAdd:
                addIncome();
                break;
            case R.id.button_IncomeReset:
                reset();
                break;
        }
    }

    private void addIncome() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = database.getReference("Expense").push().getKey();
        String title = etIncomeTitle.getText().toString();
        String desc = etIncomeDesc.getText().toString();
        String type = spinner_categories.getSelectedItem().toString();
        String expenseDate = etIncomeDate.getText().toString();
        float amount = Float.valueOf(etIncomeAmount.getText().toString());

        Income expense = new Income(id, title, desc, amount, expenseDate, type);
        incomeReference.child(String.valueOf(id)).setValue(expense);

        reset();
    }

    private void reset() {

        etIncomeTitle.setText(null);
        etIncomeDesc.setText(null);
        etIncomeDate.setText(null);
        etIncomeAmount.setText(null);

        etIncomeTitle.requestFocus();
    }
}
