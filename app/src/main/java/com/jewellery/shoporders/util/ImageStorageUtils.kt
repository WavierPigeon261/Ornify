package com.jewellery.shoporders.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.util.UUID

object ImageStorageUtils {

    fun createTempPictureUri(context: Context): Pair<Uri, File> {
        val storageDir = File(context.filesDir, "images").apply {
            if (!exists()) mkdirs()
        }
        val file = File.createTempFile(
            "IMG_${System.currentTimeMillis()}_",
            ".jpg",
            storageDir
        )
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        return Pair(uri, file)
    }

    fun copyUriToInternalStorage(context: Context, sourceUri: Uri): String? {
        return try {
            val storageDir = File(context.filesDir, "images").apply {
                if (!exists()) mkdirs()
            }
            val destinationFile = File(storageDir, "SAVED_${System.currentTimeMillis()}_${UUID.randomUUID().toString().take(6)}.jpg")
            context.contentResolver.openInputStream(sourceUri)?.use { input ->
                destinationFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            destinationFile.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
