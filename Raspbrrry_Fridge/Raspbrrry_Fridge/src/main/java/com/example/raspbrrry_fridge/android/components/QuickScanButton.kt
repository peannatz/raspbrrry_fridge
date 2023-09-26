package com.example.raspbrrry_fridge.android.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.raspbrrry_fridge.android.R

@Composable
fun QuickScanButton(navController: NavController) {
    FloatingActionButton(onClick = { navController.navigate("ScanProduceScreen") }, content = {
        Icon(
            painter = painterResource(
                R.drawable.barcode
            ),
            modifier = Modifier.size(40.dp),
            contentDescription = "Barcode Scanner"
        )
    })
}

@Preview(name = "QuickScanButton")
@Composable
private fun PreviewQuickScanButton() {
    QuickScanButton(rememberNavController())
}
