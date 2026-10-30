package com.moniliu.miimpulso

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moniliu.miimpulso.activities.DetalleObjetivoActivity
import com.moniliu.miimpulso.adapters.ObjetivoAdapter
import com.moniliu.miimpulso.data.Objetivo
import com.moniliu.miimpulso.data.ObjetivoDAO

class ObjetivosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_objetivos)
        val categoria = intent.getStringExtra("categoria")
        val titulo = findViewById<TextView>(R.id.txtCategoria)
        titulo.text = categoria ?: "Mis objetivos"

        val btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            finish()

        }

        val objetivoDAO = ObjetivoDAO(this)

        val recyclerObjetivos = findViewById<RecyclerView>(R.id.recyclerObjetivos)

        // consultamos los objetivos guardados (recupera las tareas SQLite)
        val todosObjetivos = objetivoDAO.findAll()

        //mostramos solo los de la categoria seleccionada (filtra segun categoria)
        val objetivosFiltrados = todosObjetivos.filter {
            it.categoria == categoria
        }

        // creamos el adaptador
        val adapter  = ObjetivoAdapter(
            objetivosFiltrados,

            onItemClick = { objetivo ->
                Toast.makeText(this, "has  pulsado el objetivo", Toast.LENGTH_SHORT).show()
                val intent = Intent(
                this,
                DetalleObjetivoActivity::class.java
            )
                intent.putExtra("titulo", objetivo.titulo)
                intent.putExtra("categoria", objetivo.categoria)
                intent.putExtra("completado", objetivo.completado)
                intent.putExtra("descripcion", objetivo.descripcion)


                startActivity(intent)

        },
            onItemCheck = { ojetivo ->

                objetivoDAO.update(ojetivo)

            },

            // recibe el objetivo que has mantenido pulsado

            onItemDelete = { ojetivo ->

                // abre una ventana preguntando si quieres eliminarlo
                AlertDialog.Builder(this)
                    .setTitle("Eliminar objetivo")
                    .setMessage("Esta acción eliminará el objetivo de forma permanente.")
                    .setPositiveButton("Eliminar"){ _ , _ ->


                        // elimina de SQLite solmente cuando confirmas

                        objetivoDAO.delete(ojetivo)

                        // actualiza lo que aparece en pantalla

                        val listaActualizada = objetivoDAO.findAll().filter {
                            it.categoria == categoria
                        }

                        recyclerObjetivos.adapter?.let {

                            (it as ObjetivoAdapter)
                                .actualizarLista(listaActualizada)
                        }


                    }
                    .setNegativeButton ("Cancelar", null).
                    show()


            }


        )



        // conectamos el Recyclerview (muestra las tareas en pantalla)
        recyclerObjetivos.layoutManager = LinearLayoutManager(this)
        recyclerObjetivos.adapter = adapter

        val btnNuevoObjetivo = findViewById<Button>(R.id.btnNuevoObjetivo)

        // detecta cuando pulsamos el boton

        btnNuevoObjetivo.setOnClickListener {

            // permite escribir un objetivo
            val campoTexto = EditText(this)
            campoTexto.hint = "Escribe tu objetivo"
            campoTexto.setPadding(40, 24, 40, 24)

            // crea una variable para el segundo campo
            val campoDescripcion = EditText(this)
            campoDescripcion.hint = "Describe tu objetivo"

            // hint indicacion que desaparece al escribir
            campoDescripcion.minLines = 3
            campoDescripcion.setPadding(40,24, 40,24)

            val contenedor = LinearLayout(this)
            contenedor.orientation = LinearLayout.VERTICAL

            contenedor.addView(campoTexto)
            contenedor.addView(campoDescripcion)


            // creamos la ventana para añadir un objetivo

            val dialogo = AlertDialog.Builder(this)
                .setTitle("Nuevo objetivo")
                .setView(contenedor)
                .setPositiveButton("Guardar", null)
                .setNegativeButton("Cancelar",null)
                .create()

            //abre una ventana para introducirlo

            dialogo.setOnShowListener{
                dialogo.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener{

                    val titulo = campoTexto.text.toString().trim()

                    val descripcion = campoDescripcion.text.toString().trim()

                    if (titulo.isEmpty()){
                        campoTexto.error = "Escribe un objetivo"
                    } else {
                        // crea un objetivo (? utiliza el valor de la categoria)

                        val nuevoObjetivo = Objetivo(
                            titulo = titulo,
                            categoria = categoria ?: "Personal",
                            descripcion = descripcion
                        )

                        // guarda el objetivo en SQLite

                        objetivoDAO.insert(nuevoObjetivo)

                        val listActualizada = objetivoDAO.findAll().filter {
                            it.categoria == categoria

                        }

                        // actualiza la lista visible
                        adapter.actualizarLista(listActualizada)
                        dialogo.dismiss()
                    }

                }
            }

            dialogo.show()


        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}