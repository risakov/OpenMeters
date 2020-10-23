package ru.webant.openmeters.di

import dagger.Module
import dagger.Provides
import ru.webant.domain.gateways.IndicatorGateway
import ru.webant.gateway.retrofit.Api
import ru.webant.gateway.retrofit.gateways.RetrofitIndicatorGateway
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class GatewayModule {

    @Provides
    @Singleton
    fun provideAuthGateway(api: Api): IndicatorGateway {
        return RetrofitIndicatorGateway(api)
    }
}
