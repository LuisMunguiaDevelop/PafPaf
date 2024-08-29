package com.example.pafpaf.features.album.usecase

import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Calendar
import java.util.Date
import java.util.Locale


fun saveBitmapToExternalStorage(bitmap: Bitmap, context: android.content.Context): String? {
    val filename = generateUniqueFilename() // Generate a unique filename
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val newWidth = 1254 // Customize the resized width 1254
    val newHeight = 1254 // Customize the resized height
    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)

    return try {
        if (storageDir != null) {
            val file = File(storageDir, filename)
            val outputStream = FileOutputStream(file)
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            // Optionally, insert the image into MediaStore to make it visible in the gallery
            //insertImageIntoMediaStore(file.absolutePath, context)
            file.absolutePath
        } else {
            null // Handle error: storageDir is null
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null // Handle error: File saving failed
    }
}

fun generateUniqueFilename(): String {
    val timestamp = Date().time
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1 // Month is 0-indexed
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return String.format(Locale.getDefault(), "image_%04d%02d%02d_%03d.jpg", year, month, day, timestamp)
}