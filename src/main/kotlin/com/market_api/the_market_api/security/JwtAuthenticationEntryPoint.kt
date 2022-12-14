package com.market_api.the_market_api.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint: AuthenticationEntryPoint {
    override fun commence(p0: HttpServletRequest?, p1: HttpServletResponse?, p2: AuthenticationException?) {
        p1?.sendError(HttpServletResponse.SC_UNAUTHORIZED, p2?.message)
    }
}