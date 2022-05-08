package com.example.elca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import dao.IItemDAO;
import entities.ItemEntity;
import model.AppDatabase;
import model.ItemUsage;
import model.ItemUsageList;

public class DetailActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private IItemDAO itemDAO;
    private TextView lblInvoicePrice, lblMonthlyUsage, lblDailyUsage, lblWeeklyUsage;
    private PieChart pieChart;
    private LinearLayoutCompat detailLayout, noDataLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initComponents();
        setupPieChart();
        loadData();
    }

    // Activity değişkenlerini layout ile eşleştir
    private void initComponents() {
        detailLayout = findViewById(R.id.detailLayout);
        noDataLayout = findViewById(R.id.noDataLayoutDetail);
        lblInvoicePrice = findViewById(R.id.lblInvoicePrice);
        lblMonthlyUsage = findViewById(R.id.lblMonthlyUsage);
        lblDailyUsage = findViewById(R.id.lblDailyUsage);
        lblWeeklyUsage = findViewById(R.id.lblWeeklyUsage);
        pieChart = findViewById(R.id.pieChart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detailToolBar);
        toolbar.setTitle(getResources().getString(R.string.menu_usage_detail));
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        appDatabase = AppDatabase.getAppDatabase(DetailActivity.this);
        itemDAO = appDatabase.getItemDAO();
    }

    // hiç veri olmaması durumunda no data layout'u göster
    private void controlLayout(boolean setNoDataLayoutVisible) {
        if (setNoDataLayoutVisible) {
            noDataLayout.setVisibility(View.VISIBLE);
            detailLayout.setVisibility(View.GONE);
        } else {
            noDataLayout.setVisibility(View.GONE);
            detailLayout.setVisibility(View.VISIBLE);
        }
    }

    // veritabanında kayıtlı eşyaları kullanarak ItemUsageList sınıfı ile hesap yapma, grafiği
    // yükleme
    private void loadData() {
        List<ItemEntity> items = itemDAO.loadAllItems();
        ItemUsageList itemUsageList = new ItemUsageList(items);
        lblInvoicePrice.setText(String.format("%,.2f", itemUsageList.getMonthlyInvoicePrice()) + "₺");
        lblMonthlyUsage.setText(String.format("%,.2f", itemUsageList.getMonthlyTotalUsage()) + " kilo watt");
        lblDailyUsage.setText(String.format("%,.2f",itemUsageList.getDailyTotalUsage()) + " kilo watt");
        lblWeeklyUsage.setText(String.format("%,.2f", itemUsageList.getWeeklyTotalUsage()) + " kilo watt");

        controlLayout(items.size() == 0);

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i=0; i<itemUsageList.getListSize(); i++) {
            ItemUsage itemUsage = itemUsageList.getItemUsageData(i);
            entries.add(new PieEntry(
                    (float) itemUsage.getMonthlyUsage(),
                    itemUsage.getItemTitle()
            ));
        }
       ArrayList<Integer> colors = new ArrayList<>();
       for (int color : ColorTemplate.MATERIAL_COLORS) {
           colors.add(color);
       }
       for (int color : ColorTemplate.VORDIPLOM_COLORS) {
           colors.add(color);
       }
       for (int color : ColorTemplate.LIBERTY_COLORS)
           colors.add(color);

       for (int color : ColorTemplate.PASTEL_COLORS)
           colors.add(color);

       PieDataSet dataSet = new PieDataSet(entries,
               getResources().getString(R.string.detail_chart_legend_title));
       dataSet.setColors(colors);

       PieData data = new PieData(dataSet);
       data.setDrawValues(true);
       data.setValueFormatter(new PercentFormatter(pieChart));
       data.setValueTextSize(12f);
       data.setValueTextColor(Color.BLACK);
       pieChart.setData(data);
       pieChart.invalidate();
    }

    // grafik için gerekli ayarlamalar
    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText(getResources().getString(R.string.detail_chart_title));
        pieChart.setCenterTextSize(17);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        Legend legendPieChart = pieChart.getLegend();
        legendPieChart.setDrawInside(false);
        legendPieChart.setEnabled(false);
    }
}