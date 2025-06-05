package com.codingdrama.trippal.model.network.data


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
data class CurrencyDataErrorResponse(
    val message: String,
    val errors: Map<String, List<String>>,
    val info: String
)
