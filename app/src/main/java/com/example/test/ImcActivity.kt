package com.example.test

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class ImcActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imc)

        val mainLayout = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextWeight = findViewById<EditText>(R.id.editTextWeight)
        val editTextHeight = findViewById<EditText>(R.id.editTextHeight)
        val btnCalculate = findViewById<Button>(R.id.btnCalculateImc)
        val imageViewBmi = findViewById<ImageView>(R.id.imageViewBmi)
        val resultLayout = findViewById<LinearLayout>(R.id.resultLayout)
        val textViewImcValue = findViewById<TextView>(R.id.textViewImcValue)
        val textViewImcStatus = findViewById<TextView>(R.id.textViewImcStatus)

        btnCalculate.setOnClickListener {
            val weightStr = editTextWeight.text.toString()
            val heightStr = editTextHeight.text.toString()

            if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
                try {
                    val weight = weightStr.toDouble()
                    val heightCm = heightStr.toDouble()

                    if (weight > 0 && heightCm > 0) {
                        val heightM = heightCm / 100.0
                        val imc = weight / (heightM * heightM)
                        val formattedImc = String.format(Locale.US, "%.1f", imc)
                        
                        textViewImcValue.text = formattedImc

                        val (statusResId, imageResId) = when {
                            imc < 18.5 -> Pair(R.string.imc_underweight, R.drawable.maigre)
                            imc < 25.0 -> Pair(R.string.imc_normal, R.drawable.normal)
                            imc < 30.0 -> Pair(R.string.imc_overweight, R.drawable.surpoids)
                            imc < 35.0 -> Pair(R.string.imc_obese, R.drawable.obese)
                            else -> Pair(R.string.imc_severely_obese, R.drawable.t_obese)
                        }

                        textViewImcStatus.text = getString(statusResId)
                        imageViewBmi.setImageResource(imageResId)
                        resultLayout.visibility = View.VISIBLE
                    }
                } catch (e: NumberFormatException) {
                    // Handle invalid input format gracefully
                }
            }
        }
    }
}
