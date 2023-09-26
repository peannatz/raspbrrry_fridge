package com.example.raspbrrry_fridge.android.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

@Composable
fun CameraComponent(
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner
) {
    val localContext = LocalContext.current // Obtain the context using LocalContext
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(localContext) }

    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA // Use the back camera by default

    AndroidView(
        modifier = modifier,
        factory = { context ->

            val previewView = PreviewView(context)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build()
                preview.setSurfaceProvider(previewView.surfaceProvider)

                try {
                    // Unbind use cases before rebinding
                    cameraProvider.unbindAll()

                    // Bind use cases to camera
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner, cameraSelector, preview)

                } catch(exc: Exception) {
                    Log.e(TAG, "Use case binding failed", exc)
                }
            }, ContextCompat.getMainExecutor(context))
            previewView
        }
    )
}
