/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.dagger

import dagger.Binds
import dagger.Module
import io.github.slupik.model.Converter
import io.github.slupik.model.group.Group
import io.github.slupik.universitywall.screen.group.model.DisplayableGroup
import io.github.slupik.universitywall.screen.group.model.GroupConverter

/**
 * Created by Sebastian Witasik on 05.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
abstract class GroupsModule {

    @Binds
    abstract fun groupConverter(factory: GroupConverter):
            Converter<Group, DisplayableGroup>

}