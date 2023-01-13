package com.market_api.the_market_api.model.saleModel

import com.market_api.the_market_api.base.BaseEntity
import javax.persistence.*

@Entity
data class InvoiceDetail(
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
    @JoinColumn(name = "invoiceId", nullable = false)
    var invoice: Invoice? =null,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uomId", nullable = false)
    var uom: Uom? = null,
):BaseEntity()
