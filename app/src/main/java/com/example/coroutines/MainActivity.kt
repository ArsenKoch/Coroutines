package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.coroutines.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnSearch.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        binding.pbLoad.isVisible = true
        binding.btnSearch.isEnabled = false
        loadCity {
            binding.tvCity.text = it
            loadTemperature(it) {
                binding.tvWeather.text = it
                binding.pbLoad.isVisible = false
                binding.btnSearch.isEnabled = true
            }
        }
    }

    private fun loadCity(callback: (String) -> Unit) {
        thread {
            Thread.sleep(5000)
            runOnUiThread {
                callback.invoke("Moscow")
            }
        }
    }

    private fun loadTemperature(city: String, callback: (String) -> Unit) {
        thread {
            runOnUiThread {
                Toast.makeText(
                    this,
                    "Loading temp for city: {$city}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            Thread.sleep(5000)
            runOnUiThread {
                callback.invoke(17.toString())
            }
        }
    }
}