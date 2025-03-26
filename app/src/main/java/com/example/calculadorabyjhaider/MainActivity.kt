package com.example.calculadorabyjhaider

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ButtonDefaults

import androidx.compose.runtime.Composable

import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            textoCreador()
            Spacer(modifier = Modifier.height(8.dp))
            PantallaCalculadora()
            Spacer(modifier = Modifier.height(16.dp))
            BotonesCalculadora()
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
fun PantallaCalculadora() {
    val displayText by remember { mutableStateOf("8691") }
    OutlinedTextField(
        value = displayText,
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        readOnly = true,
        textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 40.sp,
            textAlign = TextAlign.End
        )
    )
}

@Composable
fun BotonesCalculadora() {
    val buttons = listOf(
        listOf("AC", "DEL", "%", "/"),
        listOf("7", "8", "9", "*"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("e", "0", ".", "=")
    )

    Column {
        for (row in buttons) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                for (button in row) {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.Cyan
                        )
                    ) {
                        Text(text = button,fontSize = 19.sp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VistaPreviaCalculadora() {
    AppCalculadora()
}
