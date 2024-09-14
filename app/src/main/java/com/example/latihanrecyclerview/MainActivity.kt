package com.example.latihanrecyclerview

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.latihanrecyclerview.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RVAdapter(this@MainActivity, arrayListOf())

        binding.rvMain.adapter = adapter
        binding.rvMain.setHasFixedSize(true)

        remoteGetUsers()
    }

    fun remoteGetUsers(){
        ApiClient.apiService.getUsers().enqueue(object: Callback<ArrayList<UserModel>>{
            override fun onResponse(
                call: Call<ArrayList<UserModel>>,
                response: Response<ArrayList<UserModel>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    setDataToAdapter(data!!)
                }
            }

            override fun onFailure(call: Call<ArrayList<UserModel>>, t: Throwable) {
                Log.d("error", "" + t.stackTraceToString())
            }
        })
    }

    fun setDataToAdapter(data: ArrayList<UserModel>){
        adapter.setData(data)
    }

}