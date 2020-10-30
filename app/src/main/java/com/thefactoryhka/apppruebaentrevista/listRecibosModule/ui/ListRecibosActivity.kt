package com.thefactoryhka.apppruebaentrevista.listRecibosModule.ui

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.thefactoryhka.apppruebaentrevista.R
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Recibo
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.ReciboDB
import com.thefactoryhka.apppruebaentrevista.common.model.ReciboModel
import com.thefactoryhka.apppruebaentrevista.databinding.ActivityListRecibosBinding
import com.thefactoryhka.apppruebaentrevista.listRecibosModule.presenter.ListReciboPresenterClass
import com.thefactoryhka.apppruebaentrevista.listRecibosModule.presenter.ListRecibosPresenter
import kotlinx.android.synthetic.main.activity_list_recibos.*
import kotlinx.coroutines.launch
import java.util.ArrayList

class ListRecibosActivity : AppCompatActivity(), ListaRecibosView {

    private lateinit var binding: ActivityListRecibosBinding
    private lateinit var listRecibo: ArrayList<ReciboModel>
    private lateinit var viewAdapterListRecibos: AdapterListRecibos
    private lateinit var listRecibosPresenter: ListRecibosPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListRecibosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listRecibosPresenter = ListReciboPresenterClass(this, this)

        onBackPressedDispatcher.addCallback(this) {
            finish()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        listRecibo = ArrayList()
        val viewManager = LinearLayoutManager(this)
        viewAdapterListRecibos = AdapterListRecibos(listRecibo, this)
        binding.rvRecibos.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapterListRecibos
            addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
        }

        listRecibosPresenter.onCreate()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_lista_recibo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_borrar_recibos -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("¿Desea borrar todos los Recibos?")
                    .setMessage("Esta acción no se puede deshacer")
                    .setPositiveButton("Borrar") { _, _ ->
                        listRecibosPresenter.eliminarLista()
                    }
                    .setNegativeButton("NO") { _, _ ->  }
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun listaRecibos(recibos: List<Recibo>) {
        if (recibos.isNotEmpty()) {
            for (i in recibos.indices) {
                val recibo = ReciboModel()
                recibo.emisor = recibos[i].emisor
                recibo.cliente = recibos[i].cliente
                recibo.cantidadItems = recibos[i].cantidadItems
                recibo.total = recibos[i].total

                listRecibo.add(recibo)
            }
            viewAdapterListRecibos.updateList(listRecibo)
        } else {
            binding.rvRecibos.visibility = View.GONE
            binding.tvListaVacia.visibility = View.VISIBLE
        }
    }

    override fun listaEliminada() {
        binding.rvRecibos.visibility = View.GONE
        binding.tvListaVacia.visibility = View.VISIBLE
    }
}