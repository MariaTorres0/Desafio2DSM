package com.example.desafios2dsm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafios2dsm.adapters.EstudianteAdapter
import com.example.desafios2dsm.datos.Estudiante
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class ListadoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var estudianteAdapter: EstudianteAdapter
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)

        recyclerView = findViewById(R.id.recyclerViewEstudiantes)
        val btnAgregarEstudiante = findViewById<FloatingActionButton>(R.id.btnAgregarEstudiante)

        recyclerView.layoutManager = LinearLayoutManager(this)
        estudianteAdapter = EstudianteAdapter(emptyList())
        recyclerView.adapter = estudianteAdapter

        database = FirebaseDatabase.getInstance().getReference("estudiantes")

        btnAgregarEstudiante.setOnClickListener {
            startActivity(Intent(this, RegistrarNotasActivity::class.java))
        }

        cargarEstudiantes()
    }

    private fun cargarEstudiantes() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaEstudiantes = mutableListOf<Estudiante>()
                for (data in snapshot.children) {
                    val estudiante = data.getValue(Estudiante::class.java)
                    estudiante?.let { listaEstudiantes.add(it) }
                }
                estudianteAdapter.actualizarLista(listaEstudiantes)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
