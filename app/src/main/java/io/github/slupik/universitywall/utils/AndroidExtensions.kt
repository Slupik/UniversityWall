/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by Sebastian Witasik on 28.03.2020.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
inline fun <T> LiveData<T>.subscribe(lifecycle: LifecycleOwner, crossinline onChanged: (T) -> Unit) {
    observe(lifecycle, Observer { it?.run(onChanged) })
}

fun <T> Observable<T>.observeOnMainThread(): Observable<T> =
    observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.observeOnMainThread(): Single<T> =
    observeOn(AndroidSchedulers.mainThread())

fun Completable.observeOnMainThread(): Completable =
    observeOn(AndroidSchedulers.mainThread())