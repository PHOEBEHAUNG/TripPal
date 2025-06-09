package com.codingdrama.trippal.di

import com.codingdrama.trippal.model.network.CurrencyApiClient
import com.codingdrama.trippal.model.network.KiaApiClient
import com.codingdrama.trippal.repository.CurrencyRateRepository
import com.codingdrama.trippal.repository.FlightInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @CurrencyRateRepo
    @Provides
    fun provideCurrencyRateRepo(): CurrencyRateRepository {
        return CurrencyRateRepository(CurrencyApiClient)
    }

    @FlightInfoRepo
    @Provides
    fun provideFlightInfoRepo(): FlightInfoRepository {
        return FlightInfoRepository(KiaApiClient)
    }

    @Provides
    fun provideCurrencyApiClientSource(): CurrencyApiClient {
        return CurrencyApiClient
    }

    @Provides
    fun provideKiaApiClientSource(): KiaApiClient {
        return KiaApiClient
    }
}
