package com.market_api.the_market_api.model.saleModel

import com.market_api.the_market_api.base.BaseEntity
import javax.persistence.*

@Entity
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?= 0,
    var amount: Double?= 0.0,

    var paymentMethod: String? = null,

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "orderId", nullable = false)
    var order: Orders?= null
):BaseEntity()
