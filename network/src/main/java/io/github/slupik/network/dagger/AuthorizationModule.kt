/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.dagger

import dagger.Binds
import dagger.Module
import io.github.slupik.model.authorization.authorizer.AuthorizationResult
import io.github.slupik.model.authorization.authorizer.Authorizer
import io.github.slupik.model.authorization.registration.Registrar
import io.github.slupik.model.authorization.registration.RegistrationResult
import io.github.slupik.model.authorization.state.AuthorizationStateProvider
import io.github.slupik.network.ResponseConverter
import io.github.slupik.network.authorization.ServerAuthorizationStateProvider
import io.github.slupik.network.authorization.authorizer.ServerAuthorizer
import io.github.slupik.network.authorization.registrar.ServerAwareRegistrar
import io.github.slupik.network.authorization.retrofit.authorization.AuthorizationResponse
import io.github.slupik.network.authorization.retrofit.authorization.AuthorizationResponseConverter
import io.github.slupik.network.authorization.retrofit.registration.RegistrationResponse
import io.github.slupik.network.authorization.retrofit.registration.RegistrationResponseConverter
import io.github.slupik.network.authorization.token.ServerTokenHolder
import io.github.slupik.network.authorization.token.TokenHolder

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
abstract class AuthorizationModule {

    @Binds
    abstract fun provideAuthorizer(authorizer: ServerAuthorizer):
            Authorizer

    @Binds
    abstract fun authorizationResponseConverter(converter: AuthorizationResponseConverter):
            ResponseConverter<AuthorizationResponse, AuthorizationResult>

    @Binds
    abstract fun provideRegistrar(registrar: ServerAwareRegistrar):
            Registrar

    @Binds
    abstract fun registrationResponseConverter(converter: RegistrationResponseConverter):
            ResponseConverter<RegistrationResponse, RegistrationResult>

    @Binds
    abstract fun authorizationStateProvider(stateProvider: ServerAuthorizationStateProvider):
            AuthorizationStateProvider

    @Binds
    abstract fun tokenHolder(holder: ServerTokenHolder):
            TokenHolder

}