/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.group

/**
 * Created by Sebastian Witasik on 12.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
abstract class GroupActionException(
    message: String
): Exception(message)

class GroupJoinException(
    code: Int
): GroupActionException("Error during try of joining to group. Id of the error: $code")

class GroupLeaveException(
    code: Int
): GroupActionException("Error during try of leaving of group. Id of the error: $code")