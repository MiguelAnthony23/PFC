package com.example.menu.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

object UsbUtils {

    private const val REQUEST_USB_PERMISSION = 1

    fun requestUsbPermissionsIfNeeded(activity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasUsbPermissions(activity)) {
                requestUsbPermissions(activity)
            }
        }
    }

    private fun hasUsbPermissions(context: Context): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestUsbPermissions(activity: AppCompatActivity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            REQUEST_USB_PERMISSION
        )
    }
}
