package hu.matevojts.unittestbag.ui.openedbag

import androidx.annotation.DrawableRes
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class BagItemViewModel(@Bindable val bagItem: BagItem) : BaseObservable()

data class BagItem(
    @DrawableRes val imageResId: Int,
    val title: String,
    val description: String
)
