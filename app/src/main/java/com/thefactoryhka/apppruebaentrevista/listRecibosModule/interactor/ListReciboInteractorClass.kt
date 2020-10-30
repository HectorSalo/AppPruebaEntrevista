package com.thefactoryhka.apppruebaentrevista.listRecibosModule.interactor

import android.content.Context
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.ReciboDB
import com.thefactoryhka.apppruebaentrevista.common.Constantes
import com.thefactoryhka.apppruebaentrevista.listRecibosModule.presenter.ListRecibosPresenter
import kotlinx.android.synthetic.main.activity_list_recibos.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListReciboInteractorClass(val context: Context, val listRecibosPresenter: ListRecibosPresenter): ListReciboInteractor, CoroutineScope {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate() {
        val room = Room
            .databaseBuilder(context, ReciboDB::class.java, Constantes.DB_RECIBO)
            .fallbackToDestructiveMigration()
            .build()

        launch {
            val recibos = room.reciboDao().getAll()
            listRecibosPresenter.listaRecibos(recibos)
        }
    }

    override fun eliminarLista() {
        val room = Room
            .databaseBuilder(context, ReciboDB::class.java, Constantes.DB_RECIBO)
            .fallbackToDestructiveMigration()
            .build()

        launch {
            room.reciboDao().deleteAll()
            listRecibosPresenter.listaEliminada()
        }
    }
}