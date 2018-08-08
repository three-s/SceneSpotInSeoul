package com.threes.scenespotinseoul.utilities

fun runOnMain(execute: () -> Unit) {
    AppExecutors().mainThread().execute(execute)
}

fun runOnDiskIO(execute: () -> Unit) {
    AppExecutors().diskIO().execute(execute)
}

fun runOnNetworkIO(execute: () -> Unit) {
    AppExecutors().networkIO().execute(execute)
}