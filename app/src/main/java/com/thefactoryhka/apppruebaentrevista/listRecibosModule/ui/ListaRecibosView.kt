package com.thefactoryhka.apppruebaentrevista.listRecibosModule.ui

import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Recibo

interface ListaRecibosView {
    fun listaRecibos(recibos: List<Recibo>)

    fun listaEliminada()
}