package com.market_api.the_market_api.model.saleModel

import com.fasterxml.jackson.annotation.JsonIgnore
import com.market_api.the_market_api.base.BaseEntity
import com.market_api.the_market_api.model.User
import javax.persistence.*

@Entity
data class Orders(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,

    var totalQty: Long? = 0,
    var total: Long? = 0,
    var subTotal: Long? = 0,
    var series: String? = null,
    var discountPer: Float? = 0.0F,
    var discountAmount: Double? = 0.0,

    @OneToOne(mappedBy = "order", fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "paymentId", nullable = false)
    var payment: Payment? = null,

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "deliveryId", nullable = false)
    var delivery: Delivery?= null,

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    var user: User?= null,

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var orderDetail: MutableList<OrderDetail>? = null,

): BaseEntity()