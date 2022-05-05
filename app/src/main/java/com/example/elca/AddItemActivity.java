package com.example.elca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class AddItemActivity extends AppCompatActivity {
    private boolean isTitleValid = false, isAverageUsageValid = false, isMaxWattValid = false;  // control form valid
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
        btnAddClick();
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
                    txtTitleWrapper.setErrorEnabled(false);
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
                        txtAverageUsageWrapper.setError(getResources().getString(R.string.add_item_average_usage_max_error));
                    } else {
                        txtAverageUsageWrapper.setErrorEnabled(false);
                    }
                } else {
                    txtAverageUsageWrapper.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void btnAddClick() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtTitleWrapper.isErrorEnabled() && !txtAverageUsageWrapper.isErrorEnabled()) {
                   if (txtTitleWrapper.getEditText().getText().length() > 0 &&
                           txtAverageUsageWrapper.getEditText().getText().length() > 0 &&
                           txtMaxWattWrapper.getEditText().getText().length() > 0
                   ) {
                       Toast.makeText(AddItemActivity.this, "gönderiliyor", Toast.LENGTH_LONG).show();
                   } else {
                       Toast.makeText(AddItemActivity.this,
                               getResources().getString(R.string.add_item_empty_fields),
                               Toast.LENGTH_SHORT).show();
                   }
                } else {
                    Toast.makeText(AddItemActivity.this,
                            getResources().getString(R.string.add_item_has_error),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}