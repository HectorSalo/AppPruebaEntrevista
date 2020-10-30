package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor

interface ProductoInteractor {
    fun onCreate()

    fun agregarProducto(position: Int, codigo: Int, descripcion: String, precio: Double, cantidad: Int)

}