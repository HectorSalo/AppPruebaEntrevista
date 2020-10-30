package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.thefactoryhka.apppruebaentrevista.R
import com.thefactoryhka.apppruebaentrevista.baseDeDatos.*
import com.thefactoryhka.apppruebaentrevista.common.model.ProductoModel
import com.thefactoryhka.apppruebaentrevista.databinding.FragmentTotalBinding
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter.TotalPresenter
import com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.presenter.TotalPresenterClass
import kotlinx.android.synthetic.main.activity_nuevo_recibo.*
import kotlinx.android.synthetic.main.fragment_total.*
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.ArrayList


class TotalFragment : Fragment(), TotalView {

    private var _binding : FragmentTotalBinding? = null
    private val binding get() = _binding!!
    private var cantidadItems: Int = 0
    private var idRecibo: Int = 1
    private var nombreCliente: String = ""
    private var total: Double = 0.0
    private lateinit var listProducto: ArrayList<ProductoModel>
    private lateinit var viewAdapterProducto: AdapterProducto
    private lateinit var totalPresenter: TotalPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTotalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        totalPresenter = TotalPresenterClass(requireContext(), this)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            cerrarRecibo()
        }

        requireActivity().toolbar.title = "Recibo de Venta Generado"

        listProducto = ArrayList()
        val viewManager = LinearLayoutManager(context)
        viewAdapterProducto = AdapterProducto(listProducto, context)
        binding.recyclerViewTotal.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapterProducto
        }

        totalPresenter.onCreate()

        Handler().postDelayed({
            binding.lottieAnimationView.visibility = View.GONE
            binding.tvEmisor.visibility = View.VISIBLE
            binding.tvRif.visibility = View.VISIBLE
            binding.tvCliente.visibility = View.VISIBLE
            binding.tvCedula.visibility = View.VISIBLE
            binding.tvTitle.visibility = View.VISIBLE
            binding.tvTotal.visibility = View.VISIBLE
            binding.tvTotalMonto.visibility = View.VISIBLE
            binding.buttonFinalizar.visibility = View.VISIBLE
            binding.recyclerViewTotal.visibility = View.VISIBLE
        }, 3000)

        binding.buttonFinalizar.setOnClickListener { cerrarRecibo() }
    }

    private fun cerrarRecibo() {
        totalPresenter.cerrarRecibo(idRecibo, nombreCliente, binding.tvEmisor.text.toString(),
        total, cantidadItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun infoRecibo(
        productos: List<Producto>,
        cliente: Cliente,
        emisor: Emisor,
        recibo: List<Recibo>
    ) {
        val identificador = resources.getStringArray(R.array.identificador)
        nombreCliente = "${cliente.nombre} ${cliente.apellido}"
        binding.tvEmisor.text = emisor.razonSocial
        binding.tvRif.text = "${identificador[emisor.identificador]}${emisor.rif}"
        binding.tvCliente.text = "Cliente: $nombreCliente"
        binding.tvCedula.text = "C.I.: ${cliente.cedula}"

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
        val dec = DecimalFormat("#,###.##")
        for (i in 0 until listProducto.size) {
            cantidadItems += listProducto[i].cantidad
            subtotal += listProducto[i].precio * listProducto[i].cantidad
            total = subtotal
            binding.tvTotalMonto.text = dec.format(subtotal)
        }

        if (recibo.isNotEmpty()) {
            idRecibo = recibo.size + 1
        }
    }

    override fun reciboExitoso() {
        requireActivity().finish()
    }
}