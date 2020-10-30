package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.thefactoryhka.apppruebaentrevista.R
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Producto
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.ReciboDB
import com.thefactoryhka.apppruebaentrevista.common.model.ProductoModel
import com.thefactoryhka.apppruebaentrevista.databinding.FragmentProductoBinding
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter.ProductoPresenter
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter.ProductoPresenterClass
import kotlinx.android.synthetic.main.fragment_producto.*
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class ProductoFragment : Fragment(), ProductoView {

    private var _binding: FragmentProductoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewAdapterProducto: AdapterProducto
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var listProducto: ArrayList<ProductoModel>
    private lateinit var productoPresenter: ProductoPresenter
    val dec = DecimalFormat("#,###.##")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productoPresenter = ProductoPresenterClass(requireContext(), this)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_productoFragment_to_SecondFragment)
        }

        listProducto = ArrayList()
        viewManager = LinearLayoutManager(context)
        viewAdapterProducto = AdapterProducto(listProducto, context)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapterProducto
        }

        productoPresenter.onCreate()

        binding.buttonAnterior.setOnClickListener {
            findNavController().navigate(R.id.action_productoFragment_to_SecondFragment)
        }
        binding.buttonTotalizar.setOnClickListener {
            if (listProducto.isNotEmpty()) {
                findNavController().navigate(R.id.action_productoFragment_to_totalFragment)
            } else {
                Toast.makeText(context, "Debe ingresar al menos un producto para totalizar", Toast.LENGTH_LONG).show()
            }
        }

        binding.imageButtonAddCantidad.setOnClickListener {
            val cantidad: Int = binding.etCantidad.text.toString().toInt()
            val cantidadNueva = cantidad + 1
            binding.etCantidad.setText(cantidadNueva.toString())
        }

        binding.imageButtonRestarCantidad.setOnClickListener {
            val cantidad: Int = binding.etCantidad.text.toString().toInt()
            val cantidadNueva = cantidad - 1
            binding.etCantidad.setText(cantidadNueva.toString())
        }

        binding.imageButtonAddProducto.setOnClickListener {
            if (validarDatos()) {
                val item = ProductoModel()
                item.descripcion = binding.etDescripcion.text.toString()
                item.codigo = binding.etCodigo.text.toString().toInt()
                item.cantidad = binding.etCantidad.text.toString().toInt()
                item.precio = binding.etPrecio.text.toString().toDouble()
                val add = listProducto.add(item)
                if (add) {
                    productoPresenter.agregarProducto(listProducto.size, binding.etCodigo.text.toString().toInt(),
                    binding.etDescripcion.text.toString(), binding.etPrecio.text.toString().toDouble(),
                    binding.etCantidad.text.toString().toInt())
                }
            }
        }

    }

    private fun validarDatos() : Boolean {
        binding.outlinedDescripcion.error = null
        binding.outlinedPrecio.error = null
        binding.outlinedCodigo.error = null

        if (binding.etDescripcion.text.isNullOrEmpty()) {
            binding.outlinedDescripcion.error = "Debe ingresar la descripción"
            return false
        }
        if (binding.etCodigo.text.isNullOrEmpty()) {
            binding.outlinedCodigo.error = "Debe ingresar el código"
            return false
        }
        if (binding.etPrecio.text.isNullOrEmpty()) {
            binding.outlinedPrecio.error = "Debe ingresar el precio"
            return false
        }
        if (binding.etCantidad.text.isNullOrEmpty() || (binding.etCantidad.text.toString().toInt() < 1)
            || (binding.etCantidad.text.toString().toInt() > 100)) {
            Toast.makeText(context, "Sólo está permitido ingresar desde 1 hasta 100 productos por Recibo", Toast.LENGTH_LONG).show()
            return false
        }
        if (listProducto.isNotEmpty()) {
            var cantidadUsada = 0
            for (i in 0 until listProducto.size) {
                cantidadUsada += listProducto[i].cantidad
            }
            val cantidadRestante = 100 - cantidadUsada
            if (binding.etCantidad.text.toString().toInt() > cantidadRestante) {
                Toast.makeText(context, "Sólo está permitido ingresar desde 1 hasta 100 productos por Recibo", Toast.LENGTH_LONG).show()
                return false
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun productosGuardados(productos: List<Producto>) {
        for (i in productos.indices) {
            val item = ProductoModel()
            item.descripcion = productos[i].descripcion
            item.precio = productos[i].precio
            item.cantidad = productos[i].cantidad
            item.codigo = productos[i].codigo

            listProducto.add(item)
        }
        viewAdapterProducto.updateList(listProducto)

        var subtotal = 0.0
        for (i in 0 until listProducto.size) {
            subtotal += listProducto[i].precio * listProducto[i].cantidad
            binding.tvSubtotal.text = dec.format(subtotal)
        }
    }

    override fun productoAgregado() {
        viewAdapterProducto.updateList(listProducto)

        var subtotal = 0.0
        for (i in 0 until listProducto.size) {
            subtotal += listProducto[i].precio * listProducto[i].cantidad
            binding.tvSubtotal.text = dec.format(subtotal)
        }

        binding.etCantidad.setText("1")
        binding.etCodigo.text!!.clear()
        binding.etDescripcion.text!!.clear()
        binding.etPrecio.text!!.clear()
    }
}