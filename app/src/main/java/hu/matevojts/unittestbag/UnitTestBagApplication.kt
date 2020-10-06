package hu.matevojts.unittestbag

import android.app.Application
import hu.matevojts.unittestbag.di.appModule
import org.koin.core.context.startKoin

class UnitTestBagApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
        }
    }
}
