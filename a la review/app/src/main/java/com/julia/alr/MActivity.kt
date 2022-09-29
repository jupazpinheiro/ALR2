package com.julia.alr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.julia.alr.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.julia.alr.network.CharacterResponse
import com.julia.retrofit.MainAdapter

class MActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = ApiClient.apiService.fetchCharacter("1")

        client.enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ){if(response.isSuccessful){
                Log.d("descricoes",""+response.body())
                val result= response.body()?.result
                result?.let{
                    val adapter = MainAdapter(result)
                    val recyclerView = findViewById<RecyclerView>(R.id.characterRv)
                    recyclerView?.layoutManager= StaggeredGridLayoutManager (2, StaggeredGridLayoutManager.VERTICAL)
                    recyclerView?.adapter=adapter
                }
            }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                Log.e("Falha",""+t.message)
            }
        })
    }
}