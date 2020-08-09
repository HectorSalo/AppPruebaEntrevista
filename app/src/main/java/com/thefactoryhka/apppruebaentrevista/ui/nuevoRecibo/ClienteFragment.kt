package com.thefactoryhka.apppruebaentrevista.ui.nuevoRecibo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.thefactoryhka.apppruebaentrevista.R
import com.thefactoryhka.apppruebaentrevista.ui.baseDeDatos.Cliente
import com.thefactoryhka.apppruebaentrevista.ui.baseDeDatos.ReciboDB
import kotlinx.android.synthetic.main.fragment_cliente.*
import kotlinx.coroutines.launch

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

        val room = Room
            .databaseBuilder(requireContext(), ReciboDB::class.java, "recibo")
            .fallbackToDestructiveMigration()
            .build()

        lifecycleScope.launch {
            var cliente = room.clienteDao().getById(1)
            if (cliente != null) {
                et_nombre.setText(cliente.nombre)
                et_apellido.setText(cliente.apellido)
                et_cedula.setText(cliente.cedula.toString())
            }
        }

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            if (validarDatos()) {
                var cliente = Cliente(1, et_nombre.text.toString(), et_apellido.text.toString(), et_cedula.text.toString().toInt())
                lifecycleScope.launch {
                    room.clienteDao().deleteAll()
                    room.clienteDao().insert(cliente)
                }
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
        var cedula = et_cedula.text.toString().toInt()
        if(cedula < 1) {
            outlined_cedula.error = "La cédula no puede ser cero"
            return false
        }
        return true
    }

}