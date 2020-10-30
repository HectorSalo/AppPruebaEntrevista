package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor

interface TotalInteractor {
    fun onCreate()

    fun cerrarRecibo(idRecibo: Int, cliente: String, emisor: String, total: Double, items: Int)
}