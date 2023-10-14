package com.example.appcalculadoraintereses.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable 
//Spacer se utiliza para separar horizontalmente nuestros componentes
//El parámetro size que es de tipo DP, tiene un valor por defecto de 5 dp
fun SpaceH(size: Dp = 5.dp) {
    Spacer(modifier = Modifier.height(size))
}

@Composable
//Utilizaremos otro spacer para separar
//Al igual que el anterior utilizará el parámetro size que es de tipo DP
//Tiene un valor por defecto de 5 dp
fun SpaceW(size: Dp = 5.dp){
    Spacer(modifier = Modifier.width(size))
}

//Función para crear un campo de texto de la libreria de material Desing
//Value: representa el valor al cual se asocia el contenido ingresado
//onValueChange: es de tipo Unit ya que a el se le asignará una acción o función
//label: etiqueta del campo de texto
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTextField(value: String, onValueChange: (String) -> Unit, label: String){
    //Campo de texto de material Desing el cual es transparente
    //Tiene menor énfasis que un campo relleno
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label)},
        //Esta propiedad nos permite restringir el tipo de caracteres que se pueden ingresar
        //Al mismo tiempo se indica que el tipo de teclado puede mostrar, en este caso un teclado numérico
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            //el padding representa el espacio entre los bordes del componente
            //para que no se ocupe toda la pantalla
            .padding(horizontal = 30.dp)
    )
}

@Composable
//Función reutilizable para crear un botón
//el parámetro color tiene por defecto MaterialTheme para poder utilizar los colores del esquema
fun MainButton(
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
){
    OutlinedButton(
        onClick = onClick, colors = ButtonDefaults.outlinedButtonColors(
            contentColor = color,
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
        ) {
        Text(text = text)
    }
}

//Función para crear alertas de dialogo reutilizable
@Composable
fun Alert(
    title: String, //Título del mensaje de alerta
    message: String, //Mensaje a mostrar en el dialogo
    confirmText: String, //Texto para el botón de confirmación
    onConfirmClick: () -> Unit, //Acción al confirmar o aceptar el dialogo
    onDismissClick: () -> Unit //Acción al ignorar el dialogo
){
    AlertDialog(
        //Función que se ejecuta cuando el usuario trata de ignorar la alerta
        //Haciendo click fuera de ella
        onDismissRequest = onDismissClick,
        title = { Text(text = title)},
        text = {Text(text = message)},
        //Componente que representa la confirmación sobre el dialogo mostrado
        confirmButton = {
            Button(onClick = { onConfirmClick()}) {
                Text(text = confirmText)
            }
        }
    )
}