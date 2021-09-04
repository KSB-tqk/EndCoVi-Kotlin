package cf.khanhsb.kotlinchatapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import cf.khanhsb.kotlinchatapp.databinding.FragmentHomeBinding
import cf.khanhsb.kotlinchatapp.viewmodel.HomeViewModel


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(){
    override fun getViewBinding()= FragmentHomeBinding.inflate(layoutInflater)

    override fun getViewModelClass() = HomeViewModel::class.java

    override fun setUpViews() {
        binding.logOutButton.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        viewModel.signOut()
        observeSignOut()
    }

    private fun observeSignOut() {
        viewModel.signOutStatus.observe(viewLifecycleOwner, Observer { result ->
            result?.let {
                if (it.toString().equals("Success(Sign Out Successfully)")) {
                    Toast.makeText(this.requireActivity(), "Sign Out Successfully", Toast.LENGTH_SHORT)
                        .show()
                    navigateToLoginFragment()
                } else {
                    Toast.makeText(
                        this.requireActivity(),
                        "Sign Out failed with ${it}",
                        Toast.LENGTH_SHORT
                    ).show()
                    println(it.toString())
                }
            }
        })
    }

    private fun navigateToLoginFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }
}