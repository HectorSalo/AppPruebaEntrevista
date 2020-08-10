package com.thefactoryhka.apppruebaentrevista.ui.nuevoRecibo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.thefactoryhka.apppruebaentrevista.R
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Emisor
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.ReciboDB
import kotlinx.android.synthetic.main.fragment_emisor.*
import kotlinx.coroutines.launch

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

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        val room = Room
            .databaseBuilder(requireContext(), ReciboDB::class.java, "recibo")
            .fallbackToDestructiveMigration()
            .build()

        lifecycleScope.launch {
            var emisor = room.emisorDao().getById(1)
            if (emisor != null) {
                et_emisor.setText(emisor.razonSocial)
                et_rif.setText(emisor.rif.toString())
                spinner.setSelection(emisor.identificador)
            }
        }

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
                var emisor = Emisor(1, et_emisor.text.toString(), et_rif.text.toString().toInt(), spinner.selectedItemPosition)
                lifecycleScope.launch {
                    room.emisorDao().deleteAll()
                    room.emisorDao().insert(emisor)
                }
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
        var rif = et_rif.text.toString().toInt()
        if (rif < 1) {
            outlined_rif.error = "El RIF no puede ser cero"
            return false
        }
        return true
    }
}