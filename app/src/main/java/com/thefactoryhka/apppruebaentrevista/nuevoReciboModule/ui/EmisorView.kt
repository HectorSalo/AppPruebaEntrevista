package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui

interface EmisorView {
    fun emisorGuardado(razonSocial: String?, rif: Int?, identificador: Int?)

    fun guardarExitoso()
}