package com.example.appcalculadoraintereses.ui.theme.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.appcalculadoraintereses.components.Alert
import com.example.appcalculadoraintereses.components.MainButton
import com.example.appcalculadoraintereses.components.MainTextField
import com.example.appcalculadoraintereses.components.ShowInfoCard
import com.example.appcalculadoraintereses.components.SpaceH
import com.example.appcalculadoraintereses.viewmodels.PrestamoViewModel
import java.math.BigDecimal
import java.math.RoundingMode


@Composable
fun ContentHomeView(paddingValues: PaddingValues, viewModel: PrestamoViewModel){
    Column (
        modifier = Modifier
            .padding(paddingValues)
            .padding(10.dp)
            .fillMaxSize(),
        //verticalArrangement = Arrangement.Center
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        //Declaramos las variables con las que haremos el calculo
        //recuerda que mutableStateOf sirve para manejar el estado
        //de las variables en un tiempo de ejecución
        var montoPrestamo by remember { mutableStateOf("")}
        var cantCuotas by remember { mutableStateOf("")}
        var tasa by remember { mutableStateOf("")}
        var montoIntereses by remember { mutableStateOf(0.0)}
        var montoCuota by remember { mutableStateOf(0.0)}
        //Esta variable será usada para mostrar un mensaje de alerta
        var showAlert by remember { mutableStateOf(false)}

        //Invocamos la función para incluir las cads con la información
        ShowInfoCard(
            titleInteres = "Total:",
            montoIntereses = montoIntereses,
            titleMonto = "Cuota:",
            monto = montoCuota
        )
        //Declaramos los campos de texto con los que ingresamos la información
        //la variable "it" es como se declara por defecto el valor capturado por el textField
        MainTextField(value = montoPrestamo, onValueChange = {montoPrestamo = it }, label = "Prestamo")
        SpaceH()
        MainTextField(value = cantCuotas, onValueChange = {cantCuotas = it }, label = "Cuotas")
        SpaceH(10.dp)
        MainTextField(value = tasa, onValueChange = {tasa = it }, label = "Tasa")
        SpaceH(10.dp)
        //Creamos un botón para realizar los cálculos
        MainButton(text = "Calcular") {
            //Validamos que los campos no esten vacios
            if(montoPrestamo != "" && cantCuotas != ""){
                //invocamos la función creada para calcular el total a pagar
                montoIntereses = calcularTotal(montoPrestamo.toDouble(), cantCuotas.toInt(), tasa.toDouble())
                montoCuota = calcularCuota(montoPrestamo.toDouble(), cantCuotas.toInt(), tasa.toDouble())
            }else{
                //si los campos están vacios cambiamos nuestra bandera
                //para mostrar el mensaje de alerta
                showAlert = true
            }
        }
        //Agregamos un campo para limpiar los campos
        MainButton(text = "Limpiar", color = Color.Red){
            montoPrestamo = ""
            cantCuotas = ""
            tasa = ""
            montoIntereses = 0.0
            montoCuota = 0.0
        }

        //Mostramos el mensaje de alerta si la bandera es verdadera
        if(showAlert){
            Alert(title = "Alerta",
                message = "Ingresa los datos",
                confirmText = "Aceptar",
                //Al hacer click en aceptar, cambiamos el valor de la bandera
                onConfirmClick = { showAlert = false }) {}
        }
    }

    //Obtenemos el estado del modelo que declaramos en el viewModel
    val state = viewModel.state
    //Invocamos la función para incluir las card con la información
    ShowInfoCard(
        titleInteres = "Total: " ,
        //La información a mostrar en los card está contenida en el model
        montoIntereses = state.montoIntereses,
        titleMonto = "Cuota: ",
        monto = state.montoCuota
    )
    MainTextField(value = state.montoPrestamo,
        //Invocamos la función onValue declara en el viewModel
        onValueChange = {viewModel.onValue(value = it, campo = "montoPrestamo")}, label = "Prestamo")
    SpaceH()
    MainTextField(value = state.cantCuotas,
        onValueChange = {viewModel.onValue(value = it, campo = "cuotas")} , label = "Cuotas")
    SpaceH(10.dp)
    MainTextField(value = state.tasa,
        onValueChange = {viewModel.onValue(value = it, campo = "tasa")}, label = "Tasa")
    SpaceH(10.dp)

    //Creamos un boton para analizar los cálculos
    MainButton(text = "Calular") {
        //Invocamos la función calcular definida en el viewmodel
        viewModel.calcular()
    }

    SpaceH()

    //Agregamos un campo para limpiar los campos
    MainButton(text = "Limpiar", color = Color.Red) {
        //Se invoca la función declarada en la viewmodel
        viewModel.limpiar()
    }

    //Mostramos el mensaje de alerta si la bandera es verdadera
    //Tomamos el valor del model a través del viewmodel
    if (viewModel.state.showAlert){
        Alert(
            title = "Alerta",
            message = "Ingresa los datos",
            confirmText = "Aceptar",
            //Al hacer click en aceptar, invoca la función del viewModel
            onConfirmClick = { viewModel.confirmDiaglog() }) { }
    }
}

//Función para calcular el total
fun calcularTotal(montoPrestamo: Double, cantCuotas: Int, tasaInteresAnual: Double): Double {
    val res = cantCuotas * calcularCuota(montoPrestamo, cantCuotas, tasaInteresAnual)
    return BigDecimal(res).setScale(2, RoundingMode.HALF_UP).toDouble()
}

//Función para calcular la cuota
fun calcularCuota(montoPrestamo: Double, cantCuotas: Int, tasaInteresAnual: Double): Double{
    //Convierte la tasa de Interés anual a tasa periódica mensual
    val tasaInteresMensual = tasaInteresAnual/12/100

    //Calcula la cuota nivelada utilizando la formula
    val cuota = montoPrestamo * tasaInteresMensual * Math.pow(1 + tasaInteresMensual, cantCuotas.toDouble()) /
            (Math.pow(1 + tasaInteresMensual, cantCuotas.toDouble()) - 1)
    //Redondea el resultado a 2 decimales utilizando BidDecimal
    val cuotaRedondeada = BigDecimal(cuota).setScale(2, RoundingMode.HALF_UP).toDouble()

    return cuotaRedondeada
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(viewModel: PrestamoViewModel){
    //Agregamos el viewModel al constructor de la vista
    Scaffold (topBar = {
        CenterAlignedTopAppBar(
            //Ponemos un título a nuestra topBar
            title = { Text(text = "Calculadora Prestamos", color = Color.Magenta)},
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )
    }){
        //Enviamos el viewModel a la función de contenido para que
        //pueda utlizar la lógica del viewModel
        ContentHomeView(it, viewModel)
    }
}

