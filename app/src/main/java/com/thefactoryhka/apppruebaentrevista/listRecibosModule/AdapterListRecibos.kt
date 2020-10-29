package com.thefactoryhka.apppruebaentrevista.listRecibosModule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thefactoryhka.apppruebaentrevista.R
import com.thefactoryhka.apppruebaentrevista.common.model.ReciboModel
import kotlinx.android.synthetic.main.item_producto.view.*
import java.text.DecimalFormat

class AdapterListRecibos (private var listRecibos: ArrayList<ReciboModel>, private val mctx: Context?) :
    RecyclerView.Adapter<AdapterListRecibos.ViewHolder>() {
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tvCliente: TextView = view.tv_codigo
        val tvEmisor: TextView = view.tv_cantidad_precio
        val tvCantidad: TextView = view.tv_descripcion
        val tvTotal: TextView = view.tv_total_item
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterListRecibos.ViewHolder {
        val view = LayoutInflater.from(mctx).inflate(R.layout.item_producto, parent, false) as View

        return AdapterListRecibos.ViewHolder(view)
    }

    override fun getItemCount(): Int = listRecibos.size

    override fun onBindViewHolder(holder: AdapterListRecibos.ViewHolder, position: Int) {
        val dec = DecimalFormat("#,###.##")
        var total = listRecibos[position].total
        holder.tvCliente.text = "Cliente: ${listRecibos[position].cliente}"
        holder.tvEmisor.text = "Emisor: ${listRecibos[position].emisor}"
        holder.tvCantidad.text = "Cantidad de items: ${listRecibos[position].cantidadItems}"
        holder.tvTotal.text = "Monto total ${dec.format(total)}"
        holder.tvCliente.textSize = 18f
        holder.tvTotal.textSize = 16f
    }

    fun updateList (newList: ArrayList<ReciboModel>) {
        listRecibos = ArrayList()
        listRecibos = newList
        notifyDataSetChanged()
    }
}