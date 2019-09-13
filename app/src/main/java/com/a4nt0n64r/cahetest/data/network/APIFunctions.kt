package com.a4nt0n64r.cahetest.data.network

import android.util.Log
import com.a4nt0n64r.cahetest.BuildConfig
import com.a4nt0n64r.cahetest.domain.model.Player
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIFunctions {
    
    fun getPlayer(name: String, callback: (Player) -> Unit) {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl("https://api.pubg.com/shards/steam/")
            .build()


        val apIservice = retrofit.create(APIservice::class.java)

        val call = apIservice.getPlayerFromCloud(name)

        call.enqueue(object : Callback<Player> {

            override fun onFailure(call: Call<Player>, t: Throwable) {
                Log.d("FAIL", "FAIL что-то не так!")

            }

            override fun onResponse(call: Call<Player>, response: Response<Player>) {
                val data = response.body()
                if (data != null) callback.invoke(data)
            }
        })
    }
}