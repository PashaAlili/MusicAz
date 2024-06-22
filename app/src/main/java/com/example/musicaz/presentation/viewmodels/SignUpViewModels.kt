package com.example.musicaz.presentation.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns
import androidx.lifecycle.ViewModel

class SignUpViewModel : ViewModel() {
    var email : String = ""
    var password : String = ""
    var name : String = ""
    var selectedGender : Gender = Gender.MALE
    var errorMessage: String? = ""


    fun signUp() {

    }


    private fun isValidEmail(email: String?): Boolean {
        if(email.isNullOrBlank()) {
            return false
        }

        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String?): Boolean {
        if(password.isNullOrBlank()) {
            return false
        }
        return password.length >= 8
    }

    private fun validationErrorMessage(email: String?, password: String?): ErrorType? {
        if(!isValidEmail(email)) {
            return ErrorType.EMAIL
        }

        if(!isValidPassword(password)) {
            return ErrorType.PASSWORD
        }

        return null
    }

    fun genders(): List<Gender> {
        return listOf(Gender.MALE, Gender.FEMALE, Gender.OTHER)
    }

    fun hasErrorMessageWithType() : Pair<ErrorType?, Boolean> {
        if (inputsValidation() != null) {
            return Pair(inputsValidation(), true)
        }
        return Pair(null, false)
    }

    private fun inputsValidation(): ErrorType? {

        return this.validationErrorMessage(email, password)
    }
}

enum class Gender {
    MALE {
        override fun toString(): String {
            return "Erkek"
        }
    },
    FEMALE{
        override fun toString(): String {
            return "Kadin"
        }
    },
    OTHER{
        override fun toString(): String {
            return "Diger"
        }
    }
}

enum class ErrorType {
    EMAIL{
        override fun toString(): String {
            return "Email Hatali"
        }
    },
    PASSWORD{
        override fun toString(): String {
            return "Sifre en az 8 karakter olmali"
        }
    }
}

fun oturumBilgileriniKaydet(context: Context, email: String, password: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("KullaniciBilgileri", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putString("email", email)
    editor.putString("password", password)
    editor.apply()
}

fun oturumBilgileriniOku(context: Context): Pair<String?, String?> {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("KullaniciBilgileri", Context.MODE_PRIVATE)
    val email = sharedPreferences.getString("email", null)
    val password = sharedPreferences.getString("password", null)
    return Pair(email, password)
}

fun oturumBilgileriniSil(context: Context) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("KullaniciBilgileri", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.clear()
    editor.apply()
}