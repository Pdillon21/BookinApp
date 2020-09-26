package com.example.bookin.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bookin.main.MainActivity
import com.example.bookin.R
import com.example.bookin.register.SignUpActivity
import com.example.bookin.entities.BookinActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.emailEt
import kotlinx.android.synthetic.main.activity_login.passwordEt
import com.example.bookin.entities.ErrorTypes as ErrorTypes

class LoginActivity : BookinActivity() {
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.context = this
        disableSignInButton()
        initObservers()

        emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(chars: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.emailTextChanged(chars.toString())
            }

        })

        passwordEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(chars: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.passwordTextChanged(chars.toString())
            }
        })

        signInBttn.setOnClickListener {
            this.startProgressDialogWithMessage("Signing in")
            viewModel.performLoginAttempt()
        }

        goToSignUpButton.setOnClickListener {
            navigateToSignUpActivity()
        }


        viewModel.validSignInData.observe(this, Observer { validSignInData ->
            Boolean
            if (validSignInData) {
                enableSignInButton()
            } else {
                disableSignInButton()
            }
        })

        autoLogAsd.setOnClickListener{
            logAsd()
        }



    }

    private fun logAsd() {
        val emailAsd : String = "asd@gmail.com"
        val passAsd : String = "asdasd"
        emailEt.setText(emailAsd)
        passwordEt.setText(passAsd)
        val view : View = findViewById(R.id.signInBttn)
        view.performClick()
    }


    private fun initObservers() {
        viewModel.validSignInData.observe(this, Observer { validSignInData ->
            Boolean
            if (validSignInData) {
                enableSignInButton()
            } else {
                disableSignInButton()
            }
        })

        viewModel.errorEmailMessage.observe(this, Observer { errorEmailMessage -> String
            when (errorEmailMessage){
                null -> displayEmailErrorMessage(null)
                ErrorTypes.EMAIL_FIELD_INVALID_INPUT.toString() -> displayEmailErrorMessage(ErrorTypes.EMAIL_FIELD_INVALID_INPUT)
                ErrorTypes.EMAIL_FIELD_EMPTY.toString() -> displayEmailErrorMessage(ErrorTypes.EMAIL_FIELD_EMPTY)
            }
        })

        viewModel.errorPasswordMessage.observe(this, Observer {errorPassMessage -> String
            when (errorPassMessage){
                null -> displayPassErrorMessage(null)
                ErrorTypes.PASSWORD_FIELD_INVALID_INPUT.toString() -> displayPassErrorMessage(ErrorTypes.PASSWORD_FIELD_INVALID_INPUT)
                ErrorTypes.PASSWORD_FIELD_EMPTY.toString() -> displayPassErrorMessage(ErrorTypes.PASSWORD_FIELD_EMPTY)
            }
        })
    }

    private fun displayEmailErrorMessage (errorTypes: ErrorTypes?){
        //ToDo set errorType message
        // falta agregar un textView abajo del mail para mostrar el tipo de error
        when (errorTypes){
            ErrorTypes.EMAIL_FIELD_EMPTY -> {
                emailEt.background.setTint(resources.getColor(R.color.errorRed))
            }
            ErrorTypes.EMAIL_FIELD_INVALID_INPUT -> {
                emailEt.background.setTint(resources.getColor(R.color.errorRed))
            }
            null -> {
               emailEt.background.setTint(resources.getColor(R.color.baseGrey))
            }
        }
    }

    private fun displayPassErrorMessage (errorTypes: ErrorTypes?){
        //ToDo set errorType message
        // falta agregar un textView abajo del password para mostrar el tipo de error
        when (errorTypes){
            ErrorTypes.PASSWORD_FIELD_EMPTY -> {
                passwordEt.background.setTint(resources.getColor(R.color.errorRed))

            }
            ErrorTypes.PASSWORD_FIELD_INVALID_INPUT -> {
                passwordEt.background.setTint(resources.getColor(R.color.errorRed))

            }
            null -> {
                passwordEt.background.setTint(resources.getColor(R.color.baseGrey))
            }
        }
    }

    private fun enableSignInButton() {
        signInBttn.isClickable = true
        signInBttn.isEnabled = true
    }

    private fun disableSignInButton() {
        signInBttn.isClickable = false
        signInBttn.isEnabled = false
    }

    fun navigateToMainActivity (){
        MainActivity.navigate(this)
    }


    fun navigateToSignUpActivity (){
        SignUpActivity.navigate(this)
    }

    companion object {
        fun navigate(context: Activity) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

}
