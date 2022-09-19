package com.market_api.the_market_api.model

import com.market_api.the_market_api.base.BaseEntity
import javax.persistence.*

@Entity
data class Permission(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?= 0,
    @Column(name = "function_name", nullable = false, length = 100)
    var functionName: String?= null,
    @Column(name = "function_order", nullable = false)
    var functionOrder: Int?= 0,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "feature_id", nullable = false)
    var feature: Feature? = null,

    @OneToMany(mappedBy = "permission", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var rolePermissionList: List<RolePermission>? = null

) : BaseEntity(){
    constructor(id:Long):this(id,null)
}
