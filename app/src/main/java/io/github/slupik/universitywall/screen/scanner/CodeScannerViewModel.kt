/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.scanner

import com.squareup.inject.assisted.AssistedInject
import io.github.slupik.universitywall.viewmodel.ViewModel

class CodeScannerViewModel @AssistedInject constructor(

) : ViewModel() {

    @AssistedInject.Factory
    interface Factory {
        fun create(): CodeScannerViewModel
    }

}