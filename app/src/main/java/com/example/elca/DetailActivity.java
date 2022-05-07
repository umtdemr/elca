package com.example.elca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
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
import model.ItemUsageList;

public class DetailActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private IItemDAO itemDAO;
    private TextView lblInvoicePrice, lblMonthlyUsage, lblDailyUsage, lblWeeklyUsage;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initComponents();
        setupPieChart();
        loadData();
    }
    private void initComponents() {
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

        /*
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        pieChart.setCenterText("selam");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);
        */
    }

    private void loadData() {
        List<ItemEntity> items = itemDAO.loadAllItems();
        ItemUsageList itemUsageList = new ItemUsageList(items);
        lblInvoicePrice.setText(String.format("%,.2f", itemUsageList.getMonthlyInvoicePrice()) + "₺");
        lblMonthlyUsage.setText(String.format("%,.2f", itemUsageList.getMonthlyTotalUsage()) + " kilo watt");
        lblDailyUsage.setText(String.format("%,.2f",itemUsageList.getDailyTotalUsage()) + " kilo watt");
        lblWeeklyUsage.setText(String.format("%,.2f", itemUsageList.getWeeklyTotalUsage()) + " kilo watt");

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.2f, "Food & Dining"));
        entries.add(new PieEntry(0.1f, "Something"));
        entries.add(new PieEntry(0.4f, "Merhb"));
        entries.add(new PieEntry(0.3f, "Deneme"));

       ArrayList<Integer> colors = new ArrayList<>();
       for (int color : ColorTemplate.MATERIAL_COLORS) {
           colors.add(color);
       }
       for (int color : ColorTemplate.VORDIPLOM_COLORS) {
           colors.add(color);
       }

       PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
       dataSet.setColors(colors);

       PieData data = new PieData(dataSet);
       data.setDrawValues(true);
       data.setValueFormatter(new PercentFormatter(pieChart));
       data.setValueTextSize(12f);
       data.setValueTextColor(Color.BLACK);
       pieChart.setData(data);
       pieChart.invalidate();
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Spending cat");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }
}