package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor

import android.content.Context
import androidx.room.Room
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Emisor
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.ReciboDB
import com.thefactoryhka.apppruebaentrevista.common.Constantes
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter.EmisorPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class EmisorInteractorClass(val emisorPresenter: EmisorPresenter, val context: Context): EmisorInteractor, CoroutineScope {

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate() {
        val room = Room
            .databaseBuilder(context, ReciboDB::class.java, Constantes.DB_RECIBO)
            .fallbackToDestructiveMigration()
            .build()

        launch {
            var emisor = room.emisorDao().getById(1)
            if (emisor != null) {
                emisorPresenter.emisorGuardado(emisor.razonSocial, emisor.rif, emisor.identificador)
            }
        }
    }

    override fun guardarEmisor(razonSocial: String, rif: Int, identificador: Int) {
        val room = Room
            .databaseBuilder(context, ReciboDB::class.java, Constantes.DB_RECIBO)
            .fallbackToDestructiveMigration()
            .build()

        val emisor = Emisor(1, razonSocial, rif, identificador)
        launch {
            room.emisorDao().deleteAll()
            room.emisorDao().insert(emisor)
            emisorPresenter.guardarExitoso()
        }
    }
}