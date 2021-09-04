package cf.khanhsb.kotlinchatapp.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel: ViewModel() {
    private var auth: FirebaseAuth? = null

    init {
        auth = FirebaseAuth.getInstance()
    }

    private val _signUpStatus = MutableLiveData<Result<String>>()
    val signUpStatus: LiveData<Result<String>> = _signUpStatus

    fun signUp(email: String, password: String) {
        try {
            auth?.let { authentication ->
                authentication.createUserWithEmailAndPassword(
                    email,
                    password
                ).addOnCompleteListener { task: Task<AuthResult> ->
                    if (!task.isSuccessful) {
                        println("Registration fail with ${task.exception}")
                        _signUpStatus.postValue(Result.success("Sign Up failed with ${task.exception}"))
                    } else {
                        _signUpStatus.postValue(Result.success("Sign Up successful"))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}