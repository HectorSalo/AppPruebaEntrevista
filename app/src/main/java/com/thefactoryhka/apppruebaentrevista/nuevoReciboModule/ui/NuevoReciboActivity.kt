package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thefactoryhka.apppruebaentrevista.R

class NuevoReciboActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_recibo)
        setSupportActionBar(findViewById(R.id.toolbar))

    }
}