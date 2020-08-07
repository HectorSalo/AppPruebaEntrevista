package com.thefactoryhka.apppruebaentrevista.ui.nuevoRecibo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thefactoryhka.apppruebaentrevista.R
import kotlinx.android.synthetic.main.item_producto.view.*

class AdapterProducto (private var listProduct: ArrayList<ConstructorProducto>, private val mctx: Context) :
    RecyclerView.Adapter<AdapterProducto.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterProducto.ViewHolder {
        val view = LayoutInflater.from(mctx).inflate(R.layout.item_producto, parent, false) as View

        return ViewHolder(view)
    }

    class ViewHolder (var view: View) : RecyclerView.ViewHolder(view){
        val tvCodigo = view.tv_codigo
        val tvPrecioCantidad = view.tv_cantidad_precio
        val tvDescripcion = view.tv_descripcion
        val tvTotal = view.tv_total_item
    }

    override fun getItemCount(): Int = listProduct.size

    override fun onBindViewHolder(holder: AdapterProducto.ViewHolder, position: Int) {
        holder.tvCodigo.text = listProduct[position].codigo.toString()
    }
}