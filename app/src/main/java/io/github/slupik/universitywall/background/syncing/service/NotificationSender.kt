/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.background.syncing.service

import io.github.slupik.model.message.NewMessage

/**
 * Created by Sebastian Witasik on 13.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
interface NotificationSender {

    fun notifyAboutNewMessages(messages: List<NewMessage>)

}