package com.example.desafios2dsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ListadoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)

        val btnAgregarEstudiante = findViewById<Button>(R.id.btnAgregarEstudiante)
        btnAgregarEstudiante.setOnClickListener {
            val intent = Intent(this, RegistrarNotasActivity::class.java)
            startActivity(intent)
        }
    }
}
