package com.example.kegelcontrol.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kegelcontrol.ui.components.CustomButton
import com.example.kegelcontrol.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()){
    CustomButton(
        text = "Haga Click Test",
        onClick = {
            viewModel.onButtonClicked()
        }
    )
}