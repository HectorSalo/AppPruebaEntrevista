package com.thefactoryhka.apppruebaentrevista.ui.nuevoRecibo

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.thefactoryhka.apppruebaentrevista.R
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Producto
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.ReciboDB
import kotlinx.android.synthetic.main.fragment_producto.*
import kotlinx.coroutines.launch

class ProductoFragment : Fragment() {

    private lateinit var viewAdapterProducto: AdapterProducto
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var listProducto: ArrayList<ConstructorProducto>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_producto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_productoFragment_to_SecondFragment)
        }

        listProducto = ArrayList()
        viewManager = LinearLayoutManager(context)
        viewAdapterProducto = AdapterProducto(listProducto, context)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapterProducto
        }

        val room = Room
            .databaseBuilder(requireContext(), ReciboDB::class.java, "recibo")
            .fallbackToDestructiveMigration()
            .build()

        lifecycleScope.launch {
            var productos = room.productoDao().getAll()
            if (productos != null) {
                for (i in 0 until productos.size) {
                    val item = ConstructorProducto()
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
                    var resultado : Double = String.format("%.2f", subtotal).toDouble()
                    tv_subtotal.text = resultado.toString()
                }
            }
        }

        view.findViewById<Button>(R.id.button_anterior).setOnClickListener {
            findNavController().navigate(R.id.action_productoFragment_to_SecondFragment)
        }
        view.findViewById<Button>(R.id.button_totalizar).setOnClickListener {
            if (listProducto.isNotEmpty()) {
                findNavController().navigate(R.id.action_productoFragment_to_totalFragment)
            } else {
                Toast.makeText(context, "Debe ingresar al menos un producto para totalizar", Toast.LENGTH_LONG).show()
            }
        }

        imageButton_add_cantidad.setOnClickListener {
            var cantidad: Int = et_cantidad.text.toString().toInt()
            var cantidadNueva = cantidad + 1
            et_cantidad!!.setText(cantidadNueva.toString())
        }

        imageButton_restar_cantidad.setOnClickListener {
            var cantidad: Int = et_cantidad.text.toString().toInt()
            var cantidadNueva = cantidad - 1
            et_cantidad!!.setText(cantidadNueva.toString())
        }

        imageButton_add_producto.setOnClickListener {
            if (validarDatos()) {
                var productoBD = Producto(listProducto.size, et_descripcion.text.toString(), et_codigo.text.toString().toInt(), et_precio.text.toString().toDouble(), et_cantidad.text.toString().toInt())
                lifecycleScope.launch {
                    room.productoDao().insert(productoBD)
                }

                var item = ConstructorProducto()
                item.descripcion = et_descripcion.text.toString()
                item.codigo = et_codigo.text.toString().toInt()
                item.cantidad = et_cantidad.text.toString().toInt()
                item.precio = et_precio.text.toString().toDouble()
                var add = listProducto.add(item)
                if (add) {
                    viewAdapterProducto.updateList(listProducto)
                    var subtotal = 0.0
                    for (i in 0 until listProducto.size) {
                        subtotal += listProducto[i].precio * listProducto[i].cantidad
                        tv_subtotal.text = subtotal.toString()
                    }
                    et_cantidad!!.setText("1")
                    et_codigo.text?.clear()
                    et_descripcion.text?.clear()
                    et_precio.text?.clear()
                }
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
        if (et_cantidad.text.isNullOrEmpty() || (et_cantidad.text.toString().toInt() < 1) || (et_cantidad.text.toString().toInt() > 100)) {
            Toast.makeText(context, "Sólo está permitido ingresar desde 1 hasta 100 productos por Recibo", Toast.LENGTH_LONG).show()
            return false
        }
        if (listProducto.isNotEmpty()) {
            var cantidadUsada = 0
            for (i in 0 until listProducto.size) {
                cantidadUsada += listProducto[i].cantidad
            }
            var cantidadRestante = 100 - cantidadUsada
            if (et_cantidad.text.toString().toInt() > cantidadRestante) {
                Toast.makeText(context, "Sólo está permitido ingresar desde 1 hasta 100 productos por Recibo", Toast.LENGTH_LONG).show()
                return false
            }
        }
        return true
    }
}