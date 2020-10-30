package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter

interface EmisorPresenter {
    fun onCreate()

    fun emisorGuardado(razonSocial: String?, rif: Int?, identificador: Int?)

    fun guardarEmisor(razonSocial: String, rif: Int, identificador: Int)

    fun guardarExitoso()
}