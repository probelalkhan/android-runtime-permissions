package net.simplifiedcoding.androidpermissions

import android.app.AlertDialog
import android.content.Context

fun Context.showPermissionRequestExplanation(
    permission: String,
    message: String,
    retry: (() -> Unit)? = null
) {
    AlertDialog.Builder(this).apply {
        setTitle("$permission Required")
        setMessage(message)
        setPositiveButton("Ok") { _, _ -> retry?.invoke() }
    }.show()
}