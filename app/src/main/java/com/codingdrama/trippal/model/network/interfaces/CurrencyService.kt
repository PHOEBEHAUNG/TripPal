package com.codingdrama.trippal.model.network.interfaces

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * https://freecurrencyapi.com/docs/#official-libraries
 * This interface is used to define the methods for fetching currency data.
 */

/**
 * {
 *     "data": {
 *         "AED": {
 *             "symbol": "AED",
 *             "name": "United Arab Emirates Dirham",
 *             "symbol_native": "د.إ",
 *             "decimal_digits": 2,
 *             "rounding": 0,
 *             "code": "AED",
 *             "name_plural": "UAE dirhams"
 *         },
 *         ...
 * }
 */
data class CurrencyDetails(
    val symbol: String,
    val name: String,
    val symbolNative: String,
    val decimalDigits: Int,
    val rounding: Int,
    val code: String,
    val namePlural: String
)

data class CurrencyDetailsResponse(
    val data: Map<String, CurrencyDetails>
)


data class CurrencyResponse(
    val data: Map<String, Float>
)

/**
 * error response
 * {
 *     "message": "Validation error",
 *     "errors": {
 *         "currencies": [
 *             "The selected currencies is invalid."
 *         ]
 *     },
 *     "info": "For more information, see documentation: https://freecurrencyapi.com/docs/status-codes#_422"
 * }
 */
data class ErrorResponse(
    val message: String,
    val errors: Map<String, List<String>>,
    val info: String
)

interface CurrencyService {
    @GET("currencies")
    suspend fun getSupportedCurrencies(): CurrencyDetailsResponse?

    /**
     * Example of a latest rate request:
     * base_currency : The base currency to which all results are behaving relative to By default all values are based on USD
     * currencies : A list of comma separated currency codes which you want to get (EUR,USD,CAD) By default all available currencies will be show (e.g. EUR,GBP)
     */
    @GET("latest")
    suspend fun getLatestRate(@Query("base_currency") baseCurrency: String, @Query("currencies") currencies: String): CurrencyResponse?

    /**
     * Example of a historical rate request:
     * date : Start date to retrieve historical rates from (format: 2021-12-31)
     * base_currency : The base currency to which all results are behaving relative to By default all values are based on USD
     * currencies : A list of comma separated currency codes which you want to get (EUR,USD,CAD) By default all available currencies will be show (e.g. EUR,GBP)
     */
    @GET("historical")
    suspend fun getHistoricalRate(@Query("date") date: String, @Query("base_currency") baseCurrency: String, @Query("currencies") currencies: String): CurrencyResponse?
}