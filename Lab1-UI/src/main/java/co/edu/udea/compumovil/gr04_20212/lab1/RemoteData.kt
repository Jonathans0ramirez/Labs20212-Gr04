package co.edu.udea.compumovil.gr04_20212.lab1

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RemoteData(private val context: Context) {
    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    interface StoreDataService {
        @GET("colombia.json")
        fun storeData(): Call<List<CitiesResponse>>
    }

    val storeData: Unit
        get() {
            retrofit!!.create(StoreDataService::class.java).storeData()
                .enqueue(object : Callback<List<CitiesResponse>> {
                    override fun onResponse(
                        call: Call<List<CitiesResponse>>,
                        response: Response<List<CitiesResponse>>
                    ) {
                        Log.d(
                            "Async Data RemoteData",
                            "Got REMOTE DATA " + (response.body()?.size)
                        )
                        val str: MutableList<String> = ArrayList()
                        val itr = response.body()?.listIterator()
                        while (itr?.hasNext() == true) {
                            for (city in itr.next().ciudades!!) {
                                str.add(city)
                            }
                        }
                        val cityAC = (context as Activity).findViewById<View>(R.id.etCity) as AutoCompleteTextView
                        val adapter = ArrayAdapter(
                            context,
                            R.layout.dropdown_item, str.toTypedArray()
                        )
                        cityAC.setAdapter(adapter)
                    }

                    override fun onFailure(call: Call<List<CitiesResponse>>, t: Throwable) {
                        Log.e(
                            "Async Data RemoteData",
                            "error in getting remote data"
                        )
                    }
                })
        }

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/marcovega/colombia-json/master/"
        private var retrofit: Retrofit? = null
    }
}
