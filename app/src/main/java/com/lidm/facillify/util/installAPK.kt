package com.lidm.facillify.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun installAPK(context: Context, apkName: String) {
    try {
        // Copy APK from res/raw to internal storage
        val inputStream = context.resources.openRawResource(
            context.resources.getIdentifier(apkName, "raw", context.packageName)
        )
        val file = File(context.getExternalFilesDir(null), "$apkName.apk")
        val outputStream = FileOutputStream(file)
        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        // Install APK
        val intent = Intent(Intent.ACTION_VIEW)
        val apkUri: Uri =
            FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Gagal menginstal APK", Toast.LENGTH_LONG).show()
    }
}

fun launchAPK(context: Context, packageName: String) {
    val intent = context.packageManager.getLaunchIntentForPackage(packageName)
    if (intent != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Tidak dapat meluncurkan aplikasi AR", Toast.LENGTH_LONG).show()
    }
}

fun isAppInstalled(context: Context, packageName: String): Boolean {
    return try {
        context.packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}
