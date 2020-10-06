package hu.matevojts.unittestbag.ui.config.di

import hu.matevojts.unittestbag.ui.config.ConfigViewModel
import org.koin.dsl.module

val appModule = module {
    factory { ConfigViewModel() }
}

