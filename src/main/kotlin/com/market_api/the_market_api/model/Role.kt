package com.market_api.the_market_api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.market_api.the_market_api.base.BaseEntity
import javax.persistence.*

@Entity
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    @Column(name = "role_name", unique = true)
    var roleName: String? = null,
    @Column(name = "description")
    var description: String? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    private val roleMenus: List<RoleMenu>? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var userRole: MutableList<UserRole>? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var rolePermission: MutableList<RolePermission>? = null

) : BaseEntity() {
    constructor(id: Long) : this(id, "")
}
