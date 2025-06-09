package com.codingdrama.trippal.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CurrencyRateRepo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FlightInfoRepo

//@Qualifier
//@Retention(AnnotationRetention.SOURCE)
//annotation class CurrencyApiClientSource
//
//@Qualifier
//@Retention(AnnotationRetention.SOURCE)
//annotation class KiaApiClientSource