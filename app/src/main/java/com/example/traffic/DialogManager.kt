package com.example.traffic

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class DialogManager {
    fun showTrafficDialog(activity: Context, callback: (Boolean) -> Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setMessage(R.string.text_dialog_message)
            .setCancelable(true)
            .setPositiveButton(R.string.text_answer_yes,
                DialogInterface.OnClickListener { dialog, id -> callback.invoke(true) })
            .setNegativeButton(R.string.text_answer_no,
                DialogInterface.OnClickListener { dialog, id ->
                    callback.invoke(false)
                    dialog.cancel()
                })
        val alert: AlertDialog = builder.create()
        alert.setOnCancelListener { callback.invoke(false) }
        alert.show()
    }
}