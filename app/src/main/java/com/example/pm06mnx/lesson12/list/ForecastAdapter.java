package com.example.pm06mnx.lesson12.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pm06mnx.lesson12.DataStorage;
import com.example.pm06mnx.lesson12.R;
import com.example.pm06mnx.lesson12.list.dto.DayWeather;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;

/**
 * Адаптер для прогноза погоды
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private DataStorage dataStorage;
    private IRecyclerViewClickListener clickListener;

    public ForecastAdapter(@NonNull DataStorage dataStorage, IRecyclerViewClickListener clickListener) {
        this.dataStorage = dataStorage;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new ViewHolder(listItem, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ViewHolder holder, int position) {
        DayWeather weatherItem = dataStorage.getForecast().get(position);
        holder.mDateTextView.setText(getFormattedDate(weatherItem));
        holder.mTempTextView.setText(getTemperature(weatherItem));
        holder.mDescriptionTextView.setText(getWeatherDescription(weatherItem));
    }

    private String getFormattedDate(DayWeather dayWeather) {
        if (dayWeather == null || dayWeather.getDay() == null) {
            return "";
        }
        return new SimpleDateFormat("dd MMMM yyyy").format(dayWeather.getDay());
    }

    private String getTemperature(DayWeather dayWeather) {
        if (dayWeather == null) {
            return "";
        }
        return dayWeather.getMinTemp()+" .. "+dayWeather.getMaxTemp();
    }

    private String getWeatherDescription(DayWeather dayWeather) {
        if (dayWeather == null || dayWeather.getDescription() == null || dayWeather.getDescription().isEmpty()) {
            return "";
        }
        return StringUtils.join(dayWeather.getDescription(), ", ");
    }

    @Override
    public int getItemCount() {
        return dataStorage.getForecast() != null ? dataStorage.getForecast().size() : 0;
    }

    /**
     * @param adapterPos позиция элемента в списке
     * @return прогноз погоды с заданной позицией
     */
    public DayWeather getItemByPosition(int adapterPos) {
        return dataStorage.getForecast().get(adapterPos);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final IRecyclerViewClickListener mClickListener; //TODO: нужна ли WeakReference?
        private TextView mDateTextView;
        private TextView mTempTextView;
        private TextView mDescriptionTextView;

        public ViewHolder(View v, IRecyclerViewClickListener clickListener) {
            super(v);
            v.setOnClickListener(this);
            mClickListener = clickListener;
            mDateTextView = itemView.findViewById(R.id.date_list_item);
            mTempTextView = itemView.findViewById(R.id.temp_list_item);
            mDescriptionTextView = itemView.findViewById(R.id.description_list_item);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (itemView == view && position != RecyclerView.NO_POSITION) {
                mClickListener.onItemClick(this, position, getItemViewType());
            }
        }
    }

}
