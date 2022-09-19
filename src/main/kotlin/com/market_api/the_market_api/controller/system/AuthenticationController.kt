package com.market_api.the_market_api.controller.system

import com.market_api.the_market_api.model.request.JwtRequest
import com.market_api.the_market_api.responseFormat.response.ResponseObjectMap
import com.market_api.the_market_api.security.UserPrincipal
import com.market_api.the_market_api.service.JwtUserDetailsService
import com.market_api.the_market_api.utils.AppConstant
import com.market_api.the_market_api.utils.JwtTokenUtil
import com.market_api.the_market_api.utils.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(AppConstant.MAIN_PATH)
class AuthenticationController {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager
    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil
    @Autowired
    lateinit var userDetailsService: JwtUserDetailsService
    var response = ResponseObjectMap()

    @PostMapping("authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest): MutableMap<String, Any> {

        /**
         * load user by phone or email from db to authenticate
         * if match authentication then return user principle
         */
        val username: String = authenticationRequest.username
        val userDetails = when {
            Utils.isValidEmail(username) -> {
                userDetailsService.loadUserByEmail(username)
            }
            Utils.isValidPhone(username) -> {
                userDetailsService.loadUserByPhone(username)
            }
            else -> {
                userDetailsService.loadUserByUsername(username)
            }
        } as UserPrincipal

        authenticate(userDetails.username!!, authenticationRequest.password)
        val token = jwtTokenUtil.generateToken(userDetails)
        return  response.responseObject(token)
    }

    @PostMapping("/refresh-token")
    fun refreshToken(http: HttpServletRequest): MutableMap<String, Any> {
        val username = jwtTokenUtil.getJwtFromRequest(http).split("|")[1]
        val userDetails = userDetailsService.loadUserByUsername(username)
        return response.responseObject(jwtTokenUtil.generateToken(userDetails as UserPrincipal))
    }

    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", BadCredentialsException("Incorrect username or password"))
        }
    }
}