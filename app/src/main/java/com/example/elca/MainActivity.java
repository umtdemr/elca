package com.example.elca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

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

        ItemRecyclerViewAdapter adapter = new ItemRecyclerViewAdapter(itemDAO.loadAllItems());
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
                Intent itemListIntent = new Intent(MainActivity.this, ItemsListActivity.class);
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
        alertBuilder.setTitle("Emin misiniz?");
        alertBuilder.setMessage("Eşyayı silmek istediğinizden emin misiniz?");
        alertBuilder.setIcon(R.drawable.ic_warn);

        alertBuilder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemDAO.deleteItem(item);
                reloadData();
            }
        });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
    private void reloadData() {
        ItemRecyclerViewAdapter adapter = new ItemRecyclerViewAdapter(
                itemDAO.loadAllItems()
        );
        recyclerView.setAdapter(adapter);
    }
}