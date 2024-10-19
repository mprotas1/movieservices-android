package com.movieapp.android;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.movieapp.android.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "In the onCreate() event of MainActivity.java class");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}