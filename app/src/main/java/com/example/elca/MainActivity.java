package com.example.elca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import adapter.ItemRecyclerViewAdapter;
import co.dift.ui.SwipeToAction;
import dao.IItemDAO;
import entities.ItemEntity;
import model.AppDatabase;
import model.Item;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeToAction swipeToAction;
    private SharedPreferences sharedPreferences;
    public Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private LinearLayoutCompat noDataLayout;

    private AppDatabase appDatabase;
    private IItemDAO itemDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        registerEventHandlers();
        loadData();
    }

    private void initComponents() {
        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        noDataLayout = findViewById(R.id.noDataLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("AppSettingPrefs", 0);

        toggleDarkTheme();

        appDatabase = AppDatabase.getAppDatabase(MainActivity.this);
        itemDAO = appDatabase.getItemDAO();
    }
    private void registerEventHandlers() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddItemActivity.class));
            }
        });
    }

    private void loadData() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        List<ItemEntity> items = itemDAO.loadAllItems();

        controlLayout(items.size() == 0);

        ItemRecyclerViewAdapter adapter = new ItemRecyclerViewAdapter(items);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<ItemEntity>() {
            @Override
            public boolean swipeLeft(ItemEntity itemData) {
                deleteItem(itemData);
                return true;
            }

            @Override
            public boolean swipeRight(ItemEntity itemData) {
                return false;
            }

            @Override
            public void onClick(ItemEntity itemData) {

            }

            @Override
            public void onLongClick(ItemEntity itemData) {

            }
        });
    }

    private void toggleDarkTheme() {
       boolean isNightModeOn = sharedPreferences.getBoolean("NightMode", false);
       if (isNightModeOn) {
           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
       } else {
           AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
       }
    }

    private void changeToggleThemeIcon(MenuItem menuItem, boolean isNightModeOn) {
        if (isNightModeOn) {
            menuItem.setIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_wb_sunny_24));
            menuItem.setTitle(R.string.menu_dark_theme_inactive);
        } else {
            menuItem.setIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_nights_stay_24));
            menuItem.setTitle(R.string.menu_dark_theme_active);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        changeToggleThemeIcon(menu.findItem(R.id.action_toggle_dark_theme), sharedPreferences.getBoolean("NightMode", false));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_item_list:
                Intent itemListIntent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(itemListIntent);
                return true;
            case R.id.action_add_item:
                Intent addItemIntent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(addItemIntent);
                return true;
            case R.id.action_toggle_dark_theme:
                boolean isNightModeOn = sharedPreferences.getBoolean("NightMode", false);
                SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
                if (isNightModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    preferencesEditor.putBoolean("NightMode", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    preferencesEditor.putBoolean("NightMode", true);
                }
                changeToggleThemeIcon(item, isNightModeOn);
                preferencesEditor.apply();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteItem(ItemEntity item) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle(getResources().getString(R.string.item_delete_alert_title));
        alertBuilder.setMessage(getResources().getString(R.string.item_delete_alert_message));
        alertBuilder.setIcon(R.drawable.ic_warn);

        alertBuilder.setPositiveButton(getResources().getString(R.string.item_delete_positive_button_title),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemDAO.deleteItem(item);
                reloadData();

                Snackbar snackbar = Snackbar.make(recyclerView,
                        item.getTitle() + " silindi", Snackbar.LENGTH_LONG);
                snackbar.setAction(getResources().getString(R.string.snackbar_action_back),
                        new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemDAO.insertItem(item);
                        reloadData();
                    }
                });
                snackbar.show();
            }
        });

        alertBuilder.setNegativeButton(getResources().getString(R.string.item_delete_negative_button_title),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
            }
        });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
    private void reloadData() {
        List<ItemEntity> items = itemDAO.loadAllItems();
        ItemRecyclerViewAdapter adapter = new ItemRecyclerViewAdapter(items);
        controlLayout(items.size() == 0);

        recyclerView.setAdapter(adapter);
    }

    private void controlLayout(boolean setNoDataLayoutVisible) {
       if (setNoDataLayoutVisible) {
           noDataLayout.setVisibility(View.VISIBLE);
           recyclerView.setVisibility(View.GONE);
       } else {
           noDataLayout.setVisibility(View.GONE);
           recyclerView.setVisibility(View.VISIBLE);
       }
    }
}