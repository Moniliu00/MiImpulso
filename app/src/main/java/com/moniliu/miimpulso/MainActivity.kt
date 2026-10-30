package com.moniliu.miimpulso

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.moniliu.miimpulso.utils.SessionManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        // crea gestor de preferencias
        val sessionManager = SessionManager(this)

        // busca si ya tenemos un nombre guardado
        val nombreGuardado = sessionManager.obtenerNombre()

        val txtSaludo = findViewById<TextView>(R.id.txtSaludo)
        if (nombreGuardado.isNotEmpty()){
            txtSaludo.text = "¡Hola, $nombreGuardado!"
        }

        // comprueba si todabia no tiene un nombre

        if (nombreGuardado.isEmpty()){

            val campoNombre = EditText(this)
            campoNombre.hint = "Escribe tu nombre"

            val dialogo = AlertDialog.Builder(this)
                .setTitle("¡Bienvenido a Mi Impulso!")
                .setMessage("¿Como te llamas?")
                .setView(campoNombre)
                .setPositiveButton("Guardar", null)
                .create()

            dialogo.setOnShowListener {
                dialogo.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setOnClickListener {
                        val nombre = campoNombre.text.toString().trim()

                        if (nombre.isEmpty()){
                            campoNombre.error = "Escribe tu nombre"
                        } else{
                            sessionManager.guardarNombre(nombre)
                            txtSaludo.text = "Hola, $nombre!"
                            dialogo.dismiss()
                        }
                    }
            }

           dialogo.show()
        }

        val btnEstudios = findViewById<Button>(R.id.btnEstudios)
        btnEstudios.setOnClickListener {
            val intent = Intent(this, ObjetivosActivity::class.java)
            intent.putExtra("categoria", "Estudios")
            startActivity(intent)
        }

        val btnProyectos = findViewById<Button>(R.id.btnProtectos)
        btnProyectos.setOnClickListener {
            val intent= Intent (this, ObjetivosActivity::class.java)
            intent.putExtra("categoria", "Proyectos")
            startActivity(intent)
            }

        val btnPersonal = findViewById<Button>(R.id.btnPersonal)
        btnPersonal.setOnClickListener {
            val intent = Intent(this, ObjetivosActivity::class.java)
            intent.putExtra("categoria", "Personal")
            startActivity(intent)
            }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}