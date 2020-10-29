package com.thefactoryhka.apppruebaentrevista.homeModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thefactoryhka.apppruebaentrevista.databinding.ActivityMainBinding
import com.thefactoryhka.apppruebaentrevista.listRecibosModule.ListRecibosActivity
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui.NuevoReciboActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNuevoRecibo.setOnClickListener { startActivity(Intent(this, NuevoReciboActivity::class.java)) }

        binding.buttonListaRecibo.setOnClickListener { startActivity(Intent(this, ListRecibosActivity::class.java)) }

    }

    override fun onBackPressed() {
        finishAffinity()
    }
}