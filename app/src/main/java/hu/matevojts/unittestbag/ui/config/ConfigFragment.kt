package hu.matevojts.unittestbag.ui.config

import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

class ConfigFragment : Fragment() {

    private val viewModel by inject<ConfigViewModel>()
    private var foregroundDisposables = CompositeDisposable()

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
