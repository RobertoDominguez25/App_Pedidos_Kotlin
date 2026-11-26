package com.example.examen1parcial

import androidx.annotation.DrawableRes

data class Producto(val nombre: String, @DrawableRes val imagen: Int, val precio: Double, val categoria: Categoria)
