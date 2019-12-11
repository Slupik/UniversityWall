/*
 * Copyright (c) 2019. by Sebastian Witasik
 * All rights reserved. No part of this application may be reproduced or be part of other software, without the prior written permission of the publisher. For permission requests, write to the author(WitasikSebastian@gmail.com).
 */

package io.github.slupik.network.dagger

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.github.slupik.network.DOMAIN_URL
import io.github.slupik.network.authorization.retrofit.authorization.AuthorizationService
import io.github.slupik.network.authorization.retrofit.registration.RegistrationService
import io.github.slupik.network.message.retrofit.GroupsDownloadingService
import io.github.slupik.network.message.retrofit.MessagesDownloadingService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Sebastian Witasik on 08.12.2019.
 * E-mail: SebastianWitasik@gmail.com
 * All rights reserved & copyright ©
 */
@Module
class RetrofitServices {

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(DOMAIN_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

    @Provides
    fun provideAuthorizationService(retrofit: Retrofit): AuthorizationService =
        retrofit.create(AuthorizationService::class.java)

    @Provides
    fun provideRegistrationService(retrofit: Retrofit): RegistrationService =
        retrofit.create(RegistrationService::class.java)

    @Provides
    fun provideMessagesDownloadingService(retrofit: Retrofit): MessagesDownloadingService =
        retrofit.create(MessagesDownloadingService::class.java)

    @Provides
    fun provideGroupsDownloadingService(retrofit: Retrofit): GroupsDownloadingService =
        retrofit.create(GroupsDownloadingService::class.java)

}