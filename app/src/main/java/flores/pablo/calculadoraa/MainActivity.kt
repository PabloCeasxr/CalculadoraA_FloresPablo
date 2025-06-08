package flores.pablo.calculadoraa

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var pantalla: TextView

    private var operador: String? = null
    private var numeroAnterior: String = ""
    private var nuevoNumero: String = ""
    private var resultadoMostrado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pantalla = findViewById(R.id.pantalla)

        // Asignar listeners para los botones numéricos
        val numeros = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
            R.id.btn8, R.id.btn9
        )
        for (id in numeros) {
            findViewById<Button>(id).setOnClickListener { agregarNumero((it as Button).text.toString()) }
        }

        // Operadores
        findViewById<Button>(R.id.suma).setOnClickListener { seleccionarOperacion("+") }
        findViewById<Button>(R.id.resta).setOnClickListener { seleccionarOperacion("-") }
        findViewById<Button>(R.id.multi).setOnClickListener { seleccionarOperacion("*") }
        findViewById<Button>(R.id.division).setOnClickListener { seleccionarOperacion("/") }

        // Igual
        findViewById<Button>(R.id.igual).setOnClickListener { calcularResultado() }

        // Clear
        findViewById<Button>(R.id.clear).setOnClickListener { limpiar() }
    }

    private fun agregarNumero(num: String) {
        if (resultadoMostrado) {
            nuevoNumero = ""
            resultadoMostrado = false
        }
        nuevoNumero += num
        pantalla.text = nuevoNumero
    }

    private fun seleccionarOperacion(op: String) {
        if (nuevoNumero.isNotEmpty()) {
            numeroAnterior = nuevoNumero
            operador = op
            nuevoNumero = ""
        }
    }

    private fun calcularResultado() {
        if (numeroAnterior.isNotEmpty() && nuevoNumero.isNotEmpty() && operador != null) {
            val resultado = when (operador) {
                "+" -> numeroAnterior.toDouble() + nuevoNumero.toDouble()
                "-" -> numeroAnterior.toDouble() - nuevoNumero.toDouble()
                "*" -> numeroAnterior.toDouble() * nuevoNumero.toDouble()
                "/" -> {
                    if (nuevoNumero == "0") {
                        pantalla.text = "Error"
                        return
                    } else {
                        numeroAnterior.toDouble() / nuevoNumero.toDouble()
                    }
                }

                else -> return
            }
            pantalla.text = resultado.toString()
            resultadoMostrado = true
            nuevoNumero = resultado.toString()
            numeroAnterior = ""
            operador = null
        }
    }

    private fun limpiar() {
        numeroAnterior = ""
        nuevoNumero = ""
        operador = null
        resultadoMostrado = false
        pantalla.text = ""
    }
}
