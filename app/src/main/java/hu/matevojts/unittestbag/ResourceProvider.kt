package hu.matevojts.unittestbag

import android.content.Context
import androidx.annotation.StringRes

class ResourceProvider(private val context: Context) {

    fun getString(@StringRes stringRes: Int): String {
        return context.getString(stringRes)
    }

    @Suppress("SpreadOperator")
    fun getString(@StringRes resourceIdentifier: Int, vararg arguments: Any): String {
        return if (arguments.isNotEmpty())
            context.getString(resourceIdentifier, *arguments)
        else
            context.getString(resourceIdentifier)
    }
}
