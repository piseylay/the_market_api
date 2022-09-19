package com.market_api.the_market_api.service.serivceImplement

import com.market_api.the_market_api.security.UserPrincipal
import com.market_api.the_market_api.model.User
import com.market_api.the_market_api.repository.UserRepository
import com.market_api.the_market_api.service.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsServiceImp: JwtUserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun loadUserByEmail(username: String): UserPrincipal {
        val u = userRepository.findByEmailAndStatusTrue(username) ?: throw UsernameNotFoundException("User not found with email: $username")
        return UserPrincipal(u.id!!, u.username, u.email, u.password!!, getAuthority(u))
    }

    override fun loadUserByPhone(username: String): UserPrincipal {
        val u = userRepository.findByPhoneAndStatusTrue(username)
                ?: throw UsernameNotFoundException("User not found with phone: $username")
        return UserPrincipal(u.id!!, u.username, u.email, u.password!!, getAuthority(u))
    }
    override fun loadUser(username: String): UserPrincipal {
        /*val  u = userRepository.findByUsernameOrEmailOrPhoneAndStatusTrue(username)
            ?: throw UsernameNotFoundException("User not found ")
        return UserPrincipal(u.id!!, u.username, u.email, u.password!!, getAuthority(u))*/
        TODO()
    }
    override fun getAuthority(user: User): MutableSet<GrantedAuthority> {
        val authorities: MutableSet<GrantedAuthority> = HashSet()
        authorities.add(SimpleGrantedAuthority("ROLE_ADMIN"))
        return authorities
    }

    override fun loadUserByUsername(username: String): UserPrincipal {
        val u = userRepository.findByUsernameAndStatusTrue(username)
                ?: throw UsernameNotFoundException("User not found with username: $username")
        return UserPrincipal(u.id!!, u.username, u.email, u.password!!, getAuthority(u))
    }
}