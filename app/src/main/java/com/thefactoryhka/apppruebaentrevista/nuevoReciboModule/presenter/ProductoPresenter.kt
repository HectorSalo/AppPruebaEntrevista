package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter

import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Producto
import kotlinx.android.synthetic.main.fragment_producto.*

interface ProductoPresenter {
    fun onCreate()

    fun productosGuardados(productos: List<Producto>)

    fun agregarProducto(position: Int, codigo: Int, descripcion: String, precio: Double, cantidad: Int)

    fun productoAgregado()
}
