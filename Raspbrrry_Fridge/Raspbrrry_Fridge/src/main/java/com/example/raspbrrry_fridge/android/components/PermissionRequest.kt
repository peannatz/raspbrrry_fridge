package com.example.raspbrrry_fridge.android.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PermissionRequestComponent(
    onPermissionResult: (Boolean) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onPermissionResult(isGranted)
    }

    DisposableEffect(Unit) {
        launcher.launch(Manifest.permission.CAMERA)
        // Add more permissions as needed
        onDispose { }
    }
}

@Preview(name = "PermissionRequest")
@Composable
private fun PreviewPermissionRequest() {
    //PermissionRequest()
}
