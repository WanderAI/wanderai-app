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

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun Context.createImageFile(): File {
    val TAG = "cifthoriq"

    val imageDirectory = File(getExternalFilesDir(null), "my_images")
    imageDirectory.mkdirs()

    val imageFileName = "JPEG_$timeStamp.jpg"
    val image = File(imageDirectory, imageFileName)

    return image
}