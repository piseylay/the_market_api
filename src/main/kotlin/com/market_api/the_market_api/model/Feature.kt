package com.market_api.the_market_api.model

import com.market_api.the_market_api.base.BaseEntity
import javax.persistence.*

@Entity
data class Feature(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?= 0,
    @Column(name = "feature_name", nullable = false, length = 100)
    var featureName: String? = null,
    @Column(name = "feature_order", nullable = false)
    var featureOrder: Int?= 0
): BaseEntity()
