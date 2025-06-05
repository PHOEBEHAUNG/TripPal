package com.codingdrama.trippal.data

/**
 * "AUD": 1.5407801857,
 * "BGN": 1.7178401754,
 * "BRL": 5.634750803,
 * "CAD": 1.3677602265,
 * "CHF": 0.8180500897,
 * "CNY": 7.1881309902,
 * "CZK": 21.6916234267,
 * "DKK": 6.5306407782,
 * "EUR": 0.8754501168,
 * "GBP": 0.7379600916,
 * "HKD": 7.8434613771,
 * "HRK": 6.2471807554,
 * "HUF": 352.7670217303,
 * "IDR": 16291.762091983,
 * "ILS": 3.4864706481,
 * "INR": 85.863683449,
 * "ISK": 128.6770832073,
 * "JPY": 142.7506947566,
 * "KRW": 1360.5076110775,
 * "MXN": 19.1989537681,
 * "MYR": 4.2484605227,
 * "NOK": 10.1042713922,
 * "NZD": 1.6594402566,
 * "PHP": 55.6682662233,
 * "PLN": 3.7433706558,
 * "RON": 4.4208006296,
 * "RUB": 79.1578534047,
 * "SEK": 9.5766311454,
 * "SGD": 1.2856201599,
 * "THB": 32.5534150509,
 * "TRY": 39.2254447066,
 * "USD": 1,
 * "ZAR": 17.8262025031
 * EUR	Euro
 * USD	US Dollar
 * JPY	Japanese Yen
 * BGN	Bulgarian Lev
 * CZK	Czech Republic Koruna
 * DKK	Danish Krone
 * GBP	British Pound Sterling
 * HUF	Hungarian Forint
 * PLN	Polish Zloty
 * RON	Romanian Leu
 * SEK	Swedish Krona
 * CHF	Swiss Franc
 * ISK	Icelandic Króna
 * NOK	Norwegian Krone
 * HRK	Croatian Kuna
 * RUB	Russian Ruble
 * TRY	Turkish Lira
 * AUD	Australian Dollar
 * BRL	Brazilian Real
 * CAD	Canadian Dollar
 * CNY	Chinese Yuan
 * HKD	Hong Kong Dollar
 * IDR	Indonesian Rupiah
 * ILS	Israeli New Sheqel
 * INR	Indian Rupee
 * KRW	South Korean Won
 * MXN	Mexican Peso
 * MYR	Malaysian Ringgit
 * NZD	New Zealand Dollar
 * PHP	Philippine Peso
 * SGD	Singapore Dollar
 * THB	Thai Baht
 * ZAR	South African Rand
 */

enum class CurrencyEnum(currencyName: String) {
    EUR("Euro"), USD("US Dollar"), JPY("Japanese Yen"),
    BGN("Bulgarian Lev"), CZK("Czech Republic Koruna"), DKK("Danish Krone"),
    GBP("British Pound Sterling"), HUF("Hungarian Forint"), PLN("Polish Zloty"),
    RON("Romanian Leu"), SEK("Swedish Krona"), CHF("Swiss Franc"),
    ISK("Icelandic Króna"), NOK("Norwegian Krone"), HRK("Croatian Kuna"),
    RUB("Russian Ruble"), TRY("Turkish Lira"), AUD("Australian Dollar"),
    BRL("Brazilian Real"), CAD("Canadian Dollar"), CNY("Chinese Yuan"),
    HKD("Hong Kong Dollar"), IDR("Indonesian Rupiah"),
    ILS("Israeli New Sheqel"), INR("Indian Rupee"), KRW("South Korean Won"),
    MXN("Mexican Peso"), MYR("Malaysian Ringgit"), NZD("New Zealand Dollar"),
    PHP("Philippine Peso"), SGD("Singapore Dollar"),
    THB("Thai Baht"), ZAR("South African Rand");

    companion object {
        fun fromString(value: String): CurrencyEnum? {
            return entries.find { it.name.equals(value, ignoreCase = true) }
        }
    }
}