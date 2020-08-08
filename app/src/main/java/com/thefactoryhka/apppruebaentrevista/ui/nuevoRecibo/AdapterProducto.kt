package com.thefactoryhka.apppruebaentrevista.ui.nuevoRecibo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thefactoryhka.apppruebaentrevista.R
import kotlinx.android.synthetic.main.item_producto.view.*

class AdapterProducto (private var listProduct: ArrayList<ConstructorRecibo>, private val mctx: Context?) :
    RecyclerView.Adapter<AdapterProducto.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mctx).inflate(R.layout.item_producto, parent, false) as View

        return ViewHolder(view)
    }

    class ViewHolder (var view: View) : RecyclerView.ViewHolder(view){
        val tvCodigo: TextView = view.tv_codigo
        val tvPrecioCantidad: TextView = view.tv_cantidad_precio
        val tvDescripcion: TextView = view.tv_descripcion
        val tvTotal: TextView = view.tv_total_item
    }

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var codigo: String = listProduct[position].codigo.toString()
        var precio: String = listProduct[position].precio.toString()
        var cantidad: String = listProduct[position].cantidad.toString()
        var total: Double = listProduct[position].precio * listProduct[position].cantidad
        var resultado : Double = String.format("%.2f", total).toDouble()
        holder.tvCodigo.text = "Código: $codigo"
        holder.tvPrecioCantidad.text = "$cantidad x $precio"
        holder.tvDescripcion.text = listProduct[position].descripcion
        holder.tvTotal.text = resultado.toString()
    }

    fun updateList (newList: ArrayList<ConstructorRecibo>) {
        listProduct = ArrayList()
        listProduct = newList
        notifyDataSetChanged()
    }
}