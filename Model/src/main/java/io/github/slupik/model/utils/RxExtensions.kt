/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.utils

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by Sebastian Witasik on 28.03.2020.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
fun <T> Observable<T>.subscribeOnIOThread(): Observable<T> =
    subscribeOn(Schedulers.io())

fun <T> Single<T>.subscribeOnIOThread(): Single<T> =
    subscribeOn(Schedulers.io())