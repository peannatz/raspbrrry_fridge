package com.example.raspbrrry_fridge.android.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PermissionRequestComponent(
    permission: String,
    onPermissionResult: (Boolean) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onPermissionResult(isGranted)
    }

    DisposableEffect(Unit) {
        launcher.launch(permission)
        onDispose { }
    }
}

@Preview(name = "PermissionRequest")
@Composable
private fun PreviewPermissionRequest() {
    //PermissionRequest()
}
