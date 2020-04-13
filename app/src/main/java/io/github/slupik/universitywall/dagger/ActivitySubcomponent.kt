/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.dagger

import dagger.Subcomponent
import io.github.slupik.model.dagger.ActivityScope
import io.github.slupik.universitywall.screen.group.GroupsFragment
import io.github.slupik.universitywall.screen.group.GroupsViewModel
import io.github.slupik.universitywall.screen.messages.MessagesFragment
import io.github.slupik.universitywall.screen.messages.MessagesViewModel
import io.github.slupik.universitywall.screen.qrcode.activity.QrCodeScannerActivity
import io.github.slupik.universitywall.screen.registration.RegistrationFragment
import io.github.slupik.universitywall.screen.registration.RegistrationViewModel

/**
 * Created by Sebastian Witasik on 11.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@ActivityScope
@Subcomponent(
    modules = [
        ActivityModule::class,
        RegistrationModule::class,
        AssistedInjectModule::class
    ]
)
interface ActivitySubcomponent {

    val registrationViewModelFactory: RegistrationViewModel.Factory
    val messagesViewModelFactory: MessagesViewModel.Factory
    val groupsViewModelFactory: GroupsViewModel.Factory

    fun inject(clazz: RegistrationFragment)
    fun inject(clazz: QrCodeScannerActivity)
    fun inject(clazz: GroupsFragment)
    fun inject(clazz: MessagesFragment)

}