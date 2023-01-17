package com.market_api.the_market_api.model.saleModel

import com.fasterxml.jackson.annotation.JsonIgnore
import com.market_api.the_market_api.base.BaseEntity
import javax.persistence.*

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?=0,
    var name: String?= null,
    var code: String?= null,
    var barcode: String?= null,
    var _active: Boolean?= true,
    var weight: Float?= null,
    var description: String? = null,
    var thumbnail: String? = null,
    var imagePath: String? = null,
    var price: Double? = 0.0,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId", nullable = false)
    var category: Category? = null,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    //@JsonIgnore
    var productVariantUom: MutableList<ProductVariantUom>? = null,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var orderDetail: MutableList<OrderDetail>? = null,

    @OneToMany(mappedBy = "product", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var invoiceDetail: MutableList<InvoiceDetail>? = null

):BaseEntity()
