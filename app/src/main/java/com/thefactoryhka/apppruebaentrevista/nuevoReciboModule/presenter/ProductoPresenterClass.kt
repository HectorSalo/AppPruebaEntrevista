package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter

import android.content.Context
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Producto
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor.ProductoInteractor
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor.ProductoInteractorClass
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui.ProductoView

class ProductoPresenterClass(val context: Context, val productoView: ProductoView): ProductoPresenter {
    private val productoInteractor: ProductoInteractor = ProductoInteractorClass(context, this)

    override fun onCreate() {
        productoInteractor.onCreate()
    }

    override fun productosGuardados(productos: List<Producto>) {
        productoView.productosGuardados(productos)
    }

    override fun agregarProducto(
        position: Int,
        codigo: Int,
        descripcion: String,
        precio: Double,
        cantidad: Int
    ) {
        productoInteractor.agregarProducto(position, codigo, descripcion, precio, cantidad)
    }

    override fun productoAgregado() {
        productoView.productoAgregado()
    }
}