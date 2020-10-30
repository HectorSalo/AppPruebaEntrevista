package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui

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
import com.thefactoryhka.apppruebaentrevista.databinding.FragmentEmisorBinding
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter.EmisorPresenter
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter.EmisorPresenterClass
import kotlinx.android.synthetic.main.fragment_emisor.*
import kotlinx.coroutines.launch


class EmisorFragment : Fragment(), EmisorView {

    private var _binding: FragmentEmisorBinding? = null
    private val binding get() = _binding!!
    private lateinit var emisorPresenter: EmisorPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmisorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emisorPresenter = EmisorPresenterClass(requireContext(), this)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        ArrayAdapter.createFromResource(requireContext(), R.array.identificador, android.R.layout.simple_spinner_item).
        also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }

        emisorPresenter.onCreate()

        binding.buttonAnterior.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.buttonSiguiente.setOnClickListener {
            if (validarDatos()) {
                emisorPresenter.guardarEmisor(binding.etEmisor.text.toString(), binding.etRif.text.toString().toInt(),
                binding.spinner.selectedItemPosition)
            }
        }
    }

    private fun validarDatos() : Boolean {
        binding.outlinedEmisor.error = null
        binding.outlinedRif.error = null

        if (binding.etEmisor.text.isNullOrEmpty()) {
            binding.outlinedEmisor.error = "Debe ingresar el emisor"
            return false
        }
        if (binding.etRif.text.isNullOrEmpty()) {
            binding.outlinedRif.error = "Debe ingresar el RIF"
            return false
        }
        val rif = binding.etRif.text.toString().toInt()
        if (rif < 1) {
            binding.outlinedRif.error = "El RIF no puede ser cero"
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun emisorGuardado(razonSocial: String?, rif: Int?, identificador: Int?) {
        binding.etEmisor.setText(razonSocial)
        binding.etRif.setText(rif.toString())
        binding.spinner.setSelection(identificador!!)
    }

    override fun guardarExitoso() {
        findNavController().navigate(R.id.action_SecondFragment_to_productoFragment)
    }
}