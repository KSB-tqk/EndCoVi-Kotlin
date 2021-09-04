package cf.khanhsb.kotlinchatapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel: ViewModel() {
    private var auth: FirebaseAuth? = null

    init {
        auth = FirebaseAuth.getInstance()
    }

    private val _signOutStatus = MutableLiveData<Result<String>>()
    val signOutStatus: LiveData<Result<String>> = _signOutStatus

    fun signOut() {
        try {
            auth?.let { authentication ->
                authentication.signOut()
                _signOutStatus.postValue(Result.success("Sign Out Successfully"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}