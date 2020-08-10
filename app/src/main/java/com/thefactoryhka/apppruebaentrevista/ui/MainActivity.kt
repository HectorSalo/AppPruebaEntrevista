package com.thefactoryhka.apppruebaentrevista.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thefactoryhka.apppruebaentrevista.R
import com.thefactoryhka.apppruebaentrevista.ui.listRecibos.ListRecibosActivity
import com.thefactoryhka.apppruebaentrevista.ui.nuevoRecibo.NuevoReciboActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_nuevo_recibo.setOnClickListener { startActivity(Intent(this, NuevoReciboActivity::class.java)) }

        button_lista_recibo.setOnClickListener { startActivity(Intent(this, ListRecibosActivity::class.java)) }

    }

    override fun onBackPressed() {
    }
}