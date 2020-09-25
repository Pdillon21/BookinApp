package com.example.bookin.login

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookin.entities.ErrorTypes
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class LoginViewModel : ViewModel() {
    lateinit var context : LoginActivity
    var email= MutableLiveData<String>()
    var password= MutableLiveData<String>()

    var isEmailValid : Boolean = false
    var isPasswordValid : Boolean = false

    var validSignInData = MutableLiveData<Boolean>()

    var errorEmailMessage = MutableLiveData<String>()
    var errorPasswordMessage = MutableLiveData<String>()

    fun emailTextChanged(emailString : String) {
        if (emailString.isEmpty()){
            isEmailValid = false
            errorEmailMessage.value = ErrorTypes.EMAIL_FIELD_EMPTY.toString()
        } else if (!emailString.contains("@")){
            isEmailValid = false
            errorEmailMessage.value = ErrorTypes.EMAIL_FIELD_INVALID_INPUT.toString()
        } else {
            errorEmailMessage.value = null
            isEmailValid = true
        }
        email.value = emailString
        validateSignInButton()
    }

    fun setThisContext (anActivity: Activity){
        context = anActivity as LoginActivity
    }


    fun passwordTextChanged(passString : String){
        if (passString.isEmpty()){
            isPasswordValid = false
            errorPasswordMessage.value = ErrorTypes.PASSWORD_FIELD_EMPTY.toString()
        } else if (passString.length<6){
            isPasswordValid = false
            errorPasswordMessage.value = ErrorTypes.PASSWORD_FIELD_INVALID_INPUT.toString()
        } else {
            errorPasswordMessage.value =null
            isPasswordValid = true
        }
        password.value = passString
        validateSignInButton()
    }

    fun validateSignInButton(){
        validSignInData.value = isEmailValid && isPasswordValid
    }




    fun performLoginAttempt(){
        signInWithUser(email.value.toString(),password.value.toString())

    }

    private fun signInWithUser (email : String , password :  String){
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email.toString(),password.toString())
            .addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful){
                    context.stopProgressDialog()
                    context.navigateToMainActivity()

                } else {
                    var exception : Exception? = it.exception
                    var errorMessage : String = exception?.message.toString()
                    if (errorMessage.isBlank()){
                        errorMessage = "Something went wrong"
                    }
                    context.stopProgressDialog()
                    context.showCustomDialogDismissable("Oops!",errorMessage)
                }
            })
    }






}