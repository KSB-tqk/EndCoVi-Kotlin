package cf.khanhsb.kotlinchatapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private var auth: FirebaseAuth? = null
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        auth = FirebaseAuth.getInstance()
    }

    private val _signInStatus = MutableLiveData<Result<String>>()
    val signInStatus: LiveData<Result<String>> = _signInStatus

    fun signIn(email: String, password: String) {
        try {
            auth?.let { login ->
                login.signInWithEmailAndPassword(
                    email,
                    password
                ).addOnCompleteListener { task: Task<AuthResult> ->
                    if (!task.isSuccessful) {
                        println("Login failed with ${task.exception}")
                        _signInStatus.postValue(Result.success("Sign In failed with ${task.exception}"))
                    } else {
                        _signInStatus.postValue(Result.success("Sign in Successful"))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }






    fun resetSignInLiveData() {
        _signInStatus.postValue(Result.success("Reset"))
    }

}