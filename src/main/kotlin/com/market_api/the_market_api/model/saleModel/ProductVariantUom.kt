package com.market_api.the_market_api.model.saleModel

import com.market_api.the_market_api.base.BaseEntity
import javax.persistence.*

@Entity
data class ProductVariantUom(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?= 0,
    var price: Double?=0.0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uomId", nullable = false)
    var uom: Uom?= null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    var product: Product?= null
):BaseEntity()
