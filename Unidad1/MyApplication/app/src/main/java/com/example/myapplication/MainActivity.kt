package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var nombre: EditText

    lateinit var boton: Button

    lateinit var saludo: TextView
    lateinit var s: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nombre = findViewById(R.id.nombre1)
        boton = findViewById(R.id.boton)
        saludo = findViewById(R.id.saludo)

        boton.setOnClickListener {
            s = nombre.text.toString()
            if (s == ""){
                Toast.makeText(this,"Escribe tu nombre: ",Toast.LENGTH_SHORT).show()
            }
            else {
                s = "Hola " + s + ", cómo estás?"
                saludo.setText(s)
            }
        }


    }
}