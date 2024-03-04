package com.example.raspbrrry_fridge.android


import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.raspbrrry_fridge.android.data.Product
import com.example.raspbrrry_fridge.android.viewModel.ProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.random.Random

class FridgeNotificationService(
    appContext: Context, workerParams: WorkerParameters
) :
    Worker(appContext, workerParams) {
    private val context = appContext
    private val productViewModel = ProductViewModel()

    override fun doWork(): Result {
        productViewModel.fetchProducts()

        val coroutineScope = CoroutineScope(Dispatchers.Default)

        coroutineScope.async {
            productViewModel.fetchProducts()
            productViewModel.products.first { it.isNotEmpty() }

            val products: List<Product> = productViewModel.products.value
            val expiringProducts = products.filter { e -> getDifferenceInDays(e.localDateMhd) <= 3 }

            if (expiringProducts.isNotEmpty()) {
                showBasicNotification(expiringProducts)
            }
        }
        return Result.success()
    }

    private fun getDifferenceInDays(mhd: LocalDate): Int {
        val currentDate = LocalDate.now()
        val differenceInDays = ChronoUnit.DAYS.between(currentDate, mhd)
        println(differenceInDays.toInt())
        return differenceInDays.toInt()
    }

    private val notificationManager = appContext.getSystemService(NotificationManager::class.java)

    private fun showBasicNotification(expiringProducts: List<Product>) {

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.action = "open_recipes"
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(applicationContext,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE)

        val notificationText = if (expiringProducts.count() == 1) {
            "${expiringProducts[0].name} is expiring in ${getDifferenceInDays(expiringProducts[0].localDateMhd)} days."
        } else if (expiringProducts.count() == 2) {
            "${expiringProducts[0].name} and ${expiringProducts[1].name} are expiring in the next three days."
        } else {
            "${expiringProducts.count()} products are expiring within the next three days."
        }

        val notification = NotificationCompat.Builder(context, "fridge_notification")
            .setContentTitle("Jesse it's time to cook!")
            .setContentText(notificationText)
            .setSmallIcon(R.drawable.nutrition)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }
}
