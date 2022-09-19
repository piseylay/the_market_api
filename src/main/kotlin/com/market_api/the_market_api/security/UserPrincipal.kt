package com.market_api.the_market_api.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(var id:Long, var name: String?,var email: String?,var pass: String,val auth: MutableCollection<out GrantedAuthority>?) : UserDetails {

    companion object {
        fun create(id: Long,name:String?,email:String?,password: String,authorities: MutableCollection<out GrantedAuthority>?): UserPrincipal {
            return UserPrincipal(id,name,email,password,authorities)
        }
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return this.auth
    }

    override fun isEnabled(): Boolean {
        return true;
    }

    override fun getUsername(): String? {
        return this.name
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true;
    }

    override fun getPassword(): String {
        return this.pass
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}
