/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.background.syncing.dagger

import dagger.Binds
import dagger.Module
import io.github.slupik.universitywall.background.syncing.service.GroupedNotificationSender
import io.github.slupik.universitywall.background.syncing.service.NotificationSender

/**
 * Created by Sebastian Witasik on 13.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
abstract class SynchronizationModule {

    @Binds
    abstract fun notificationSender(sender: GroupedNotificationSender): NotificationSender

}