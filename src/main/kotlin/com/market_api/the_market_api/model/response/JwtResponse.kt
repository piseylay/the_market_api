package com.market_api.the_market_api.model.response

import java.util.*

class JwtResponse (
        var token: String,
        var expireIn: Date
)