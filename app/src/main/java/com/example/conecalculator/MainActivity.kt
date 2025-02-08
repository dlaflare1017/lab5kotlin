package com.example.conecalculator

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.conecalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInput.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }

        binding.btnWork.setOnClickListener {
            val prefs: SharedPreferences = getSharedPreferences("ConeData", MODE_PRIVATE)
            val radius = prefs.getFloat("radius", 0f)
            val height = prefs.getFloat("height", 0f)
            val density = prefs.getFloat("density", 0f)
            val calcVolume = prefs.getBoolean("calcVolume", false)
            val calcMass = prefs.getBoolean("calcMass", false)

            if (radius == 0f || height == 0f || (calcMass && density == 0f)) {
                Toast.makeText(this, "Нет данных для вычисления", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val volume = (1.0 / 3.0) * Math.PI * radius * radius * height
            val mass = if (calcMass) volume * density else null

            val message = StringBuilder()
            if (calcVolume) message.append("Объем: %.2f см³\n".format(volume))
            if (calcMass != null) message.append("Масса: %.2f г".format(mass))

            AlertDialog.Builder(this)
                .setTitle("Результаты")
                .setMessage(message.toString())
                .setPositiveButton("OK", null)
                .show()
        }

        binding.btnExit.setOnClickListener {
            finishAffinity()
        }
    }
}
