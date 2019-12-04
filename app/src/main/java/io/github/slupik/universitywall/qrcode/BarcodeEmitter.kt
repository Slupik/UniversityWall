/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.qrcode

import com.google.android.gms.vision.barcode.Barcode
import io.reactivex.rxjava3.core.Observable

/**
 * Created by Sebastian Witasik on 04.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
interface BarcodeEmitter {

    val selected: Observable<Barcode>

}