package com.ripal.weather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)




        var retriver = WeatherRetriever()

        val callback = object : Callback<Weather> {
            /**
             * Invoked for a received HTTP response.
             *
             *
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call [Response.isSuccessful] to determine if the response indicates success.
             */
            override fun onResponse(call: Call<Weather>?, response: Response<Weather>?) {
                println("It's Working")

                title = response?.body()?.query?.results?.channel?.title

                var forecasts = response?.body()?.query?.results?.channel?.item?.forecast

                var forecastStrings = mutableListOf<String>()

                if (forecasts != null) {

                    for (forecast in forecasts) {

                        val newString = "${forecast.date} - High:${forecast.high} Low:${forecast.low} - ${forecast.text}"
                        forecastStrings.add(newString)

                    }
                }

                var listView = findViewById<ListView>(R.id.forecastListView)

                var adapter = ArrayAdapter(this@ForecastActivity,android.R.layout.simple_list_item_1, forecastStrings)

                listView.adapter = adapter

            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<Weather>?, t: Throwable?) {

                println("Its not working")
            }
        }

        val searchTerm = intent.extras.getString("searchTerm")

        retriver.getForecast(callback, searchTerm)

    }
}
