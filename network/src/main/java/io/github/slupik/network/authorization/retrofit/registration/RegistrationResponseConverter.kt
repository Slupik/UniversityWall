/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.authorization.retrofit.registration

import io.github.slupik.model.authorization.registration.RegistrationResult
import io.github.slupik.network.ResponseConverter
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class RegistrationResponseConverter @Inject constructor() :
    ResponseConverter<RegistrationResponse, RegistrationResult>() {

    override fun convert(response: RegistrationResponse): RegistrationResult {
        if (!response.validLogin) {
            return RegistrationResult.INVALID_LOGIN
        }
        if (!response.validPassword) {
            return RegistrationResult.INVALID_PASSWORD
        }
        if (response.token.isEmpty()) {
            return RegistrationResult.CONNECTION_ERROR
        }
        return RegistrationResult.SUCCESS
    }

}