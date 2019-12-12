/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.messages

import javax.inject.Inject

class MessagesViewLogic @Inject constructor() {

    private lateinit var navigation: GraphController

    fun inject(navigation: GraphController) {
        this.navigation = navigation
    }

    fun onGotoGroups() {
        navigation.moveToGroupsScreen()
    }

}
