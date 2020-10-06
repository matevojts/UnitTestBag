package hu.matevojts.unittestbag.ui.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.matevojts.unittestbag.databinding.FragmentConfigBinding
import io.reactivex.disposables.CompositeDisposable
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
    }

    override fun onPause() {
        super.onPause()
        viewModel.onViewPaused()
        foregroundDisposables.clear()
    }
}
