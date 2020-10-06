package hu.matevojts.unittestbag.ui.openedbag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.matevojts.unittestbag.databinding.FragmentOpenedBagBinding
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

class OpenedBagFragment : Fragment() {

    private val viewModel by inject<OpenedBagViewModel>()
    private var foregroundDisposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentOpenedBagBinding
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
