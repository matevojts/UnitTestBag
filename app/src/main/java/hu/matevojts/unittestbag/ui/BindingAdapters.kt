package hu.matevojts.unittestbag.ui

import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(value = ["items", "itemLayout"], requireAll = true)
fun setItems(recyclerView: RecyclerView, items: List<Any>, @LayoutRes layoutResId: Int) {
    val adapter = ViewModelAdapter(items, layoutResId, BR.vm)
    recyclerView.adapter = adapter
}

@BindingAdapter(value = ["orientation"])
fun setOrientation(recyclerView: RecyclerView, orientation: Int) {
    val layoutManager = LinearLayoutManager(recyclerView.context, orientation, false)
    recyclerView.layoutManager = layoutManager
}
