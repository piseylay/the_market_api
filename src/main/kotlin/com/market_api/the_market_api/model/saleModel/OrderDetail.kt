package com.market_api.the_market_api.model.saleModel

import com.market_api.the_market_api.base.BaseEntity
import javax.persistence.*

@Entity
data class OrderDetail(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    var qty: Long? = 0,
    var price: Double? = 0.0,
    var discountPer: Float? = 0.0F,
    var discountAmount: Double = 0.0,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    var product: Product?=null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", nullable = false)
    var order: Orders? =null,

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "uomId")
    var uom: Uom? = null,
): BaseEntity()
