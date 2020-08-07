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

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ClienteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cliente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            if (validarDatos()) {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }
    }

    private fun validarDatos() : Boolean {
        outlined_nombre.error = null
        outlined_apellido.error = null
        outlined_cedula.error = null

        if (et_nombre.text.isNullOrEmpty()) {
            outlined_nombre.error = "Debe ingresar un nombre"
            return false
        }
        if (et_apellido.text.isNullOrEmpty()) {
            outlined_apellido.error = "Debe ingresar un apellido"
            return false
        }
        if (et_cedula.text.isNullOrEmpty()) {
            outlined_cedula.error = "Debe ingresar la cédula"
            return false
        }
        return true
    }
}