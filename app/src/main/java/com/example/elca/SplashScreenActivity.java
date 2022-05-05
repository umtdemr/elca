package com.example.elca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // todo : rechange it to MainActivity
        startActivity(new Intent(SplashScreenActivity.this, AddItemActivity.class));
        finish();
    }
}