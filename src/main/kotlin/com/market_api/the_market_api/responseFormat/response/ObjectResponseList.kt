package com.market_api.the_market_api.responseFormat.response


data class ObjectResponseList(
        var response : Response?= null,
        var results : List<Any> ?= null
)