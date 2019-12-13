/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.model.message

import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Sebastian Witasik on 13.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
class MessagesSyncingService @Inject constructor(
    private val downloader: MessagesDownloader,
    private val repository: MessagesRepository
) : MessagesSynchronizer {

    override fun refresh(): Single<List<NewMessage>> =
        repository.getAll()
            .flatMap {rList ->
                downloader.downloadMessages()
                    .doOnSuccess {
                        repository.save(it)
                    }
                    .map {sList ->
                        sList
                            .filter {sMess ->
                                rList
                                    .none {rMess ->
                                        sMess.id == rMess.id
                                    }
                            }
                    }
            }

}