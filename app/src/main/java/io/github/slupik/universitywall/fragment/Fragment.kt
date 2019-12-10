/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.fragment

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import androidx.fragment.app.Fragment as AndroidFragment


/**
 * Created by Sebastian Witasik on 02.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
abstract class Fragment: AndroidFragment() {

    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    protected fun Disposable.remember() {
        compositeDisposable.add(this)
    }

}