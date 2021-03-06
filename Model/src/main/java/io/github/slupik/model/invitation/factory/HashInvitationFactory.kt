/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.invitation.factory

import io.github.slupik.model.invitation.model.Invitation
import io.github.slupik.model.invitation.model.InvitationType
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 05.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
private const val HASH_LENGTH = 64
private const val INVITATION_PREFIX = "universitywall_invite_";
private val PATTERN = Regex("$INVITATION_PREFIX[0-1].{0,163}([A-Z]|[0-9]){$HASH_LENGTH}")
private const val URL_PATH = "https://coolapp.com/join/"

class HashInvitationFactory @Inject constructor() :
    InvitationFactory {

    override fun create(raw: String): Single<Invitation> =
        if (PATTERN.matches(raw)) {

            val type = getInvitationType(raw) ?: throw Exception("Type can't be a null")
            val link = getLink(raw)
            val desc = getDescription(raw)

            Single.just(
                Invitation(
                    link,
                    desc,
                    type
                )
            )
        } else {
            Single.error(DataNotMatchException())
        }

    private fun getDescription(raw: String): String {
        val start = INVITATION_PREFIX.length + 1
        return raw.substring(start, raw.length - HASH_LENGTH)
    }

    private fun getLink(raw: String): String {
        val hash = raw.substring(raw.length - HASH_LENGTH, raw.length)
        return URL_PATH + hash
    }

    private fun getInvitationType(raw: String): InvitationType? {
        val start = INVITATION_PREFIX.length
        return when (raw.substring(start, start + 1)) {
            "0" -> InvitationType.INFINITE
            "1" -> InvitationType.SINGLE
            else -> null
        }
    }

}