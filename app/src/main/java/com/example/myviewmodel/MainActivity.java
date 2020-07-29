package com.example.myviewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    EditText edtCity;
    Button btnCity;
    WeatherAdapter weatherAdapter;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        edtCity = findViewById(R.id.editCity);
        btnCity = findViewById(R.id.btnCity);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        weatherAdapter = new WeatherAdapter();
        weatherAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(weatherAdapter);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edtCity.getText().toString();

                if (TextUtils.isEmpty(city)) return;

                showLoading(true);
                mainViewModel.setWeather(city);
            }
        });

        mainViewModel.getWeather().observe(this, new Observer<ArrayList<WeatherItems>>() {
            @Override
            public void onChanged(ArrayList<WeatherItems> weatherItems) {
                if (weatherItems != null){
                    weatherAdapter.setData(weatherItems);
                    showLoading(false);
                }
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
