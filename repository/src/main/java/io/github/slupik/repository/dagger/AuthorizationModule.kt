/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.repository.dagger

import dagger.Binds
import dagger.Module
import io.github.slupik.model.authorization.credentials.CredentialSaver
import io.github.slupik.model.authorization.credentials.CredentialsProvider
import io.github.slupik.repository.credential.SharedPreferencesCredentialRepository

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
abstract class AuthorizationModule {

    @Binds
    abstract fun credentialSaver(repository: SharedPreferencesCredentialRepository): CredentialSaver

    @Binds
    abstract fun credentialProvider(repository: SharedPreferencesCredentialRepository): CredentialsProvider

}