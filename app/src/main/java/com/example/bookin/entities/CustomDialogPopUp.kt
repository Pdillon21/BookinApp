package com.example.bookin.entities

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import com.example.bookin.R
import kotlinx.android.synthetic.main.custom_alert_dialog_pop_up.*
import java.lang.Exception

class CustomDialogPopUp constructor(anActivity: Activity){
    var activity: Activity = anActivity
    lateinit var dialog: AlertDialog


    fun showDismissableWarning (title : String, body : String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = activity.layoutInflater
        val view : View = inflater.inflate(R.layout.custom_alert_dialog_pop_up,null)
        builder.setView(view)
        builder.setCancelable(true)
        builder.setPositiveButton("Got it") { _: DialogInterface, _: Int -> dialog.dismiss() }
        dialog = builder.create()



        dialog.show()

        try {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isAllCaps = false
        } catch (e : Exception){
            e.printStackTrace()
        }
        if (!title.isEmpty()){
            dialog.custom_title.text = title
        } else {
            dialog.custom_title.text = activity.getText(R.string.default_warning_title)
        }

        if (!body.isEmpty()){
            dialog.custom_body_message.text = body
        } else {
            dialog.custom_body_message.text = activity.getText(R.string.default_warning_message)
        }

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(activity.getColor(R.color.mainBlue))
    }

    fun showUndismissableWarning (){
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_alert_dialog_pop_up, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()

        try {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isAllCaps = false
        } catch (e : Exception){
            e.printStackTrace()
        }
    }
}