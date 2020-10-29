package com.thefactoryhka.apppruebaentrevista.nuevoReciboModule.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thefactoryhka.apppruebaentrevista.R
import com.thefactoryhka.apppruebaentrevista.common.model.ProductoModel
import kotlinx.android.synthetic.main.item_producto.view.*
import java.text.DecimalFormat

class AdapterProducto (private var listProduct: ArrayList<ProductoModel>, private val mctx: Context?) :
    RecyclerView.Adapter<AdapterProducto.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mctx).inflate(R.layout.item_producto, parent, false) as View

        return ViewHolder(view)
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val tvCodigo: TextView = view.tv_codigo
        val tvPrecioCantidad: TextView = view.tv_cantidad_precio
        val tvDescripcion: TextView = view.tv_descripcion
        val tvTotal: TextView = view.tv_total_item
    }

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dec = DecimalFormat("#,###.##")
        var codigo: String = listProduct[position].codigo.toString()
        var precio: String = listProduct[position].precio.toString()
        var cantidad: String = listProduct[position].cantidad.toString()
        var total: Double = listProduct[position].precio * listProduct[position].cantidad
        holder.tvCodigo.text = "Código: $codigo"
        holder.tvPrecioCantidad.text = "$cantidad x $precio"
        holder.tvDescripcion.text = listProduct[position].descripcion
        holder.tvTotal.text = dec.format(total)
    }

    fun updateList (newList: ArrayList<ProductoModel>) {
        listProduct = ArrayList()
        listProduct = newList
        notifyDataSetChanged()
    }
}