package com.thefactoryhka.apppruebaentrevista.listRecibosModule.presenter

import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Recibo

interface ListRecibosPresenter {
    fun onCreate()

    fun listaRecibos(recibos: List<Recibo>)

    fun eliminarLista()

    fun listaEliminada()
}