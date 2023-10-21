package com.example.appcalculadoraintereses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.appcalculadoraintereses.ui.theme.AppCalculadoraInteresesTheme
import com.example.appcalculadoraintereses.ui.theme.views.HomeView
import com.example.appcalculadoraintereses.viewmodels.PrestamoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //declaramos el viewModel
        val viewModel: PrestamoViewModel by viewModels()
        setContent {
            AppCalculadoraInteresesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Incluimos nuestra vista creada
                    HomeView(viewModel)
                }
            }
        }
    }
}


