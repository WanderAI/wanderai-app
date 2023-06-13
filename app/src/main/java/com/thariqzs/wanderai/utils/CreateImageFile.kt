package com.thariqzs.wanderai.utils

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import com.thariqzs.wanderai.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"

fun createImageFile(context: Context): File {
    val TAG = "cifthoriq"

    val imageDirectory = File(context.getExternalFilesDir(null), "my_images")
    imageDirectory.mkdirs()

    val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())

    val imageFileName = "JPEG_$timeStamp.jpg"
    val image = File(imageDirectory, imageFileName)

    Log.d(TAG, "image: $image")
    return image
}