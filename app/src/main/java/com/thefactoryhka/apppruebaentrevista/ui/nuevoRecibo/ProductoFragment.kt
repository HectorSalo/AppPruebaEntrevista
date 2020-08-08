package com.thefactoryhka.apppruebaentrevista.ui.nuevoRecibo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thefactoryhka.apppruebaentrevista.R
import kotlinx.android.synthetic.main.fragment_producto.*

class ProductoFragment : Fragment() {

    private lateinit var viewAdapterProducto: AdapterProducto
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var listProducto: ArrayList<ConstructorRecibo>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_producto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listProducto = ArrayList()
        viewManager = LinearLayoutManager(context)
        viewAdapterProducto = AdapterProducto(listProducto, context)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapterProducto
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        view.findViewById<Button>(R.id.button_anterior).setOnClickListener {
            findNavController().navigate(R.id.action_productoFragment_to_SecondFragment)
        }
        view.findViewById<Button>(R.id.button_totalizar).setOnClickListener {

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
                var item = ConstructorRecibo()
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