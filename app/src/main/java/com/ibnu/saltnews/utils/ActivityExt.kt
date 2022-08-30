package com.ibnu.saltnews.utils
import android.app.Activity
import androidx.appcompat.app.AlertDialog
import kotlin.system.exitProcess

fun Activity.showExitAppDialog(){
    AlertDialog.Builder(this).apply {
        setTitle("Keluar Aplikasi")
        setMessage("Apakah Anda yakin untuk keluar dari aplikasi Salt News?")
        setNegativeButton("Tidak") { p0, _ ->
            p0.dismiss()
        }
        setPositiveButton("IYA") { _, _ ->
            finish()
            exitProcess(0)
        }
    }.create().show()
}