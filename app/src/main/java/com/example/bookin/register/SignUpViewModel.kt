package com.example.bookin.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookin.entities.ErrorTypes
import java.util.regex.Matcher
import java.util.regex.Pattern
import com.example.bookin.main.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception


class SignUpViewModel : ViewModel() {
    lateinit var context: SignUpActivity
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var confirmPassword = MutableLiveData<String>()

    var emailIsValid: Boolean = false
    var passwordIsValid: Boolean = false
    var passwordsMatch: Boolean = false

    var errorEmailMessage = MutableLiveData<String>()
    var errorPasswordMessage = MutableLiveData<String>()
    var errorConfirmPassMessage = MutableLiveData<String>()

    var validSignUpData = MutableLiveData<Boolean>()

    var passwordStrength = MutableLiveData<Int>()

    fun emailTextChanged(emailString: String) {
        if (emailString.isEmpty()) {
            emailIsValid = false
            errorEmailMessage.value = ErrorTypes.EMAIL_FIELD_EMPTY.toString()
        } else if (!emailString.contains("@")) {
            emailIsValid = false
            errorEmailMessage.value = ErrorTypes.EMAIL_FIELD_INVALID_INPUT.toString()
        } else {
            errorEmailMessage.value = null
            emailIsValid = true
        }
        email.value = emailString
        validateSignUpButton()
    }

    fun passwordTextChanged(passString: String) {
        passwordStrengthCheck(passString)
        if (passString.isEmpty()) {
            passwordIsValid = false
            errorPasswordMessage.value = ErrorTypes.PASSWORD_FIELD_EMPTY.toString()
        } else if (passString.length < 6) {
            passwordIsValid = false
            errorPasswordMessage.value = ErrorTypes.PASSWORD_FIELD_INVALID_INPUT.toString()
        } else {
            errorPasswordMessage.value = null
            passwordIsValid = true
        }
        password.value = passString
        validateSignUpButton()
    }

    private fun passwordStrengthCheck(passString: String) {
        var strengthValue: Int = 0

        if (containsSpecialCharacters(passString)) {
            strengthValue += 1
        }
        if (containsMixedCases(passString)) {
            strengthValue += 1
        }

        if (containsNumbers(passString)) {
            strengthValue += 1
        }

        if (passString.length in 8..9) {
            strengthValue += 1
        } else if (passString.length >= 10) {
            strengthValue += 2
        }

        passwordStrength.value = strengthValue

    }

    private fun containsNumbers(passString: String): Boolean {
        val pattern = Pattern.compile("([0-9])")
        val matcher = pattern.matcher(passString)
        return matcher.find()
    }

    private fun containsMixedCases(passString: String): Boolean {
        var isAllUperCase = false
        var isAllLowerCase = false

        if (passString == passString.toLowerCase()) {
            isAllLowerCase = true
        }
        if (passString == passString.toUpperCase()) {
            isAllUperCase = true
        }

        return !isAllLowerCase && !isAllUperCase
    }

    private fun containsSpecialCharacters(string: String): Boolean {
        var pattern: Pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
        var mathcer: Matcher = pattern.matcher(string)
        return mathcer.find()
    }

    fun confirmPassTextChanged(confirmPass: String) {
        passwordsMatch = password.value == confirmPass
        if (!passwordsMatch) {
            errorConfirmPassMessage.value = ErrorTypes.PASSWORDS_DO_NOT_MATCH.toString()
        } else {
            errorConfirmPassMessage.value = null
        }
        validateSignUpButton()
    }

    fun performSignUpAttempt(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                OnCompleteListener {
                    if (it.isSuccessful) {
                        context.stopProgressDialog()
                        MainActivity.navigate(context)
                    } else {
                        var exception : Exception? = it.exception
                        var errorMessage : String = exception?.message.toString()

                        context.stopProgressDialog()
                        context.showCustomDialogDismissable("Oops!",errorMessage)
                    }
                }
            )
    }


    fun validateSignUpButton() {
        validSignUpData.value = emailIsValid && passwordIsValid && passwordsMatch
    }

}