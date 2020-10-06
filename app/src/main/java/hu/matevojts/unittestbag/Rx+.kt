package hu.matevojts.unittestbag

import com.jakewharton.rxrelay2.Relay

fun Relay<Unit>.trigger() = this.accept(Unit)
