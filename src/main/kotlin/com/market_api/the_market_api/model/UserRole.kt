package com.market_api.the_market_api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.market_api.the_market_api.base.BaseEntity
import javax.persistence.*

@Entity
data class UserRole(

    @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id")
    var role: Role? = null

) : BaseEntity() {
}
