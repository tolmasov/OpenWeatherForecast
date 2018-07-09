package com.example.pm06mnx.lesson12;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pm06mnx.lesson12.service.OpenWeatherService;
import com.example.pm06mnx.lesson12.service.dto.Forecast;
import com.example.pm06mnx.lesson12.utils.RetrofitHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private TextView mTextView;
    private OpenWeatherService service;

    private String forecast = "Данные еще не загружены";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.button);
        mTextView = findViewById(R.id.textView);

        service = RetrofitHelper.getOpenWeatherService();
        loadForecast();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText(forecast);
            }
        });
    }

    private void loadForecast() {
        final Call<Forecast> forecastCall = service.getForecast("Moscow", "52cfb4235ff69216aaa10d8f22aa86cf");
        forecastCall.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful()) {
                    Forecast forecastResponse = response.body();
                    if (forecastResponse != null && forecastResponse.getList() != null && !forecastResponse.getList().isEmpty()) {
                        forecast = forecastResponse.getList().get(0).getMain().getTemp().toString();
                        return;
                    }
                    forecast = "Прогноз не содержит данных";
                } else {
                    forecast = "Не удалось получить прогноз";
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.e("GET_FORECAST_ERROR", "Ошибка получения прогноза", t);
                forecast = "Не удалось получить прогноз";
            }
        });

    }

}
