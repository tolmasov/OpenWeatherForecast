package com.example.pm06mnx.lesson12;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.pm06mnx.lesson12.list.ForecastAdapter;
import com.example.pm06mnx.lesson12.list.IRecyclerViewClickListener;
import com.example.pm06mnx.lesson12.list.dto.DayWeather;
import com.example.pm06mnx.lesson12.service.OpenWeatherService;
import com.example.pm06mnx.lesson12.service.dto.Forecast;
import com.example.pm06mnx.lesson12.service.dto.WeatherItem;
import com.example.pm06mnx.lesson12.utils.RetrofitHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IRecyclerViewClickListener, RecyclerView.OnItemTouchListener {

    private OpenWeatherService service;
    private RecyclerView recyclerView;
    private DataStorage dataStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataStorage = new DataStorage();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ForecastAdapter(dataStorage, this));
        recyclerView.addOnItemTouchListener(this);

        service = RetrofitHelper.getOpenWeatherService();
        loadForecast();
    }

    private void loadForecast() {
        final Call<Forecast> forecastCall = service.getForecast("Moscow", "52cfb4235ff69216aaa10d8f22aa86cf");
        forecastCall.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful()) {
                    Forecast forecast = response.body();
                    dataStorage.setForecast(convertResult(forecast.getList()));
                    recyclerView.getAdapter().notifyDataSetChanged();
                } else {
                    Log.e("GET_FORECAST_ERROR", "Ошибка получения прогноза, код "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.e("GET_FORECAST_ERROR", "Ошибка получения прогноза", t);
            }
        });

    }

    private List<DayWeather> convertResult(List<WeatherItem> forecast) {
        if (forecast == null || forecast.isEmpty()) {
            return Collections.emptyList();
        }
        List<DayWeather> result = new ArrayList<>();
        Calendar calendar = getBeginningOfCurrentDay();
        while (true) {
            Date dayStart = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            Date dayEnd = calendar.getTime();
            List<WeatherItem> dayWeatherItemList = extractDayWeather(forecast, dayStart, dayEnd);
            if (dayWeatherItemList.isEmpty()) {
                break;
            }
            result.add(makeOneDayWeather(dayStart, dayWeatherItemList));
        }
        return result;
    }

    private Calendar getBeginningOfCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar;
    }

    private List<WeatherItem> extractDayWeather(List<WeatherItem> forecast, Date dayStart, Date dayEnd) {
        List<WeatherItem> result = new ArrayList<>();
        for (WeatherItem weatherItem : forecast) {
            if (weatherItem.getDate() == null || weatherItem.getDate().after(dayEnd)) {
                break;
            }
            if (weatherItem.getDate().after(dayStart)) {
                result.add(weatherItem);
            }
        }
        return result;
    }

    private DayWeather makeOneDayWeather(Date day, List<WeatherItem> dayWeatherItemList) {
        DayWeather dayWeather = new DayWeather();
        dayWeather.setDay(day);
        dayWeather.setMaxTemp(Collections.max(dayWeatherItemList, new TempComparator()).getMain().getTemp());
        dayWeather.setMinTemp(Collections.min(dayWeatherItemList, new TempComparator()).getMain().getTemp());
        dayWeather.setDescription(dayWeatherItemList.stream().map(u -> u.getWeather().get(0).getDescription()).collect(Collectors.toSet()));
        dayWeather.setMaxWindSpeed(Collections.max(dayWeatherItemList, new WindComparator()).getWind().getSpeed());
        dayWeather.setMinWindSpeed(Collections.min(dayWeatherItemList, new WindComparator()).getWind().getSpeed());
        return dayWeather;
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder sender, int adapterPosition, int viewType) {
        Log.i("ON ITEM CLICK", "Click at position "+adapterPosition);
        DayWeather dayWeather = ((ForecastAdapter) recyclerView.getAdapter()).getItemByPosition(adapterPosition);
        Toast.makeText(this, dayWeather.getDay().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null) {
            long id = rv.getChildItemId(child);
            long adapterPosition = rv.getChildAdapterPosition(child);
            Log.i("ON ITEM CLICK INTERCEPT", "Click at position "+adapterPosition+" with id "+id);
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    //TODO: null safe
    private static class TempComparator implements Comparator<WeatherItem> {

        @Override
        public int compare(WeatherItem i1, WeatherItem i2) {
            Float temp1 = i1.getMain().getTemp();
            Float temp2 = i2.getMain().getTemp();
            return temp1.compareTo(temp2);
        }
    }

    //TODO: null safe
    private static class WindComparator implements Comparator<WeatherItem> {

        @Override
        public int compare(WeatherItem i1, WeatherItem i2) {
            Float wind1 = i1.getWind().getSpeed();
            Float wind2 = i2.getWind().getSpeed();
            return wind1.compareTo(wind2);
        }
    }

}
