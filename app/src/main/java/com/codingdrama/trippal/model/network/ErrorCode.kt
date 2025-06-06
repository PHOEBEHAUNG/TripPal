package com.codingdrama.trippal.model.network

enum class ErrorCode(val code: Int, val message: String) {
    SUCCESS(90000, "Success"),
    UNKNOWN(90001, "Unknown error"),
    INTERNET(90002, "Internet connection issue"),
    SERVER_ISSUE(90003, "Server issue")
}