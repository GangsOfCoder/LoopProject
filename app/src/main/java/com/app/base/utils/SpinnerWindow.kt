package com.app.base.utils

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import com.app.base.R
import com.app.base.callBack.SpinnerWindowInterface

/**
 * Created by Suraj Bahadur on 26-May-20.
 */
class SpinnerWindow(spinnerWindow_interface: SpinnerWindowInterface?) {
    companion object {
        private var spinnerWindow_interface: SpinnerWindowInterface? = null
        fun showSpinner(context: Context?, array_data: ArrayList<String>) {
            val dialog_spinner = Dialog(context!!)
            dialog_spinner.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_spinner.setContentView(R.layout.spinner_dialog)
            val lp_number_picker = WindowManager.LayoutParams()
            val window = dialog_spinner.window
            lp_number_picker.copyFrom(window!!.attributes)
            lp_number_picker.width = WindowManager.LayoutParams.MATCH_PARENT
            lp_number_picker.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.setGravity(Gravity.BOTTOM)
            window.attributes = lp_number_picker
            dialog_spinner.window!!.setGravity(Gravity.BOTTOM)
            dialog_spinner.window!!.attributes.windowAnimations = R.style.custom_alert_dialog_animation_spinner
            val listview_spinner = dialog_spinner.findViewById<ListView>(R.id.listview_spinner)
            listview_spinner.adapter = ArrayAdapter(context, R.layout.spinner_textview, R.id.number_textview, array_data)
            listview_spinner.onItemClickListener = OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                spinnerWindow_interface!!.selectedPosition(position)
                if (dialog_spinner != null) {
                    dialog_spinner.dismiss()
                    dialog_spinner.cancel()
                }
            }
            dialog_spinner.show()
        }
    }

    init {
        Companion.spinnerWindow_interface = spinnerWindow_interface
    }
}