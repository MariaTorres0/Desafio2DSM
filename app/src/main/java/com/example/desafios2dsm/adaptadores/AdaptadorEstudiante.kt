package com.example.desafios2dsm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafios2dsm.R
import com.example.desafios2dsm.datos.Estudiante

class EstudianteAdapter(private var listaEstudiantes: List<Estudiante>) :
    RecyclerView.Adapter<EstudianteAdapter.EstudianteViewHolder>() {

    inner class EstudianteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombre)
        val txtDetalle: TextView = view.findViewById(R.id.txtDetalle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstudianteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_estudiante, parent, false)
        return EstudianteViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstudianteViewHolder, position: Int) {
        val estudiante = listaEstudiantes[position]
        holder.txtNombre.text = "${estudiante.nombre} ${estudiante.apellido}"
        holder.txtDetalle.text = "Grado: ${estudiante.grado} - Materia: ${estudiante.materia} - Nota: ${estudiante.nota}"
    }

    override fun getItemCount(): Int = listaEstudiantes.size

    fun actualizarLista(nuevaLista: List<Estudiante>) {
        listaEstudiantes = nuevaLista
        notifyDataSetChanged()
    }
}
