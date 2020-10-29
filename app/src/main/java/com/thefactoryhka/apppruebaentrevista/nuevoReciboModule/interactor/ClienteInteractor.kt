package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor

interface ClienteInteractor {
    fun onCreate()

    fun guardarCliente(nombre: String, apellido: String, cedula: Int)

    fun eliminarCliente()
}