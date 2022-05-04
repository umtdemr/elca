package com.example.elca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("AppSettingPrefs", 0);
        toggleDarkTheme();
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
        } else {
            menuItem.setIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_baseline_nights_stay_24));
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
                Intent intent = new Intent(MainActivity.this, ItemsListActivity.class);
                startActivity(intent);
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
}