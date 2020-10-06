package hu.matevojts.unittestbag.di

import hu.matevojts.unittestbag.datasource.BagDataSource
import hu.matevojts.unittestbag.ui.config.ConfigViewModel
import hu.matevojts.unittestbag.ui.openedbag.OpenedBagViewModel
import org.koin.dsl.module

val appModule = module {
    factory { ConfigViewModel(get()) }
    factory { OpenedBagViewModel(get()) }
    single { BagDataSource() }
}

