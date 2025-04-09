package com.example.calculadorabyjhaider

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadorabyjhaider.ui.theme.CalculadorabyJhaiderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppCalculadora()
        }
    }
}

@Composable
fun AppCalculadora() {
    CalculadorabyJhaiderTheme {
        val expresion = remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            textoCreador()
            Spacer(modifier = Modifier.height(8.dp))
            PantallaCalculadora(expresion.value)
            Spacer(modifier = Modifier.height(16.dp))
            BotonesCalculadora(
                onButtonClick = { valor ->
                    when (valor) {
                        "AC" -> expresion.value = ""
                        "DEL" -> if (expresion.value.isNotEmpty()) expresion.value =
                            expresion.value.dropLast(1)

                        "=" -> {
                            try {
                                val resultado = evaluarExpresion(expresion.value)
                                expresion.value = resultado
                            } catch (e: Exception) {
                                expresion.value = "Error"
                            }
                        }

                        else -> expresion.value += valor
                    }
                }
            )
        }
    }
}

@Composable
fun textoCreador() {
    Text(
        text = "Calculadora by Jhaider",
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PantallaCalculadora(displayText: String) {
    OutlinedTextField(
        value = displayText,
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        readOnly = true,
        textStyle = TextStyle(
            fontSize = 40.sp,
            textAlign = TextAlign.End
        )
    )
}

@Composable
fun BotonesCalculadora(onButtonClick: (String) -> Unit) {
    val buttons = listOf(
        listOf("AC", "DEL", "%", "/"),
        listOf("7", "8", "9", "*"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("(", "0", ".", "=")
    )

    Column {
        for (row in buttons) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (button in row) {
                    Button(
                        onClick = { onButtonClick(button) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.Cyan
                        )
                    ) {
                        Text(text = button, fontSize = 19.sp)
                    }
                }
            }
        }
    }
}

fun evaluarExpresion(expresion: String): String {
    return try {
        // Convertir el símbolo de porcentaje a /100 para interpretar como porcentaje
        val expresionFormateada = expresion.replace("%", "/100")
        val result = ExpressionParser().parse(expresionFormateada)

        // Mostrar sin decimales si es entero, o con 2 decimales si no lo es
        if (result % 1.0 == 0.0) {
            result.toInt().toString()
        } else {
            String.format("%.2f", result)
        }
    } catch (e: Exception) {
        "Error"
    }
}

class ExpressionParser {
    fun parse(expr: String): Double {
        return object {
            var pos = -1
            var ch = 0

            fun nextChar() {
                ch = if (++pos < expr.length) expr[pos].code else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.code) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < expr.length) throw RuntimeException("Unexpected: " + expr[pos])
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    when {
                        eat('+'.code) -> x += parseTerm()
                        eat('-'.code) -> x -= parseTerm()
                        else -> return x
                    }
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    when {
                        eat('*'.code) -> x *= parseFactor()
                        eat('/'.code) -> x /= parseFactor()
                        else -> return x
                    }
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.code)) return parseFactor()
                if (eat('-'.code)) return -parseFactor()

                var x: Double
                val startPos = pos
                if (eat('('.code)) {
                    x = parseExpression()
                    eat(')'.code)
                } else if (ch in '0'.code..'9'.code || ch == '.'.code) {
                    while (ch in '0'.code..'9'.code || ch == '.'.code) nextChar()
                    x = expr.substring(startPos, pos).toDouble()
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }

                return x
            }
        }.parse()
    }
}

@Preview(showBackground = true)
@Composable
fun VistaPreviaCalculadora() {
    AppCalculadora()
}
