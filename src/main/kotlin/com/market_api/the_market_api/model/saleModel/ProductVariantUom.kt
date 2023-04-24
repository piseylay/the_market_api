package com.market_api.the_market_api.model.saleModel

import com.fasterxml.jackson.annotation.JsonIgnore
import com.market_api.the_market_api.base.BaseEntity
import javax.persistence.*

@Entity
data class ProductVariantUom(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?= 0,
    var price: Double?=0.0,
    var isDefault: Boolean = false,
    var convensionFactory: Float = 0F,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "uomId", nullable = true)
    var uom: Uom?= null,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "productId", insertable = true, updatable = true, nullable = true)
    var product: Product?= null
):BaseEntity()
