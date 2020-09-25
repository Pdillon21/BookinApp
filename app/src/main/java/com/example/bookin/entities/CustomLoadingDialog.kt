package com.example.bookin.entities

import android.app.Activity
import android.app.AlertDialog
import android.graphics.drawable.AnimationDrawable
import android.view.LayoutInflater
import android.widget.TextView
import com.example.bookin.R
import kotlinx.android.synthetic.main.custom_loading_dialog.*
import java.lang.Exception

class CustomLoadingDialog constructor(anActivity: Activity) {
    var activity: Activity = anActivity
    lateinit var dialog: AlertDialog

    fun startLoadingAnimation() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.custom_loading_dialog, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
        try {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isAllCaps = false
        } catch (e : Exception){
            e.printStackTrace()
        }
        val animDrawable: AnimationDrawable = dialog.loadingImageView.drawable as AnimationDrawable
        animDrawable.start()


    }

    fun startLoadingAnimationWithMessage(message: String) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = activity.layoutInflater

        builder.setView(inflater.inflate(R.layout.custom_loading_dialog, null))
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()
        try {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).isAllCaps = false
        } catch (e : Exception){
            e.printStackTrace()
        }
        val messageTextVIew: TextView = dialog.loadingMessageTextView
        messageTextVIew.setText(message)
        val animDrawable: AnimationDrawable = dialog.loadingImageView.drawable as AnimationDrawable
        animDrawable.start()

    }


    fun stopLoadingAnimation() {
        if (dialog != null) {
            val animDrawable: AnimationDrawable =
                dialog.loadingImageView.drawable as AnimationDrawable
            animDrawable.start()
            dialog.dismiss()
        }
    }

}