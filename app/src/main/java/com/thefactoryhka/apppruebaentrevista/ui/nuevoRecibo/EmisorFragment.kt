package com.thefactoryhka.apppruebaentrevista.ui.nuevoRecibo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.thefactoryhka.apppruebaentrevista.R
import kotlinx.android.synthetic.main.fragment_cliente.*
import kotlinx.android.synthetic.main.fragment_emisor.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EmisorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emisor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ArrayAdapter.createFromResource(requireContext(), R.array.identificador, android.R.layout.simple_spinner_item).
        also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        view.findViewById<Button>(R.id.button_anterior).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        view.findViewById<Button>(R.id.button_siguiente).setOnClickListener {
            if (validarDatos()) {
                findNavController().navigate(R.id.action_SecondFragment_to_productoFragment)
            }
        }
    }

    private fun validarDatos() : Boolean {
        outlined_emisor.error = null
        outlined_rif.error = null

        if (et_emisor.text.isNullOrEmpty()) {
            outlined_emisor.error = "Debe ingresar el emisor"
            return false
        }
        if (et_rif.text.isNullOrEmpty()) {
            outlined_rif.error = "Debe ingresar el RIF"
            return false
        }
        return true
    }
}