package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor

interface EmisorInteractor {
    fun onCreate()

    fun guardarEmisor(razonSocial: String, rif: Int, identificador: Int)
}