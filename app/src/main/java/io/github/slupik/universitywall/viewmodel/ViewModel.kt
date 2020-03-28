/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.viewmodel

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import androidx.lifecycle.ViewModel as AndroidViewModel

/**
 * Created by Sebastian Witasik on 02.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
abstract class ViewModel : AndroidViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    protected fun Disposable.remember() {
        disposables.add(this)
    }

    protected fun disposeAll() {
        disposables.dispose()
    }

}