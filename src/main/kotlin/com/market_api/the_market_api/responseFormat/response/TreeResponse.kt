package com.market_api.the_market_api.responseFormat.response

class TreeResponse <T> (
    var data: T? = null,
    var children: List<TreeResponse<T>>? = null
)