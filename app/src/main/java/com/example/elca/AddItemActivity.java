package com.example.elca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

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
        registerEventHandlers();
    }

    private void initComponents() {
        btnAdd = findViewById(R.id.btnAdd);
        txtTitleWrapper = findViewById(R.id.txtTitleWrapper);
        txtMaxWattWrapper = findViewById(R.id.txtMaxWattWrapper);
        txtAverageUsageWrapper = findViewById(R.id.txtAverageUsageWrapper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.addItemToolBar);
        toolbar.setTitle(getResources().getString(R.string.menu_add_item));
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

    }
    private void registerEventHandlers() {
        controlTxtTitleValid();
        controlAverageUsageValid();
    }

    private void controlTxtTitleValid() {
        EditText txtTitle = txtTitleWrapper.getEditText();

        txtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence value, int i, int i1, int i2) {
                String text = String.valueOf(value);
                if (text.length() > 12) {
                    txtTitleWrapper.setError(getResources().getString(R.string.add_item_title_error_long));
                } else {
                    txtTitleWrapper.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void controlAverageUsageValid() {
        EditText txtAverageUsage = txtAverageUsageWrapper.getEditText();

        txtAverageUsage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence value, int i, int i1, int i2) {
                String text = String.valueOf(value);
                if (text.length() > 0) {   // for avoiding empty text parsing
                    double averageUsage = Double.parseDouble(String.valueOf(text));

                    if (averageUsage > 24) {
                        txtAverageUsageWrapper.setError("ortalama kullanım 24 saatten az olamaz");
                    } else {
                        txtAverageUsageWrapper.setError(null);
                    }
                } else {
                    txtAverageUsageWrapper.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}