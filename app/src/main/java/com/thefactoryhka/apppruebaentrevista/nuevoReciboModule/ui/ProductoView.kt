package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui

import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Producto

interface ProductoView {

    fun productosGuardados(productos: List<Producto>)

    fun productoAgregado()
}