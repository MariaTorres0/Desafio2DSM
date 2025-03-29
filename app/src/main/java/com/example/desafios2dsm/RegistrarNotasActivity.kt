package com.example.desafios2dsm

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.desafios2dsm.R
import com.google.firebase.database.FirebaseDatabase
import com.example.desafios2dsm.models.Estudiante

class RegistrarNotasActivity : AppCompatActivity() {

    private lateinit var inputNombre: EditText
    private lateinit var inputApellido: EditText
    private lateinit var spinnerGrado: Spinner
    private lateinit var spinnerMateria: Spinner
    private lateinit var inputNota: EditText
    private lateinit var btnGuardar: Button

    private val database = FirebaseDatabase.getInstance()
    private val refEstudiantes = database.getReference("estudiantes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_notas)

        inputNombre = findViewById(R.id.inputNombreEstudiante)
        inputApellido = findViewById(R.id.inputApellidoEstudiante)
        spinnerGrado = findViewById(R.id.spinnerGrado)
        spinnerMateria = findViewById(R.id.spinnerMateria)
        inputNota = findViewById(R.id.inputNota)
        btnGuardar = findViewById(R.id.btnGuardarNota)

        // Llenar los Spinners
        cargarSpinners()

        btnGuardar.setOnClickListener {
            guardarEstudiante()
        }
    }

    private fun cargarSpinners() {
        val grados = listOf("Seleccione un grado", "1er grado", "2do grado", "3er grado", "4to grado", "5to grado", "6to grado", "7mo grado", "8vo grado", "9no grado")
        val materias = listOf("Seleccione una materia", "Matemáticas", "Ciencias", "Sociales", "Lenguaje", "Inglés", "Educación física")

        val adapterGrado = ArrayAdapter(this, android.R.layout.simple_spinner_item, grados)
        adapterGrado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGrado.adapter = adapterGrado

        val adapterMateria = ArrayAdapter(this, android.R.layout.simple_spinner_item, materias)
        adapterMateria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMateria.adapter = adapterMateria
    }

    private fun guardarEstudiante() {
        val nombre = inputNombre.text.toString().trim()
        val apellido = inputApellido.text.toString().trim()
        val grado = spinnerGrado.selectedItem?.toString() ?: ""
        val materia = spinnerMateria.selectedItem?.toString() ?: ""
        val notaText = inputNota.text.toString().trim()

        if (nombre.isEmpty() || apellido.isEmpty() || grado == "Seleccione un grado" || materia == "Seleccione una materia" || notaText.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val nota = notaText.toDoubleOrNull()
        if (nota == null || nota < 0.0 || nota > 10.0) {
            Toast.makeText(this, "La nota debe estar entre 0 y 10", Toast.LENGTH_SHORT).show()
            return
        }

        val id = refEstudiantes.push().key ?: return
        val estudiante = Estudiante(id, nombre, apellido, grado, materia, nota)

        refEstudiantes.child(id).setValue(estudiante).addOnSuccessListener {
            Toast.makeText(this, "Estudiante registrado correctamente", Toast.LENGTH_SHORT).show()
            limpiarCampos()
        }.addOnFailureListener {
            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        inputNombre.text.clear()
        inputApellido.text.clear()
        inputNota.text.clear()
        spinnerGrado.setSelection(0)
        spinnerMateria.setSelection(0)
    }
}
