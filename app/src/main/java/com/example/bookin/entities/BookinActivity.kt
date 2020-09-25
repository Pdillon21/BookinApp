package com.example.bookin.entities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("Registered")
open class BookinActivity : AppCompatActivity() {
    private var customLoadingDialog: CustomLoadingDialog = CustomLoadingDialog(this)
    private var customDialogPopUp: CustomDialogPopUp = CustomDialogPopUp(this)


    fun startProgressDialog() {

        customLoadingDialog.startLoadingAnimation()

    }

    fun startProgressDialogWithMessage(message: String) {

        customLoadingDialog.startLoadingAnimationWithMessage(message)

    }

    fun stopProgressDialog() {

        customLoadingDialog.stopLoadingAnimation()

    }

    fun showCustomDialogDismissable(title: String, body: String) {

        customDialogPopUp.showDismissableWarning(title, body)

    }


}