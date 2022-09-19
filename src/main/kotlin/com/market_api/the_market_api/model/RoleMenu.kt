package com.market_api.the_market_api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.market_api.the_market_api.base.BaseEntity
import javax.persistence.*

@Entity
data class RoleMenu(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long ?= 0,

    @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnore
    var role: Role?= null,

    @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_item_id", nullable = false)
    @JsonIgnore
    var menuItem: MenuItem?= null
) : BaseEntity() {

}
