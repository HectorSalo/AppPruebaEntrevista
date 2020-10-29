package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter

interface ClientePresenter {

    fun onCreate()

    fun clienteGuardado(nombre: String?, apellido: String?, cedula: Int?)

    fun guardarCliente(nombre: String, apellido: String, cedula: Int)

    fun guardarExitoso()

    fun eliminarCliente()

    fun eliminarExitoso()
}