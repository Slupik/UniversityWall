/*
 * Copyright (c) 2020. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.universitywall.screen.group

/**
 * Created by Sebastian Witasik on 11.04.2020.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
sealed class DialogCommand

object GroupsRefreshingError : DialogCommand()

object GroupLeavingError : DialogCommand()

object GroupJoiningError : DialogCommand()

data class GroupJoining(
    val name: String
) : DialogCommand()