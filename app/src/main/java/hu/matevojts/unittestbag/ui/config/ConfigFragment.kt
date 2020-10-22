package hu.matevojts.unittestbag.ui.config

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.matevojts.unittestbag.R
import hu.matevojts.unittestbag.databinding.FragmentConfigBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import org.koin.android.ext.android.inject

class ConfigFragment : Fragment() {

    private val viewModel by inject<ConfigViewModel>()
    private var foregroundDisposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentConfigBinding
            .inflate(inflater, container, false)
            .apply { vm = viewModel }
            .root

    override fun onResume() {
        super.onResume()
        viewModel.onViewResumed()
        foregroundDisposables = CompositeDisposable()

        viewModel.output.openBag
            .subscribe {
                view?.hideKeyboard()
                this.findNavController().navigate(ConfigFragmentDirections.actionToOpenedBagFragment())
            }
            .addTo(foregroundDisposables)

        viewModel.output.invalidItems
            .subscribe {
                Toast.makeText(requireContext(), R.string.config_empty_bag_error_message, Toast.LENGTH_SHORT).show()
            }
            .addTo(foregroundDisposables)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onViewPaused()
        foregroundDisposables.clear()
    }

    private fun View.hideKeyboard() {
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.hideSoftInputFromWindow(this.windowToken, 0)
    }
}
