package com.market_api.the_market_api.responseFormat.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class CustomBadRequestException(message: String) : RuntimeException(message)