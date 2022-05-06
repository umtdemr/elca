package com.example.elca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import dao.IItemDAO;
import entities.ItemEntity;
import model.AppDatabase;
import model.ItemUsageList;

public class DetailActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private IItemDAO itemDAO;
    private TextView lblUsage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initComponents();
        loadData();
    }
    private void initComponents() {
        lblUsage = findViewById(R.id.lblUsage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detailToolBar);
        toolbar.setTitle(getResources().getString(R.string.menu_usage_detail));
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        appDatabase = AppDatabase.getAppDatabase(DetailActivity.this);
        itemDAO = appDatabase.getItemDAO();
    }

    private void loadData() {
        List<ItemEntity> items = itemDAO.loadAllItems();
        ItemUsageList itemUsageList = new ItemUsageList(items);
        lblUsage.setText(String.valueOf(itemUsageList.getMonthlyInvoicePrice()) + " ₺");
    }
}