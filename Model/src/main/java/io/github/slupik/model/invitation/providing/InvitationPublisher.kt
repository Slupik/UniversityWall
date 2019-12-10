/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.invitation.providing

import io.github.slupik.model.invitation.model.Invitation
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Sebastian Witasik on 06.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Singleton
class InvitationPublisher @Inject constructor(): InvitationBroadcaster, InvitationEmitter {

    private val publisher: PublishSubject<Invitation> = PublishSubject.create()

    override val invitations: Observable<Invitation>
        get() = publisher

    override fun broadcast(invitation: Invitation) {
        publisher.onNext(invitation)
    }

}