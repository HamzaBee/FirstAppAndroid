package com.example.test

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.ArrayList

class GameActivity : AppCompatActivity() {
    private lateinit var buttonOK: Button
    private lateinit var editTextNumber: EditText
    private lateinit var listViewHisto: ListView
    private lateinit var textViewIndication: TextView
    private lateinit var progressBarScore: ProgressBar
    private lateinit var textViewScore: TextView
    private lateinit var textViewScoreCumul: TextView

    private var secret: Int = 0
    private var nombreEssais: Int = 1
    private val nombreMaxEssais: Int = 6
    private val historique = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private var scoreCumule: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextNumber = findViewById(R.id.editTextNumber)
        listViewHisto = findViewById(R.id.lisViewHisto)
        textViewIndication = findViewById(R.id.textViewIndication)
        textViewScore = findViewById(R.id.textViewScore)
        progressBarScore = findViewById(R.id.progressBarScore)
        buttonOK = findViewById(R.id.buttonOK)
        textViewScoreCumul = findViewById(R.id.textViewScoreCumul)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, historique)
        listViewHisto.adapter = adapter

        initialisation()

        buttonOK.setOnClickListener {
            val str = editTextNumber.text.toString()
            var number = 0
            try {
                number = str.toInt()
            } catch (ex: NumberFormatException) {
                ex.printStackTrace()
                return@setOnClickListener
            }

            historique.add("$nombreEssais => $number")
            adapter.notifyDataSetChanged()
            Log.i("MyInfos", getString(R.string.essai_numero) + nombreEssais.toString() + "=>" + number.toString())

            textViewScore.text = nombreEssais.toString()
            progressBarScore.progress = nombreEssais

            if (number > secret) {
                textViewIndication.text = getString(R.string.nombre_plus_grand)
            } else if (number < secret) {
                textViewIndication.text = getString(R.string.nombre_plus_petit)
            } else {
                textViewIndication.text = getString(R.string.bravo)
                scoreCumule += 5
                retry()
            }
            editTextNumber.setText("")

            if (number != secret && nombreEssais >= nombreMaxEssais) {
                retry()
            }
            nombreEssais++
        }
    }

    private fun retry() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.str_nouvel_essai))
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { dialog, which ->
            initialisation()
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Finish") { dialog, which ->
            finish()
        }
        alertDialog.show()
    }

    private fun initialisation() {
        this.nombreEssais = 1
        this.secret = 1 + (Math.random() * 100).toInt()
        this.editTextNumber.requestFocus()
        this.progressBarScore.progress = nombreEssais
        this.textViewIndication.text = "..."
        this.editTextNumber.setText("")
        this.textViewScore.text = nombreEssais.toString()
        this.textViewScoreCumul.text = scoreCumule.toString()
        this.historique.clear()
        this.adapter.notifyDataSetChanged()
    }
}
