package hu.matevojts.unittestbag

import android.app.Application
import hu.matevojts.unittestbag.di.appModule
import io.reactivex.plugins.RxJavaPlugins
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class UnitTestBagApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
            androidContext(this@UnitTestBagApplication)
        }

        RxJavaPlugins.setErrorHandler { Timber.e(it) }
    }
}
