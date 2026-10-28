package com.moniliu.miimpulso

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

            onItemClick =   { objetivo ->
                Toast.makeText(
                    this,
                    "Has pulsado: $(objetivo.titulo)",
                    Toast.LENGTH_SHORT
                ).show()
        },
            onItemCheck = {objetivo ->
                objetivoDAO.update(objetivo)
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

            // creamos la ventana para añadir un objetivo

            val dialogo = AlertDialog.Builder(this)
                .setTitle("Nuevo objetivo")
                .setView(campoTexto)
                .setPositiveButton("Guardar", null)
                .setNegativeButton("Cancelar",null)
                .create()

            //abre una ventana para introducirlo

            dialogo.setOnShowListener{
                dialogo.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener{

                    val titulo = campoTexto.text.toString().trim()

                    if (titulo.isEmpty()){
                        campoTexto.error = "Escribe un objetivo"
                    } else {
                        // crea un objetivo (? utiliza el valor de la categoria)

                        val nuevoObjetivo = Objetivo(titulo = titulo, categoria = categoria ?: "Personal")

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