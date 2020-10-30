package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter

import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Cliente
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Emisor
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Producto
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Recibo
import kotlinx.android.synthetic.main.fragment_total.*

interface TotalPresenter {
    fun onCreate()

    fun infoRecibo(productos: List<Producto>, cliente: Cliente, emisor: Emisor, recibo: List<Recibo>)

    fun cerrarRecibo(idRecibo: Int, cliente: String, emisor: String, total: Double, items: Int)

    fun reciboExitoso()
}
