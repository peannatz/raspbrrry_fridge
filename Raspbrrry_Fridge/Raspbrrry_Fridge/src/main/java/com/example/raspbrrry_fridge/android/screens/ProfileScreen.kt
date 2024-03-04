package com.example.raspbrrry_fridge.android.screens

import android.Manifest
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.work.WorkManager
import com.example.raspbrrry_fridge.android.components.GeneralScaffold
import com.example.raspbrrry_fridge.android.components.PermissionRequestComponent
import com.example.raspbrrry_fridge.android.viewModel.ProductViewModel

var hour = 0
var minute = 0
val sendNotifications = mutableStateOf(false)

@Composable
fun ProfileScreen(
    navController: NavController,
    productViewModel: ProductViewModel = viewModel()
) {
    val context = LocalContext.current

    getSetting(context)

    GeneralScaffold(navController) {
        Column(Modifier.fillMaxSize()) {
            val notificationTime = remember {
                mutableStateOf(
                    if (sendNotifications.value) {
                        "%02d:%02d".format(hour, minute)
                    } else {
                        "Never"
                    }
                )
            }

            val timePickerDialog = TimePickerDialog(context, { _, hourOfDay, minute ->
                notificationTime.value = "%02d:%02d".format(hourOfDay, minute)
                setSetting(context, hourOfDay, minute)
                productViewModel.scheduleDailyTask(hourOfDay, minute)
            }, hour, minute, true)

            timePickerDialog.setOnCancelListener {
                WorkManager.getInstance(context).cancelUniqueWork("Fridge")
                notificationTime.value = "Never"
                sendNotifications.value = false
                deleteSetting(context)
            }

            var isPermissionGranted by remember { mutableStateOf(false) }

            if (!isPermissionGranted && sendNotifications.value) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    PermissionRequestComponent(Manifest.permission.POST_NOTIFICATIONS) { granted ->
                        isPermissionGranted = granted
                        if (!granted) {
                            print("notGranted")
                            // Handle the case where permission is not granted
                            // You can show a message or take appropriate action
                            // e.g., show a message using a Snackbar
                        }
                    }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .border(width = 2.dp, MaterialTheme.colorScheme.onPrimaryContainer)
                    .clickable {
                        sendNotifications.value = !sendNotifications.value
                        if (sendNotifications.value) {
                            timePickerDialog.show()
                        } else {
                            notificationTime.value = "Never"
                            WorkManager.getInstance(context).cancelUniqueWork("Fridge")
                            sendNotifications.value = false
                            deleteSetting(context)
                        }
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Send Notifications",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Text(
                    text =
                    notificationTime.value,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
        }
    }
}

fun getSetting(context: Context) {
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    hour = sharedPreferences.getInt("notification_time_hour", 10)
    minute = sharedPreferences.getInt("notification_time_minute", 0)
    sendNotifications.value = sharedPreferences.getBoolean("notifications_enabled", false)
}

fun setSetting(context: Context, hour: Int, min: Int) {
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("notifications_enabled", true)
    editor.putInt("notification_time_hour", hour)
    editor.putInt("notification_time_minute", min)
    editor.apply()
}

fun deleteSetting(context: Context) {
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.remove("notification_time_hour")
    editor.putBoolean("notifications_enabled", false)
    editor.remove("notification_time_minute")
    editor.apply()
}

@Preview(name = "StatsScreen")
@Composable
private fun PreviewRecipesScreen() {
    ProfileScreen(rememberNavController())
}
