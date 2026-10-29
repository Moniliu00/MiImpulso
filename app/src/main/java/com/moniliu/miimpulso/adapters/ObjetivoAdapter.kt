package com.moniliu.miimpulso.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moniliu.miimpulso.R
import com.moniliu.miimpulso.data.Objetivo

// conecta los objetivos con la lista visual

class ObjetivoAdapter (

    private var objetivos: List<Objetivo>,
    private val onItemClick: (Objetivo) -> Unit,
    private val onItemCheck: (Objetivo) -> Unit,
    private val onItemDelete: (Objetivo) -> Unit
) : RecyclerView.Adapter<ObjetivoAdapter.ObjetivoViewHolder>() {


        // identifica titulo y la casilla de cada tarea

    class ObjetivoViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val titulo: TextView = view.findViewById(R.id.txtTituloObjetivo)

        val check: CheckBox = view.findViewById(R.id.checkCompletado)

        val eliminar : ImageButton = view.findViewById(R.id.btnEliminar)

    }

    // crea el diseño de una fila

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjetivoViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_objetivo, parent, false)

        return ObjetivoViewHolder(view)

    }

    // coloca los datos de cada objetivo en su fila

    override fun onBindViewHolder(
        holder: ObjetivoViewHolder,
        position: Int
    ){
        val objetivo = objetivos[position]

        holder.eliminar.setOnClickListener {
            onItemDelete(objetivo)
        }

        holder.titulo.text = objetivo.titulo
        holder.check.isChecked = objetivo.completado
        // cuando pulsemos la casilla
        holder.check.setOnClickListener {
            objetivo.completado = holder.check.isChecked
            onItemCheck(objetivo)

        }

        // cuando pulsamos la fila
        holder.itemView.setOnClickListener { onItemClick(objetivo)
        }

        holder.itemView.setOnLongClickListener {
            onItemDelete(objetivo)
            true
        }
    }

    // indica cuantos objetivos hay

    override fun getItemCount(): Int {
        return  objetivos.size
    }

    fun actualizarLista (nuevaLista : List<Objetivo>) {
        objetivos = nuevaLista
        notifyDataSetChanged()
    }


    }




