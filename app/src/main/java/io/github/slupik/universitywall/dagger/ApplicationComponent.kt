/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.dagger

import dagger.Component
import io.github.slupik.model.dagger.ModelModule
import io.github.slupik.network.dagger.NetworkModule
import io.github.slupik.repository.dagger.RepositoryModule
import io.github.slupik.universitywall.MainActivity
import io.github.slupik.universitywall.screen.login.LoginFragment
import io.github.slupik.universitywall.screen.login.LoginViewModel
import io.github.slupik.universitywall.screen.messages.MessagesFragment
import io.github.slupik.universitywall.screen.qrcode.ui.scanner.QrCodeScannerFragment
import io.github.slupik.universitywall.screen.qrcode.ui.scanner.element.BarcodeGraphic
import javax.inject.Singleton

/**
 * Created by Sebastian Witasik on 06.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Component(
    modules = [
        ModelModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ContextModule::class,
        InvitationModule::class,
        MessagesModule::class,
        GroupsModule::class,
        AssistedInjectModule::class
    ]
)
@Singleton
interface ApplicationComponent {

    val loginViewModelFactory: LoginViewModel.Factory

    fun plus(subModule: ActivityModule): ActivitySubcomponent

    fun inject(clazz: BarcodeGraphic)
    fun inject(clazz: QrCodeScannerFragment)
    fun inject(clazz: LoginFragment)
    fun inject(clazz: MessagesFragment)
    fun inject(clazz: MainActivity)

}