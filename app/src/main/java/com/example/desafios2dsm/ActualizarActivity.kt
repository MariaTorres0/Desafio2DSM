package com.example.desafios2dsm

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.desafios2dsm.datos.Estudiante
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActualizarActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var inputNombre: EditText
    private lateinit var inputApellido: EditText
    private lateinit var spinnerGrado: Spinner
    private lateinit var spinnerMateria: Spinner
    private lateinit var inputNota: EditText
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button

    val grados = listOf("Seleccione un grado", "1er grado", "2do grado", "3er grado", "4to grado", "5to grado", "6to grado", "7mo grado", "8vo grado", "9no grado")
    val materias = listOf("Seleccione una materia", "Matemáticas", "Ciencias", "Sociales", "Lenguaje", "Inglés")

    private var estudianteId: String? = null  // ID del estudiante

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar)

        inputNombre=findViewById(R.id.inputNombreEstudianteA)
        inputApellido=findViewById(R.id.inputApellidoEstudianteA)
        spinnerGrado = findViewById(R.id.spinnerGradoA)
        spinnerMateria = findViewById(R.id.spinnerMateriaA)
        inputNota = findViewById(R.id.inputNotaA)
        btnActualizar = findViewById(R.id.btnGuardarNotaA)
        btnEliminar = findViewById(R.id.btnEliminar)

        estudianteId = intent.getStringExtra("ESTUDIANTE_ID")

        database = FirebaseDatabase.getInstance().getReference("estudiantes")
        cargarDatosEstudiantes()

        // Configurar el botón de actualización
        btnActualizar.setOnClickListener {
            actualizarEstudiante()
        }

        btnEliminar.setOnClickListener {
            eliminarEstudiante()
        }

    }

    private fun cargarDatosEstudiantes(){
        if (estudianteId != null) {
            database.child(estudianteId!!).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val estudiante = snapshot.getValue(Estudiante::class.java)
                    estudiante?.let {
                        inputNombre.setText(it.nombre)
                        inputApellido.setText(it.apellido)
                        inputNota.setText(it.nota.toString())

                        // Seleccionar en los Spinners
                        cargarSpinners()

                        val gradoPos=grados.indexOf(it.grado)
                        if (gradoPos >= 0) {
                            spinnerGrado.setSelection(gradoPos)
                        }
                        // Seleccionar la materia en el spinner
                        val materiaPos = materias.indexOf(it.materia)
                        if (materiaPos >= 0) {
                            spinnerMateria.setSelection(materiaPos)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ActualizarActivity, "Error al cargar datos", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun cargarSpinners() {


        val adapterGrado = ArrayAdapter(this, android.R.layout.simple_spinner_item, grados)
        adapterGrado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGrado.adapter = adapterGrado

        val adapterMateria = ArrayAdapter(this, android.R.layout.simple_spinner_item, materias)
        adapterMateria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMateria.adapter = adapterMateria
    }
    private fun actualizarEstudiante() {
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

        val estudiante = Estudiante(estudianteId!!, nombre, apellido, grado, materia, nota)

        database.child(estudianteId!!).setValue(estudiante).addOnSuccessListener {
            Toast.makeText(this, "Estudiante actualizado correctamente", Toast.LENGTH_SHORT).show()
            finish() // Finaliza la actividad y regresa
        }.addOnFailureListener {
            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarEstudiante() {
        if (estudianteId != null) {
            database.child(estudianteId!!).removeValue().addOnSuccessListener {
                Toast.makeText(this, "Estudiante eliminado correctamente", Toast.LENGTH_SHORT).show()
                redirigirAListado()
            }.addOnFailureListener {
                Toast.makeText(this, "Error al eliminar estudiante", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun redirigirAListado() {
        val intent = Intent(this, ListadoActivity::class.java)
        startActivity(intent)
        finish()  // Cierra la actividad actual
    }


}
