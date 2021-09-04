package cf.khanhsb.kotlinchatapp.view

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import cf.khanhsb.kotlinchatapp.BaseFragment
import cf.khanhsb.kotlinchatapp.R
import cf.khanhsb.kotlinchatapp.databinding.FragmentSignUpBinding
import cf.khanhsb.kotlinchatapp.viewmodel.SignUpViewModel


class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>() {
    override fun getViewBinding() = FragmentSignUpBinding.inflate(layoutInflater)

    override fun getViewModelClass() = SignUpViewModel::class.java

    override fun setUpViews() {
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
                    if (binding.confirmPasswordEditText.text.toString()
                            .equals(binding.passwordEditText.text.toString())
                    ) {
                        val email: String = binding.emailEditText.text.toString().trim { it <= ' ' }
                        val password: String =
                            binding.passwordEditText.text.toString().trim { it <= ' ' }
                        signUp(email, password)
                        navigateToLoginFragment()
                    } else {
                        Toast.makeText(
                            this.requireActivity(),
                            "Your confirmed password is Incorrect",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.navToSignInButton.setOnClickListener {
            navigateToLoginFragment()
        }
    }

    private fun observeSignIn() {
        viewModel.signUpStatus.observe(viewLifecycleOwner, Observer { result ->
            result?.let {
                if (it.toString().equals("Success(Sign Up successful)")) {
                    Toast.makeText(this.requireActivity(), "Sign Up Successfully", Toast.LENGTH_SHORT)
                        .show()
                    navigateToLoginFragment()
                } else {
                    Toast.makeText(
                        this.requireActivity(),
                        "Sign up failed with ${it}",
                        Toast.LENGTH_SHORT
                    ).show()
                    println(it.toString())
                }
            }
        })
    }

    private fun navigateToLoginFragment() {
        findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
    }

    private fun signUp(email: String, password: String) {
        viewModel.signUp(email, password)
        observeSignIn()
    }
}

