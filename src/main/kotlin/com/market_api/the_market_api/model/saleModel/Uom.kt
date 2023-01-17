package com.market_api.the_market_api.model.saleModel

import com.fasterxml.jackson.annotation.JsonIgnore
import com.market_api.the_market_api.base.BaseEntity
import javax.persistence.*

@Entity
data class Uom(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?= 0,
    @Column(name = "name", nullable = false)
    var name: String? = null,
    @Column(name = "description", nullable = false)
    var description: String? = null,
    @Column(name = "abbr", nullable = false)
    var abbr: String? = null,
    var isDefault: Boolean = false,
    @OneToMany(mappedBy = "uom", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var productVariantUom: MutableList<ProductVariantUom>? = null
):BaseEntity()
