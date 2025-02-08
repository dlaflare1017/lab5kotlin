package com.example.conecalculator

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InputActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        prefs = getSharedPreferences("ConeData", Context.MODE_PRIVATE)

        val etRadius = findViewById<EditText>(R.id.etRadius)
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val etDensity = findViewById<EditText>(R.id.etDensity)
        val cbVolume = findViewById<CheckBox>(R.id.cbVolume)
        val cbMass = findViewById<CheckBox>(R.id.cbMass)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // Загружаем сохраненные данные
        etRadius.setText(prefs.getFloat("radius", 0f).toString())
        etHeight.setText(prefs.getFloat("height", 0f).toString())
        etDensity.setText(prefs.getFloat("density", 0f).toString())
        cbVolume.isChecked = prefs.getBoolean("calcVolume", false)
        cbMass.isChecked = prefs.getBoolean("calcMass", false)

        btnSave.setOnClickListener {
            val radius = etRadius.text.toString().toFloatOrNull()
            val height = etHeight.text.toString().toFloatOrNull()
            val density = etDensity.text.toString().toFloatOrNull()
            val calcVolume = cbVolume.isChecked
            val calcMass = cbMass.isChecked

            if (radius == null || height == null || (calcMass && density == null)) {
                Toast.makeText(this, "Некорректные данные!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("ConeData", MODE_PRIVATE)
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

        btnBack.setOnClickListener {
            finish()
        }
    }
}
