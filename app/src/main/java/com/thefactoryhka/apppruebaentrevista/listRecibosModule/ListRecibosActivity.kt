package com.thefactoryhka.apppruebaentrevista.listRecibosModule

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
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.ReciboDB
import com.thefactoryhka.apppruebaentrevista.common.model.ReciboModel
import kotlinx.android.synthetic.main.activity_list_recibos.*
import kotlinx.coroutines.launch

class ListRecibosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_recibos)

        onBackPressedDispatcher.addCallback(this) {
            finish()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var listRecibo: ArrayList<ReciboModel> = ArrayList()
        var viewManager = LinearLayoutManager(this)
        var viewAdapterListRecibos = AdapterListRecibos(listRecibo, this)
        rv_recibos.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapterListRecibos
            addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
        }

        val room = Room
            .databaseBuilder(this, ReciboDB::class.java, "recibo")
            .fallbackToDestructiveMigration()
            .build()

        lifecycleScope.launch {
            var recibos = room.reciboDao().getAll()
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
                rv_recibos.visibility = View.GONE
                tv_lista_vacia.visibility = View.VISIBLE
            }
        }
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
                    .setPositiveButton("Borrar") { dialogInterface, i ->
                        val room = Room
                            .databaseBuilder(this, ReciboDB::class.java, "recibo")
                            .fallbackToDestructiveMigration()
                            .build()

                        lifecycleScope.launch {
                            room.reciboDao().deleteAll()
                            rv_recibos.visibility = View.GONE
                            tv_lista_vacia.visibility = View.VISIBLE
                        }
                    }
                    .setNegativeButton("NO") { dialogInterface, i ->  }
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}