/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model

import org.junit.jupiter.api.Test

/**
 * Created by Sebastian Witasik on 06.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
internal class HashInvitationFactoryTest {

    private val sut: HashInvitationFactory = HashInvitationFactory()

    @Test
    fun `apply correct data`() {
        val data = "universitywall_invite_0Inżynieria Oprogramowania InformatykaB9DFCC0B25A8BEC063EA7B8F0F790BE63DE4AB7A373DFA8B1EBF4F47B009186D"

        val result = sut.create(data)

        result.test().assertValue {
            it.type == InvitationType.INFINITE
        }
        result.test().assertValue {
            it.link.endsWith("B9DFCC0B25A8BEC063EA7B8F0F790BE63DE4AB7A373DFA8B1EBF4F47B009186D")
        }
        result.test().assertValue {
            it.description == "Inżynieria Oprogramowania Informatyka"
        }
    }

}