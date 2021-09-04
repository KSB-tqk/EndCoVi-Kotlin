package cf.khanhsb.kotlinchatapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VBinding : ViewBinding, VM : ViewModel> : Fragment() {
    protected lateinit var binding: VBinding
    protected abstract fun getViewBinding(): VBinding

    protected lateinit var viewModel: VM
    protected abstract fun getViewModelClass(): Class<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeData()
    }

    open fun setUpViews() {}

    open fun observeData() {}

    open fun observeView() {}

    private fun init() {
        binding = getViewBinding()
        viewModel = ViewModelProvider(this).get(getViewModelClass())
    }
}