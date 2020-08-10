package com.thefactoryhka.apppruebaentrevista.ui.nuevoRecibo

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.thefactoryhka.apppruebaentrevista.R
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.Recibo
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.ReciboDB
import kotlinx.android.synthetic.main.activity_nuevo_recibo.*
import kotlinx.android.synthetic.main.fragment_total.*
import kotlinx.coroutines.launch
import java.text.DecimalFormat


class TotalFragment : Fragment() {

    var cantidadItems: Int = 0
    var idRecibo: Int = 1
    var nombreCliente: String = ""
    var total: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_total, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            cerrarRecibo()
        }

        requireActivity().toolbar.title = "Recibo de Venta Generado"

        var identificador = resources.getStringArray(R.array.identificador)

        var listProducto: ArrayList<ConstructorProducto> = ArrayList()
        var viewManager = LinearLayoutManager(context)
        var viewAdapterProducto = AdapterProducto(listProducto, context)
        recyclerView_total.apply {
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
            var cliente =  room.clienteDao().getById(1)
            var emisor = room.emisorDao().getById(1)
            var recibo = room.reciboDao().getAll()

            nombreCliente = "${cliente.nombre} ${cliente.apellido}"
            tv_emisor.text = emisor.razonSocial
            tv_rif.text = "${identificador[emisor.identificador]}${emisor.rif}"
            tv_cliente.text = "Cliente: $nombreCliente"
            tv_cedula.text = "C.I.: ${cliente.cedula}"

            for (i in productos.indices) {
                val item = ConstructorProducto()
                item.descripcion = productos[i].descripcion
                item.precio = productos[i].precio
                item.cantidad = productos[i].cantidad
                item.codigo = productos[i].codigo

                listProducto.add(item)
            }
            viewAdapterProducto.updateList(listProducto)
            var subtotal = 0.0
            val dec = DecimalFormat("#,###.##")
            for (i in 0 until listProducto.size) {
                cantidadItems += listProducto[i].cantidad
                subtotal += listProducto[i].precio * listProducto[i].cantidad
                total = subtotal
                tv_total_monto.text = dec.format(subtotal)
            }

            if (recibo.isNotEmpty()) {
                idRecibo = recibo.size + 1
            }
        }

        Handler().postDelayed({
            lottieAnimationView.visibility = View.GONE
            tv_emisor.visibility = View.VISIBLE
            tv_rif.visibility = View.VISIBLE
            tv_cliente.visibility = View.VISIBLE
            tv_cedula.visibility = View.VISIBLE
            tv_title.visibility = View.VISIBLE
            tv_total.visibility = View.VISIBLE
            tv_total_monto.visibility = View.VISIBLE
            button_finalizar.visibility = View.VISIBLE
            recyclerView_total.visibility = View.VISIBLE
        }, 3000)

        button_finalizar.setOnClickListener { cerrarRecibo() }
    }

    private fun cerrarRecibo() {
        val room = Room
            .databaseBuilder(requireContext(), ReciboDB::class.java, "recibo")
            .fallbackToDestructiveMigration()
            .build()

        var resumenRecibo = Recibo(idRecibo, nombreCliente, tv_emisor.text.toString(), total, cantidadItems)

        lifecycleScope.launch {
            room.reciboDao().insert(resumenRecibo)
            room.productoDao().deleteAll()
            room.clienteDao().deleteAll()
            room.emisorDao().deleteAll()
            requireActivity().finish()
        }
    }
}