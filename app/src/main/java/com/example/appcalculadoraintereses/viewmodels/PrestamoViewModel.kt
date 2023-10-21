package com.example.appcalculadoraintereses.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.appcalculadoraintereses.models.PrestamoState
import java.math.BigDecimal
import java.math.RoundingMode

//Las clases viewmodel heredan el constructor viewModel
//para que android maneje el ciclo de vida del viewModel

class PrestamoViewModel : ViewModel(){
    //se declara una variable PrestamoState mutable, ya que sus
    //valores se muestran en la vista

    var state by mutableStateOf(PrestamoState())
        private set

    fun confirmDiaglog(){
        //Copiamos el estado para cambiar el cambpo alert
        state = state.copy(showAlert = false)
    }

    fun limpiar(){
        //Para modificar el valor del estado es necesario invocar
        //a la funcion copy
        state = state.copy(
            montoPrestamo = "",
            cantCuotas = "",
            tasa = "",
            montoIntereses = 0.0,
            montoCuota = 0.0
        )
    }
    //Función para cambiar los valores desde la vista
    //Esta funcion se asignará a cadda campo de texto de la vista

    fun onValue(value: String, campo: String){
        Log.i("chdez", campo)
        Log.i("chdez", value)
        //When es una estructura condivional similar al switch - case
        when(campo){
            //Los campos del state son constantes, por lo que para cambiar
            //dicho valor es necesario utilizar el método copy
            "montoPrestamo" -> state = state.copy(montoPrestamo = value)
            "cuotas"-> state = state.copy(cantCuotas = value)
            "tasa"-> state = state.copy(tasa = value)
        }
    }
    //Función para calcular el total
    private fun calcularTotal(
        montoPrestamo: Double, cantCuotas: Int, tasainteresAnual: Double
    ):Double{
        val res = cantCuotas * calcularCuota(montoPrestamo, cantCuotas, tasainteresAnual)
        return BigDecimal(res).setScale(2, RoundingMode.HALF_UP).toDouble()
    }

    //Función para calcular cuotas
    private fun calcularCuota(
        montoPrestamo: Double, cantCuotas: Int, tasainteresAnual: Double
    ):Double{

        val tasaInteresMensual = tasainteresAnual / 12 / 100

        val cuota = montoPrestamo * tasaInteresMensual * Math.pow(
            1 * tasaInteresMensual,
            cantCuotas.toDouble()
        ) / (Math.pow(1 + tasaInteresMensual, cantCuotas.toDouble()) - 1)

        val cuotaRedondeada = BigDecimal(cuota).setScale(2, RoundingMode.HALF_UP).toDouble()

        return cuotaRedondeada
    }

    fun calcular(){
        //Declaramos cada variable asociada al estado
        val montoPrestamo = state.montoPrestamo
        val cantCuotas = state.cantCuotas
        val tasa = state.tasa
        if (montoPrestamo!="" && cantCuotas !="" && tasa !=""){
            //Para modificar el estado del modelo se requiere utilizar el método copy
            state = state.copy(
                montoCuota = calcularCuota(montoPrestamo.toDouble(), cantCuotas.toInt(), tasa.toDouble()),
                montoIntereses = calcularTotal(montoPrestamo.toDouble(), cantCuotas.toInt(), tasa.toDouble())
            )
        }else{
            state = state.copy(showAlert = true)
        }
    }

}