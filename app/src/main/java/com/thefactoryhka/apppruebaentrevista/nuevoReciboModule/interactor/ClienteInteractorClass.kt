package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor

import android.content.Context
import androidx.room.Room
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Cliente
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.ReciboDB
import com.thefactoryhka.apppruebaentrevista.common.Constantes
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter.ClientePresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ClienteInteractorClass (val clientePresenter: ClientePresenter, val context: Context) : ClienteInteractor, CoroutineScope{

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate() {
        val room = Room
            .databaseBuilder(context, ReciboDB::class.java, Constantes.DB_RECIBO)
            .fallbackToDestructiveMigration()
            .build()

        launch {
            val cliente = room.clienteDao().getById(1)
            if (cliente != null) {
                clientePresenter.clienteGuardado(cliente.nombre, cliente.apellido, cliente.cedula)
            }
        }


    }

    override fun guardarCliente(nombre: String, apellido: String, cedula: Int) {
        val room = Room
            .databaseBuilder(context, ReciboDB::class.java, Constantes.DB_RECIBO)
            .fallbackToDestructiveMigration()
            .build()

        val cliente = Cliente(1, nombre, apellido, cedula)
        launch {
            room.clienteDao().deleteAll()
            room.clienteDao().insert(cliente)
            clientePresenter.guardarExitoso()
        }
    }

    override fun eliminarCliente() {
        val room = Room
            .databaseBuilder(context, ReciboDB::class.java, Constantes.DB_RECIBO)
            .fallbackToDestructiveMigration()
            .build()

        launch {
            room.clienteDao().deleteAll()
            room.emisorDao().deleteAll()
            room.productoDao().deleteAll()
            clientePresenter.eliminarExitoso()
        }
    }


}