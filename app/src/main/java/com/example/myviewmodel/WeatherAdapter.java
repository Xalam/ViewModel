package com.example.myviewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private ArrayList<WeatherItems> weatherItems = new ArrayList<>();

    public void setData(ArrayList<WeatherItems> items){
        weatherItems.clear();
        weatherItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        return new WeatherViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.WeatherViewHolder holder, int position) {
        holder.bind(weatherItems.get(position));
    }

    @Override
    public int getItemCount() {
        return weatherItems.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder{
        TextView tvCity, tvTemperature, tvDescription;

        WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCity = itemView.findViewById(R.id.textCity);
            tvTemperature = itemView.findViewById(R.id.textTemp);
            tvDescription = itemView.findViewById(R.id.textDesc);
        }

        void bind(WeatherItems weatherItems){
            tvCity.setText(weatherItems.getName());
            tvTemperature.setText(weatherItems.getTemperature());
            tvDescription.setText(weatherItems.getDescription());
        }
    }
}
