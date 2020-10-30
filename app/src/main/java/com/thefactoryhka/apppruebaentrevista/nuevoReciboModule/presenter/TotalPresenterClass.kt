package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter

import android.content.Context
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Cliente
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Emisor
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Producto
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Recibo
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor.TotalInteractor
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor.TotalInteractorClass
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui.TotalView

class TotalPresenterClass(val context: Context, val totalView: TotalView): TotalPresenter {
    val totalInteractor: TotalInteractor = TotalInteractorClass(context, this)

    override fun onCreate() {
        totalInteractor.onCreate()
    }

    override fun infoRecibo(
        productos: List<Producto>,
        cliente: Cliente,
        emisor: Emisor,
        recibo: List<Recibo>
    ) {
        totalView.infoRecibo(productos, cliente, emisor, recibo)
    }

    override fun cerrarRecibo(
        idRecibo: Int,
        cliente: String,
        emisor: String,
        total: Double,
        items: Int
    ) {
        totalInteractor.cerrarRecibo(idRecibo, cliente, emisor, total, items)
    }

    override fun reciboExitoso() {
        totalView.reciboExitoso()
    }

}