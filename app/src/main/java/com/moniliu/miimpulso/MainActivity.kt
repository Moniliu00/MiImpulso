package com.moniliu.miimpulso

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

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