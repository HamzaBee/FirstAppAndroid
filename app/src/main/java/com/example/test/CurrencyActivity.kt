package com.example.test

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.ArrayList

class CurrencyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_currency)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextAmount = findViewById<EditText>(R.id.editTextAmount)
        val btnCompute = findViewById<Button>(R.id.btnCompute)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)
        val listViewResult = findViewById<ListView>(R.id.listViewResults)

        val data = ArrayList<String>()
        val stringArrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        listViewResult.adapter = stringArrayAdapter

        btnCompute.setOnClickListener {
            val amountText = editTextAmount.text.toString()
            if (amountText.isNotEmpty()) {
                try {
                    val amount = amountText.toDouble()
                    val result = amount * 11
                    textViewResult.text = result.toString()
                    data.add("$amount=>$result")
                    stringArrayAdapter.notifyDataSetChanged()
                    editTextAmount.setText("")
                } catch (e: NumberFormatException) {
                    // Handle invalid input format gracefully
                }
            }
        }
    }
}
