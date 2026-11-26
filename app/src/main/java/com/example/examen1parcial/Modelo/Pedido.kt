package com.example.examen1parcial

class Pedido(val lineas: List<LineaPedido>) {
    var envio: TipoEnvio = TipoEnvio.ESTANDAR
    var cupon: Cupon? = null

    // Nested class Cupon
    class Cupon(
        val codigo: String,
        val porcentaje: Double
    )

    companion object {
        const val UNIDADES_DESCUENTO = 10
        const val PORCENTAJE_DESCUENTO_CANTIDAD = 0.05
        const val UMBRAL_ENVIO_GRATIS = 50.0
    }

    //REVISAR
    fun subtotal(): Double = lineas.sumOf { it.subtotal }

    fun ivaLinea(): Double = lineas.sumOf { it.ivaLinea(cupon) }


    //Me falta que diferencie cuando es envio estandar o cuando es express
    fun costeEnvio(): Double = when {
        subtotal() == 0.0 -> 0.0
        envio == TipoEnvio.ESTANDAR && subtotal() >= UMBRAL_ENVIO_GRATIS -> 0.0
        else -> envio.coste
    }

    fun total(): Double = lineas.sumOf { it.baseLinea(cupon) } + ivaLinea() + costeEnvio()

    fun filtrarLineas (criterio: (LineaPedido) -> Boolean): List<LineaPedido> = lineas.filter (criterio)

    fun lineasConDescuento (): List<LineaPedido> = filtrarLineas { it.descuentoCantidad() > 0 || (cupon != null && it.cantidad > 0) }

    fun importeLineasConDescuento(): Double = lineasConDescuento().sumOf { it.baseLinea( cupon) }

    fun productoMasCaro(): Producto? = filtrarLineas { it.cantidad > 0 }.maxByOrNull { it.producto.precio }?.producto

    fun descuentos(): Double = lineas.sumOf { it.subtotal - it.baseLinea(cupon)}


}






