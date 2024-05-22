package com.example.menu.ui.test

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.menu.R

class MyDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_layout, null)

        builder.setView(view)
            .setPositiveButton("Aceptar") { dialogInterface: DialogInterface, i: Int ->

            }

        return builder.create()
    }
}
