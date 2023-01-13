package com.market_api.the_market_api.model.saleModel

import com.fasterxml.jackson.annotation.JsonIgnore
import com.market_api.the_market_api.base.BaseEntity
import com.market_api.the_market_api.model.User
import javax.persistence.*

@Entity
data class Invoice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var subTotal: Double? = 0.0,
    var total: Double? = 0.0,
    var discountPer : Double? = 0.0,
    var address: String? = null,

   /* @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "orderId", nullable = false, insertable=false, updatable=false)
    var order: Order? = null,*/

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "userId", nullable = false,  insertable=false, updatable=false)
    var user: User?= null,

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "paymentId", nullable = false, insertable=false, updatable=false)
    var payment: Payment?= null,

    @OneToMany(mappedBy = "invoice", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var invoiceDetail: MutableList<InvoiceDetail>? = null,

):BaseEntity()
