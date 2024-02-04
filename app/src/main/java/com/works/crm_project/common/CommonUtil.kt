package com.works.crm_project.common

import android.app.AlertDialog
import android.content.Context

object CommonUtil {
    fun showAlertDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String,
        negativeButtonText: String,
        positiveButtonAction: () -> Unit,
        negativeButtonAction: () -> Unit
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButtonText) { dialog, which ->
            positiveButtonAction.invoke()
            dialog.dismiss()
        }
        builder.setNegativeButton(negativeButtonText) { dialog, which ->
            negativeButtonAction.invoke()
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }
}
