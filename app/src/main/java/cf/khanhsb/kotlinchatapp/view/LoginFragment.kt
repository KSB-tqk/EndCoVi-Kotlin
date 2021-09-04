package cf.khanhsb.kotlinchatapp.view

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cf.khanhsb.kotlinchatapp.BaseFragment
import cf.khanhsb.kotlinchatapp.R
import cf.khanhsb.kotlinchatapp.databinding.FragmentLoginBinding
import cf.khanhsb.kotlinchatapp.viewmodel.LoginViewModel


class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override fun getViewBinding() = FragmentLoginBinding.inflate(layoutInflater)

    override fun getViewModelClass() = LoginViewModel::class.java

    override fun setUpViews() {
        binding.signInButton.setOnClickListener {
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

                else -> {
                    val email: String = binding.emailEditText.text.toString().trim { it <= ' ' }
                    val password: String =
                        binding.passwordEditText.text.toString().trim { it <= ' ' }
                    signIn(email, password)
                }
            }
        }

        binding.navToSignUpButton.setOnClickListener {
            navigateToSignUpFragment()
        }
    }

    private fun signIn(email: String, password: String) {
        viewModel.signIn(email, password)
        observeSignIn()
    }

    private fun observeSignIn() {
        viewModel.signInStatus.observe(viewLifecycleOwner, Observer { result ->
            result?.let {
                if (it.toString().equals("Success(Sign in Successful)")) {
                    Toast.makeText(this.requireActivity(), "Login Successfully", Toast.LENGTH_SHORT)
                        .show()
                    viewModel.resetSignInLiveData()
                    navigateToHomeFragment()
                } else if (it.toString() == "Success(Reset)") {
                    //do nothing
                } else {
                    Toast.makeText(
                        this.requireActivity(),
                        "Login failed with ${it}",
                        Toast.LENGTH_SHORT
                    ).show()
                    println(it.toString())
                }
            }
        })
    }

    private fun navigateToHomeFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun navigateToSignUpFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment2)
    }

}