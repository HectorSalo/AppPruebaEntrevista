package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter

import android.content.Context
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor.EmisorInteractor
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.interactor.EmisorInteractorClass
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui.EmisorView

class EmisorPresenterClass(val context: Context, val emisorView: EmisorView): EmisorPresenter {
    private val emisorInteractor: EmisorInteractor = EmisorInteractorClass(this, context)

    override fun onCreate() {
        emisorInteractor.onCreate()
    }

    override fun emisorGuardado(razonSocial: String?, rif: Int?, identificador: Int?) {
        emisorView.emisorGuardado(razonSocial, rif, identificador)
    }

    override fun guardarEmisor(razonSocial: String, rif: Int, identificador: Int) {
        emisorInteractor.guardarEmisor(razonSocial, rif, identificador)
    }

    override fun guardarExitoso() {
        emisorView.guardarExitoso()
    }
}