package com.market_api.the_market_api.model.saleModel

import com.market_api.the_market_api.base.BaseEntity
import com.market_api.the_market_api.model.User
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
data class Delivery(
    var id: Long?= 0,
    var name: String? = null,
    var address: String? = null,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name= "userId", nullable = false)
    var user: User?= null,

):BaseEntity()
