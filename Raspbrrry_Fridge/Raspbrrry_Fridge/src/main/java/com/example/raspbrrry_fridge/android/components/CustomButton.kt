package com.example.raspbrrry_fridge.android.components

import android.service.autofill.OnClickAction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.raspbrrry_fridge.android.R

@Composable
fun CustomButton(
    modifier: Modifier = Modifier, icon : Painter, onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),
        content = {
            Icon(
                painter = icon, tint = MaterialTheme.colorScheme.onPrimary, contentDescription = null
            )
        }

    )
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier, text : String, onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),
        content = {
           Text(text = text)
        }

    )
}
