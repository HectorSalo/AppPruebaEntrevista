package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui

import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Cliente
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Emisor
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Producto
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Recibo

interface TotalView {
    fun infoRecibo(productos: List<Producto>, cliente: Cliente, emisor: Emisor, recibo: List<Recibo>)

    fun reciboExitoso()
}