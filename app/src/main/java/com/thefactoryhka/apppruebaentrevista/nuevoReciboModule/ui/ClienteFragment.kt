package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.thefactoryhka.apppruebaentrevista.R
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Cliente
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.ReciboDB
import com.thefactoryhka.apppruebaentrevista.common.Constantes
import com.thefactoryhka.apppruebaentrevista.databinding.FragmentClienteBinding
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter.ClientePresenter
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter.ClientePresenterClass
import kotlinx.android.synthetic.main.fragment_cliente.*
import kotlinx.android.synthetic.main.fragment_cliente.view.*
import kotlinx.coroutines.launch


class ClienteFragment : Fragment(), ClienteView {

    private var _binding: FragmentClienteBinding? = null
    private val binding get() = _binding!!
    private lateinit var clientePresenter: ClientePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentClienteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clientePresenter = ClientePresenterClass(requireContext(), this)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            confirmarSalir()
        }

        clientePresenter.onCreate()

        binding.btnSiguiente.setOnClickListener {
            if (validarDatos()) {
                clientePresenter.guardarCliente(binding.etNombre.text.toString(), binding.etApellido.text.toString(),
                binding.etCedula.text.toString().toInt())
            }
        }

        binding.btnSalir.setOnClickListener {
            confirmarSalir()
        }
    }

    private fun validarDatos() : Boolean {
        binding.tfNombre.error = null
        binding.tfApellido.error = null
        binding.tfCedula.error = null

        if (binding.etNombre.text.isNullOrEmpty()) {
            binding.tfNombre.error = "Debe ingresar un nombre"
            return false
        }
        if (binding.etApellido.text.isNullOrEmpty()) {
            binding.tfApellido.error = "Debe ingresar un apellido"
            return false
        }
        if (binding.etCedula.text.isNullOrEmpty()) {
            binding.tfCedula.error = "Debe ingresar la cédula"
            return false
        }
        val cedula = binding.etCedula.text.toString().toInt()
        if(cedula < 1) {
            binding.tfCedula.error = "La cédula no puede ser cero"
            return false
        }
        return true
    }

    private fun confirmarSalir() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("¿Desea salir?")
            .setMessage("Se perderán los datos ingresados. Esta acción no se puede deshacer")
            .setPositiveButton(R.string.salir) { _, _ ->
                clientePresenter.eliminarCliente()
            }
            .setNegativeButton("NO") { _, _ ->  }
            .show()
    }

    override fun clienteGuardado(nombre: String?, apellido: String?, cedula: Int?) {
        if (!nombre.isNullOrEmpty() && !apellido.isNullOrBlank()) {
            binding.etNombre.setText(nombre)
            binding.etApellido.setText(apellido)
            binding.etCedula.setText(cedula.toString())
        }
    }

    override fun guardarExitoso() {
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun eliminarExitoso() {
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}