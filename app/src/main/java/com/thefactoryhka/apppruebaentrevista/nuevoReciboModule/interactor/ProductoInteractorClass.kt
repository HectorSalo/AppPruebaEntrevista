package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor

import android.content.Context
import androidx.room.Room
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Producto
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.ReciboDB
import com.thefactoryhka.apppruebaentrevista.common.Constantes
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter.ProductoPresenter
import kotlinx.android.synthetic.main.fragment_producto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProductoInteractorClass(val context: Context, val productoPresenter: ProductoPresenter): ProductoInteractor, CoroutineScope {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate() {
        val room = Room
            .databaseBuilder(context, ReciboDB::class.java, Constantes.DB_RECIBO)
            .fallbackToDestructiveMigration()
            .build()

        launch {
            var productos = room.productoDao().getAll()
            if (productos != null) {
                productoPresenter.productosGuardados(productos)
            }
        }

    }

    override fun agregarProducto(
        position: Int,
        codigo: Int,
        descripcion: String,
        precio: Double,
        cantidad: Int
    ) {
        val room = Room
            .databaseBuilder(context, ReciboDB::class.java, Constantes.DB_RECIBO)
            .fallbackToDestructiveMigration()
            .build()

        launch {
            val productoBD = Producto(position, descripcion, codigo, precio, cantidad)
            room.productoDao().insert(productoBD)
            productoPresenter.productoAgregado()
        }
    }
}