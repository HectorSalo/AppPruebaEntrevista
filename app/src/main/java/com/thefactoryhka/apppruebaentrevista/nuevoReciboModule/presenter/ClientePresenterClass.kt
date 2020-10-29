package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter

import android.content.Context
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor.ClienteInteractor
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor.ClienteInteractorClass
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui.ClienteView

class ClientePresenterClass(val context: Context, val clienteView: ClienteView): ClientePresenter {
    private val clienteInteractor: ClienteInteractor = ClienteInteractorClass(this, context)

    override fun onCreate() {
        clienteInteractor.onCreate()
    }

    override fun clienteGuardado(nombre: String?, apellido: String?, cedula: Int?) {
        clienteView.clienteGuardado(nombre, apellido, cedula)
    }


    override fun guardarCliente(nombre: String, apellido: String, cedula: Int) {
        clienteInteractor.guardarCliente(nombre, apellido, cedula)
    }

    override fun guardarExitoso() {
        clienteView.guardarExitoso()
    }

    override fun eliminarCliente() {
        clienteInteractor.eliminarCliente()
    }

    override fun eliminarExitoso() {
        clienteView.eliminarExitoso()
    }
}