package com.market_api.the_market_api.service

import com.market_api.the_market_api.security.UserPrincipal
import com.market_api.the_market_api.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService

interface JwtUserDetailsService: UserDetailsService {
    fun loadUserByEmail(username: String): UserPrincipal
    fun loadUserByPhone(username: String): UserPrincipal
    fun getAuthority(user: User): MutableSet<GrantedAuthority>
    fun loadUser(username: String): UserPrincipal
}