package com.example.elca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class AddItemActivity extends AppCompatActivity {
    private boolean isValid;  // control form valid
    private MaterialButton btnAdd;
    private TextInputLayout txtTitleWrapper, txtMaxWattWrapper, txtAverageUsageWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        initComponents();
    }

    private void initComponents() {
        btnAdd = findViewById(R.id.btnAdd);
        txtTitleWrapper = findViewById(R.id.txtTitleWrapper);
        txtMaxWattWrapper = findViewById(R.id.txtAverageUsageWrapper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.addItemToolBar);
        toolbar.setTitle(getResources().getString(R.string.menu_add_item));
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

    }}