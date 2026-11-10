package com.moniliu.miimpulso.activities

import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.moniliu.miimpulso.R
import com.moniliu.miimpulso.data.Objetivo
import com.moniliu.miimpulso.data.ObjetivoDAO

class DetalleObjetivoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_objetivo)

        //recupera un texto enviado desde otra pantalla

        val titulo = intent.getStringExtra("titulo")
        val categoria = intent.getStringExtra("categoria")
        val descripcion = intent.getStringExtra("descripcion")
        val objetivoId = intent.getIntExtra("id",-1)


        //recupera un valor verdadero o falso
        val completado = intent.getBooleanExtra("completado", false)

        val txtTitulo = findViewById<TextView>(R.id.txtDetalleTitulo)
        val txtEncabezado = findViewById<TextView>(R.id.txtEncabezadoDetalle)
        val txtCategoria = findViewById<TextView>(R.id.txtDetalleCategoria)
        val txtEstado = findViewById<TextView>(R.id.txtDetalleEstado)
        val txtDescripcion = findViewById<TextView>(R.id.txtDetalleDescripcion)
        val btnEditar = findViewById<ImageView>(R.id.btnEditarObjetivo)

        txtTitulo.text = titulo ?: "Sin titulo"
        txtCategoria.text = categoria ?: "Sin categoria"
        txtEstado.text = if (completado)"Completado" else "Pendiente"


        //comprueba si el usuario no ha escrito ninguna descripcion
        txtDescripcion.text = if (descripcion.isNullOrBlank()){
            "Sin descripcion"

            // muestra si la descripcion es real
        } else {
            descripcion
        }

        btnEditar.setOnClickListener {

           val campoTitulo = EditText(this).apply {
               setText (txtTitulo.text.toString())
               hint = "Titulo del objetivo"
               setSingleLine(true)
           }
            val campoDescripcion = EditText(this).apply {
                setText (descripcion.orEmpty())
                hint = "Descripción"
                minLines = 2
            }
            val contenedor = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(48,16,48,0)
                addView (campoTitulo)
                addView(campoDescripcion)

            }

            AlertDialog.Builder(this)
                .setTitle("EditarObjetivo")
                .setView(contenedor)
                .setNegativeButton("Cancelar",null)
                .setPositiveButton("Guardar") {_,_ ->
                    val nuevoTitulo = campoTitulo.text.toString().trim()
                    val nuevaDescripcion = campoDescripcion.text.toString().trim()

                    if (nuevoTitulo.isNotEmpty() && objetivoId != -1){
                        val objetivoEditado = Objetivo(
                            id = objetivoId,
                            titulo = nuevoTitulo,
                            categoria = categoria.orEmpty(),
                            completado = completado,
                            descripcion = nuevaDescripcion
                        )
                        ObjetivoDAO(this).update(objetivoEditado)

                        txtTitulo.text =nuevoTitulo
                        txtDescripcion.text = nuevaDescripcion.ifEmpty{
                            "Sin descripción"
                        }

                    }

                }
                .show()
        }

        val btnVolver = findViewById<MaterialButton>(R.id.btnVolverObjetivos)

        val colorFondo = when (categoria){
            "Estudios" -> R.color.impulso_icono_estudios
            "Proyectos" -> R.color.impulso_icono_proyectos
            "Personal" -> R.color.impulso_icono_personal
            else -> R.color.impulso_texto_estudios
        }

        val colorTexto = when (categoria){
            "Estudios" -> R.color.impulso_texto_estudios
            "Proyectos" -> R.color.impulso_texto_proyectos
            "Personal" -> R.color.impulso_texto_personal
            else -> R.color.impulso_texto_estudios
        }
        val fondo = ContextCompat.getColor(this, colorFondo)
        val texto = ContextCompat.getColor(this, colorTexto)
        val txtMiImpulso = findViewById<TextView>(R.id.txtMiImpulso)
        txtMiImpulso.setTextColor(texto )
        val fondoEncabezado = findViewById<LinearLayout>(R.id.fondoEncabezado)
        fondoEncabezado.setBackgroundColor(fondo)


        txtTitulo.setTextColor(texto)
        txtCategoria.setTextColor(texto)
        txtDescripcion.setTextColor(texto)
        txtEncabezado.setTextColor(texto)

        btnEditar.setColorFilter(texto)

        btnVolver.backgroundTintList =
            ColorStateList.valueOf(fondo)
        btnVolver.setTextColor(texto)
        btnVolver.iconTint = ColorStateList.valueOf(texto)

        btnVolver.setOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}