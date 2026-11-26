package com.example.examen1parcial

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.examen1parcial.databinding.ElementoLineaPedidoBinding


class LineaAdapter(
    private val lineas: List<LineaPedido>,
    private val onCantidadCambiada: () -> Unit
) : RecyclerView.Adapter<LineaAdapter.LineaViewHolder>() {

    // 3.3 ViewHolder como nested class
    class LineaViewHolder(private val mibinding: ElementoLineaPedidoBinding) : RecyclerView.ViewHolder(mibinding.root) {
        fun bind(linea: LineaPedido, onCantidadCambiada: () -> Unit) {

            mibinding.ivProducto.setImageResource(linea.producto.imagen)
            mibinding.tvNombreProducto.text = linea.producto.nombre
            mibinding.tvPrecioUnidad.text = String.format("%.2f €", linea.producto.precio)
            mibinding.tvCantidad.text = linea.cantidad.toString()
            mibinding.tvSubtotal.text = String.format("%15.2f €", linea.subtotal)

            // Color del subtotal según si tiene descuento por cantidad
            val colorContext = mibinding.root.context
            val colorRes = if (linea.cantidad >= Pedido.UNIDADES_DESCUENTO) {
                R.color.color_descuento
            } else {
                R.color.color_normal
            }
            mibinding.tvSubtotal.setTextColor(ContextCompat.getColor(colorContext, colorRes))

            // Botón +
            mibinding.bttMas.setOnClickListener {
                linea.cantidad++
                mibinding.tvCantidad.text = linea.cantidad.toString()
                mibinding.tvSubtotal.text = String.format("%15.2f €", linea.subtotal)
                // Actualizar color dinámicamente si alcanza el umbral
                val nuevoColor = if (linea.cantidad >= Pedido.UNIDADES_DESCUENTO) R.color.color_descuento else R.color.color_normal
                mibinding.tvSubtotal.setTextColor(ContextCompat.getColor(colorContext, nuevoColor))
                onCantidadCambiada()
            }

            // Botón -
            mibinding.bttMenos.setOnClickListener {
                if (linea.cantidad > 0) {
                    linea.cantidad--
                    mibinding.tvCantidad.text = linea.cantidad.toString()
                    mibinding.tvSubtotal.text = String.format("%15.2f €", linea.subtotal)
                    val nuevoColor = if (linea.cantidad >= Pedido.UNIDADES_DESCUENTO) R.color.color_descuento else R.color.color_normal
                    mibinding.tvSubtotal.setTextColor(ContextCompat.getColor(colorContext, nuevoColor))
                    onCantidadCambiada()
                }
            }
        }
    }

    // 3.4 onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineaViewHolder {
        val binding = ElementoLineaPedidoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LineaViewHolder(binding)
    }

    // 3.5 onBindViewHolder
    override fun onBindViewHolder(holder: LineaViewHolder, position: Int) {
        holder.bind(lineas[position], onCantidadCambiada)
    }

    // 3.6 getItemCount
    override fun getItemCount(): Int = lineas.size
}