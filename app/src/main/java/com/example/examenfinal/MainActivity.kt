package com.example.examenfinal

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.examenfinal.ui.theme.ExamenFinalTheme




data class Empleado(
    val id: Long,
    val nombre: String,
    val cargo: String,
    val departamento: String,
    val salario: String,
    val fechaContratacion: String
)

class MainActivity : ComponentActivity() {

    private val tagLifecycle = "MainActivityLifecycle"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenFinalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EmployeeAppScreen()
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        Log.i(tagLifecycle, "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.i(tagLifecycle, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tagLifecycle, "onDestroy")
    }
}

@Composable
fun EmployeeAppScreen() {
    var listaEmpleados by remember { mutableStateOf(listOf<Empleado>()) }
    var idCounter by remember { mutableLongStateOf(1L) }


    var nombre by remember { mutableStateOf("") }
    var cargo by remember { mutableStateOf("") }
    var departamento by remember { mutableStateOf("") }
    var salario by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Registro de Empleados",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp),
            color = MaterialTheme.colorScheme.primary
        )


        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre Completo") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = cargo, onValueChange = { cargo = it }, label = { Text("Cargo") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = departamento, onValueChange = { departamento = it }, label = { Text("Departamento") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = salario, onValueChange = { salario = it }, label = { Text("Salario") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = fecha, onValueChange = { fecha = it }, label = { Text("Fecha de Contratación") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank() && cargo.isNotBlank()) {
                    val nuevo = Empleado(idCounter++, nombre, cargo, departamento, salario, fecha)
                    listaEmpleados = listaEmpleados + nuevo
                    nombre = ""; cargo = ""; departamento = ""; salario = ""; fecha = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Empleado")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Empleados Registrados", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(listaEmpleados, key = { it.id }) { empleado ->
                EmpleadoCard(
                    empleado = empleado,
                    onDelete = { listaEmpleados = listaEmpleados.filter { it.id != empleado.id } }
                )
            }
        }
    }
}

@Composable
fun EmpleadoCard(empleado: Empleado, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = empleado.nombre,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))


            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item { DataChip(label = "Cargo", value = empleado.cargo) }
                item { DataChip(label = "Dep", value = empleado.departamento) }
                item { DataChip(label = "Salario", value = empleado.salario) }
                item { DataChip(label = "Fecha", value = empleado.fechaContratacion) }
            }

            Spacer(modifier = Modifier.height(12.dp))


            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Eliminar", color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}

@Composable
fun DataChip(label: String, value: String) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = "$label: $value",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}