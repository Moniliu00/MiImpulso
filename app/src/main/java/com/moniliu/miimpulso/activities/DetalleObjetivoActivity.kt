package com.moniliu.miimpulso.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.moniliu.miimpulso.R

class DetalleObjetivoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_objetivo)

        //recupera un texto enviado desde otra pantalla

        val titulo = intent.getStringExtra("titulo")
        val categoria = intent.getStringExtra("categoria")
        val descripcion = intent.getStringExtra("descripcion")


        //recupera un valor verdadero o falso
        val completado = intent.getBooleanExtra("completado", false)

        val txtTitulo = findViewById<TextView>(R.id.txtDetalleTitulo)
        val txtCategoria = findViewById<TextView>(R.id.txtDetalleCategoria)
        val txtEstado = findViewById<TextView>(R.id.txtDetalleEstado)
        val txtDescripcion = findViewById<TextView>(R.id.txtDetalleDescripcion)

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

        val btnVolver = findViewById<Button>(R.id.btnVolverObjetivos)
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