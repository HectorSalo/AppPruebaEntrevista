package com.thefactoryhka.apppruebaentrevista.ui.nuevoRecibo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.thefactoryhka.apppruebaentrevista.R
import kotlinx.android.synthetic.main.fragment_cliente.*
import kotlinx.android.synthetic.main.fragment_producto.*

class ProductoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_producto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_anterior).setOnClickListener {
            findNavController().navigate(R.id.action_productoFragment_to_SecondFragment)
        }
        view.findViewById<Button>(R.id.button_siguiente).setOnClickListener {
            if (validarDatos()) {
                
            }
        }

    }

    private fun validarDatos() : Boolean {
        outlined_descripcion.error = null
        outlined_precio.error = null
        outlined_codigo.error = null

        if (et_descripcion.text.isNullOrEmpty()) {
            outlined_descripcion.error = "Debe ingresar la descripción"
            return false
        }
        if (et_codigo.text.isNullOrEmpty()) {
            outlined_codigo.error = "Debe ingresar el código"
            return false
        }
        if (et_precio.text.isNullOrEmpty()) {
            outlined_precio.error = "Debe ingresar el precio"
            return false
        }
        return true
    }
}