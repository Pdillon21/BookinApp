package com.example.bookin.register

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bookin.R
import com.example.bookin.entities.BookinActivity
import com.example.bookin.entities.ErrorTypes
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.emailEt
import kotlinx.android.synthetic.main.activity_sign_up.passwordEt

class SignUpActivity : BookinActivity() {
    lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.context = this
        disableSignUpButton()
        initObservers()

        emailEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
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


        confirmPasswordEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(chars: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.confirmPassTextChanged(chars.toString())
            }
        })

        signUpBttn.setOnClickListener {
            this.startProgressDialog()
            viewModel.performSignUpAttempt(emailEt.text.toString(),passwordEt.text.toString())
        }

    }

    private fun initObservers() {
        viewModel.validSignUpData.observe(this, Observer { validSignUpData ->
            Boolean
            if (validSignUpData) {
                enableSignUpButton()
            } else {
                disableSignUpButton()
            }

        })


        viewModel.errorEmailMessage.observe(this, Observer { emailError ->
            String
            when (emailError) {
                null -> displayEmailErrorMessage(null)
                ErrorTypes.EMAIL_FIELD_INVALID_INPUT.toString() -> displayEmailErrorMessage(
                    ErrorTypes.EMAIL_FIELD_INVALID_INPUT
                )
                ErrorTypes.EMAIL_FIELD_EMPTY.toString() -> displayEmailErrorMessage(ErrorTypes.EMAIL_FIELD_EMPTY)
            }
        })

        viewModel.errorPasswordMessage.observe(this, Observer { passError ->
            String
            when (passError) {
                null -> displayPassErrorMessage(null)
                ErrorTypes.PASSWORD_FIELD_INVALID_INPUT.toString() -> displayPassErrorMessage(
                    ErrorTypes.PASSWORD_FIELD_INVALID_INPUT
                )
                ErrorTypes.PASSWORD_FIELD_EMPTY.toString() -> displayPassErrorMessage(ErrorTypes.PASSWORD_FIELD_EMPTY)
            }
        })

        viewModel.errorConfirmPassMessage.observe(this, Observer { confirPassError ->
            String
            when (confirPassError) {
                null -> displayConfirmErrorMessage(null)
                ErrorTypes.PASSWORDS_DO_NOT_MATCH.toString() -> displayConfirmErrorMessage(
                    ErrorTypes.PASSWORDS_DO_NOT_MATCH
                )
            }
        })

        viewModel.passwordStrength.observe(this, Observer { strengthValue -> Int
            displayPassStrength(strengthValue)
            if (passwordEt.text.isEmpty()){
                strengthPassTV.visibility = View.INVISIBLE
            } else {
                strengthPassTV.visibility = View.VISIBLE
            }
        })


    }

    private fun disableSignUpButton() {
        signUpBttn.isClickable = false
        signUpBttn.isEnabled = false
    }

    private fun enableSignUpButton() {
        signUpBttn.isClickable = true
        signUpBttn.isEnabled = true
    }

    private fun displayEmailErrorMessage(errorTypes: ErrorTypes?) {
        //ToDo set errorType message
        when (errorTypes) {
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

    private fun displayPassErrorMessage(errorTypes: ErrorTypes?) {
        //ToDo set errorType message
        when (errorTypes) {
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

    private fun displayConfirmErrorMessage(errorTypes: ErrorTypes?) {
        when (errorTypes) {
            null -> confirmPasswordEt.background.setTint(resources.getColor(R.color.baseGrey))
            ErrorTypes.PASSWORDS_DO_NOT_MATCH -> confirmPasswordEt.background.setTint(resources.getColor(R.color.errorRed))
        }
    }

    private fun displayPassStrength(strength: Int) {
        //The strength value con only go from 0 to 5
        when (strength) {
            0 -> {
                strengthPassTV.visibility = View.INVISIBLE
            }
            1 -> {
                strengthPassTV.visibility = View.VISIBLE
                strengthPassTV.setText("Weak")
                strengthPassTV.setTextColor(resources.getColor(R.color.errorRed))
            }
            2 -> {
                strengthPassTV.visibility = View.VISIBLE
                strengthPassTV.setText("Fair")
                strengthPassTV.setTextColor(resources.getColor(R.color.mainGold))
            }
            3 -> {
                strengthPassTV.visibility = View.VISIBLE
                strengthPassTV.setText("Good")
                strengthPassTV.setTextColor(resources.getColor(R.color.mainBlue))
            }
            4 -> {
                strengthPassTV.visibility = View.VISIBLE
                strengthPassTV.setText("Strong")
                strengthPassTV.setTextColor(resources.getColor(R.color.softGreen))
            }
            5 -> {
                strengthPassTV.visibility = View.VISIBLE
                strengthPassTV.setText("Very Strong")
                strengthPassTV.setTextColor(resources.getColor(R.color.strongGreen))
            }
        }
    }

    companion object {
        fun navigate(context: Activity) {
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}
