package cf.khanhsb.kotlinchatapp

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import cf.khanhsb.kotlinchatapp.databinding.FragmentSignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)


        binding.navToSignInButton.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        binding.signUpButton.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.emailEditText.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this.requireActivity(),
                        "Please enter your email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(binding.passwordEditText.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this.requireActivity(),
                        "Please enter your password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(
                    binding.confirmPasswordEditText.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(
                        this.requireActivity(),
                        "Please confirm your password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val email: String = binding.emailEditText.text.toString().trim { it <= ' ' }
                    val password: String = binding.emailEditText.text.toString().trim { it <= ' ' }

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                Toast.makeText(
                                    this.requireActivity(),
                                    "Register Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                Navigation.findNavController(binding.root)
                                    .navigate(R.id.action_signUpFragment_to_loginFragment)

                            } else {
                                Toast.makeText(
                                    this.requireActivity(),
                                    "Register Fail",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                }
            }
        }

        return binding.root
    }
}