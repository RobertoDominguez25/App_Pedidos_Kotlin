package com.example.examen1parcial

class LineaPedido(
    val producto: Producto,
    var cantidad: Int = 0
) {
    val subtotal: Double
        get() = producto.precio * cantidad

    fun descuentoCantidad(): Double =
        if ( cantidad >= Pedido.UNIDADES_DESCUENTO) subtotal * Pedido.PORCENTAJE_DESCUENTO_CANTIDAD
        else 0.0

    fun baseLinea(cupon: Pedido.Cupon?): Double {
        val trasCantidad = subtotal - descuentoCantidad()
        val descuentoCupon = cupon?.let { trasCantidad * it.porcentaje } ?: 0.0
        return trasCantidad - descuentoCupon
    }

    fun ivaLinea( cupon: Pedido.Cupon? = null): Double =
        baseLinea(cupon) * producto.categoria.iva
}