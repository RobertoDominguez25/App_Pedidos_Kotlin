package com.example.examen1parcial

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen1parcial.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var mibinding: ActivityMainBinding
    private lateinit var adapter: LineaAdapter

    private val pedido = Pedido(
        listOf(
            LineaPedido(Producto("Teclado", R.drawable.teclado,30.0, Categoria.TECNOLOGIA)),
            LineaPedido(Producto("Cuaderno", R.drawable.cuaderno,3.5, Categoria.OFICINA)),
            LineaPedido(Producto("Libro Kotlin", R.drawable.libro,12.0, Categoria.LIBROS)),
            LineaPedido(Producto("Bolígrafo", R.drawable.boligrafo,1.2, Categoria.OFICINA))
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mibinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mibinding.root)

        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        adapter = LineaAdapter(pedido.lineas) {
            actualizarResumen()
        }

        mibinding.RecViewLineas.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }


        mibinding.switchPromocion.setOnCheckedChangeListener { _, activo ->

            pedido.cupon = if (activo) {
                Pedido.Cupon("PROMO10", 0.10)
            } else {
                null
            }
            actualizarResumen()
        }


        mibinding.rgEnvio.setOnCheckedChangeListener { _, activo ->

            when (activo) {
                R.id.rbEstandar -> pedido.envio = TipoEnvio.ESTANDAR
                R.id.rbExpress -> pedido.envio = TipoEnvio.EXPRESS
            }
            actualizarResumen()

        }


        //Aqui los campos de los precios

        mibinding.bttVaciar.setOnClickListener {
            pedido.lineas.forEach { it.cantidad=0 }
            adapter.notifyDataSetChanged()
            actualizarResumen()
            Toast.makeText(this, "Pedido vaciado correctamente", Toast.LENGTH_SHORT).show()
        }


        actualizarResumen()

    }

    private fun actualizarResumen() {
        mibinding.tvTotal.text = getString(R.string.precio_formato, pedido.subtotal())
        mibinding.tvDescuento.text = getString(R.string.precio_formato, pedido.descuentos())
        mibinding.tvEnvio.text = getString(R.string.precio_formato, pedido.costeEnvio())
        mibinding.tvImporteTotal.text = getString(R.string.precio_formato, pedido.total())
    }


}