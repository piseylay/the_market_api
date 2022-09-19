package com.market_api.the_market_api.responseFormat.exception

class CustomException(status: Int, message: String) : RuntimeException(message) {
    var status: Int = 0
    init {
        this.status = status
    }
}