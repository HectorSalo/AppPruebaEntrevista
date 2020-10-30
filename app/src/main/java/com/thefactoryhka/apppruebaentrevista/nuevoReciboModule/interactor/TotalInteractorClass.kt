package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor

import android.content.Context
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Recibo
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.ReciboDB
import com.thefactoryhka.apppruebaentrevista.common.Constantes
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter.TotalPresenter
import kotlinx.android.synthetic.main.fragment_total.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TotalInteractorClass(val context: Context, val totalPresenter: TotalPresenter): TotalInteractor, CoroutineScope {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate() {
        val room = Room
            .databaseBuilder(context, ReciboDB::class.java, Constantes.DB_RECIBO)
            .fallbackToDestructiveMigration()
            .build()

        launch {
            val productos = room.productoDao().getAll()
            val cliente =  room.clienteDao().getById(1)
            val emisor = room.emisorDao().getById(1)
            val recibo = room.reciboDao().getAll()

            totalPresenter.infoRecibo(productos, cliente, emisor, recibo)
        }
    }

    override fun cerrarRecibo(
        idRecibo: Int,
        cliente: String,
        emisor: String,
        total: Double,
        items: Int
    ) {
        val room = Room
            .databaseBuilder(context, ReciboDB::class.java, Constantes.DB_RECIBO)
            .fallbackToDestructiveMigration()
            .build()

        val recibo = Recibo(idRecibo, cliente, emisor, total, items)
        launch {
            room.reciboDao().insert(recibo)
            room.productoDao().deleteAll()
            room.clienteDao().deleteAll()
            room.emisorDao().deleteAll()

            totalPresenter.reciboExitoso()
        }
    }
}