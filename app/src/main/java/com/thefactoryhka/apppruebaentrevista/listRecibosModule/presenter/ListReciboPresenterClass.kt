package com.thefactoryhka.apppruebaentrevista.listRecibosModule.presenter

import android.content.Context
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Recibo
import com.thefactoryhka.apppruebaentrevista.listRecibosModule.interactor.ListReciboInteractor
import com.thefactoryhka.apppruebaentrevista.listRecibosModule.interactor.ListReciboInteractorClass
import com.thefactoryhka.apppruebaentrevista.listRecibosModule.ui.ListaRecibosView

class ListReciboPresenterClass(val context: Context, val listaRecibosView: ListaRecibosView): ListRecibosPresenter {
    private val listReciboInteractor: ListReciboInteractor = ListReciboInteractorClass(context, this)

    override fun onCreate() {
        listReciboInteractor.onCreate()
    }

    override fun listaRecibos(recibos: List<Recibo>) {
        listaRecibosView.listaRecibos(recibos)
    }

    override fun eliminarLista() {
        listReciboInteractor.eliminarLista()
    }

    override fun listaEliminada() {
        listaRecibosView.listaEliminada()
    }
}