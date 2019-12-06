/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.dagger

import dagger.Component
import io.github.slupik.universitywall.screen.qrcode.ui.scanner.element.BarcodeGraphic

/**
 * Created by Sebastian Witasik on 06.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Component(
    modules = [
        InvitationModule::class
    ]
)
interface BarcodeGraphicComponent {

    fun inject(clazz: BarcodeGraphic)

}