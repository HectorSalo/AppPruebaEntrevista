package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui

interface ClienteView {
    fun clienteGuardado(nombre: String?, apellido: String?, cedula: Int?)

    fun guardarExitoso()

    fun eliminarExitoso()
}