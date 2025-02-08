package com.example.conecalculator

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.conecalculator.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("ConeData", Context.MODE_PRIVATE)

        // Загружаем сохраненные данные
        binding.etRadius.setText(prefs.getFloat("radius", 0f).toString())
        binding.etHeight.setText(prefs.getFloat("height", 0f).toString())
        binding.etDensity.setText(prefs.getFloat("density", 0f).toString())
        binding.cbVolume.isChecked = prefs.getBoolean("calcVolume", false)
        binding.cbMass.isChecked = prefs.getBoolean("calcMass", false)

        binding.btnSave.setOnClickListener {
            val radius = binding.etRadius.text.toString().toFloatOrNull()
            val height = binding.etHeight.text.toString().toFloatOrNull()
            val density = binding.etDensity.text.toString().toFloatOrNull()
            val calcVolume = binding.cbVolume.isChecked
            val calcMass = binding.cbMass.isChecked

            if (radius == null || height == null || (calcMass && density == null)) {
                Toast.makeText(this, "Некорректные данные!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            prefs.edit().apply {
                putFloat("radius", radius)
                putFloat("height", height)
                putFloat("density", density ?: 0f)
                putBoolean("calcVolume", calcVolume)
                putBoolean("calcMass", calcMass)
            }.apply()

            Toast.makeText(this, "Данные сохранены!", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}
