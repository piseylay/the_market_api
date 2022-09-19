package com.market_api.the_market_api.security

import com.market_api.the_market_api.service.JwtUserDetailsService
import com.market_api.the_market_api.utils.JwtTokenUtil
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil
    @Autowired
    lateinit var jwtUserDetailsService: JwtUserDetailsService

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

        val requestTokenHeader = request.getHeader("Authorization")
        var userCredential: List<String>? = null
        var jwtToken: String? = null
        /**
         * JWT Token is in the form "Bearer token". Remove Bearer word and get
         * only the Token
         */
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7)
            try {
                userCredential = jwtTokenUtil.getUserFromToken(jwtToken).split("|")
            } catch (e: IllegalArgumentException) {
                println("Unable to get JWT Token")
            } catch (e: ExpiredJwtException) {
                println("JWT Token has expired")
            }
        }
        /**
         * Once we get the token validate it.
         */
        if (userCredential != null && SecurityContextHolder.getContext().authentication == null) {
            val username = userCredential[1]
            val userDetails = jwtUserDetailsService.loadUserByUsername(username)
            /**
             * if token is valid configure Spring Security to manually set
             * authentication
             */
            if (jwtTokenUtil.validateToken(jwtToken!!, userDetails as UserPrincipal)!!) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                /**
                 * After setting the Authentication in the context, we specify
                 * that the current user is authenticated. So it passes the
                 * Spring Security Configurations successfully.
                 */
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        filterChain.doFilter(request, response)
    }
}