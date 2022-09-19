package com.market_api.the_market_api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.market_api.the_market_api.base.BaseEntity
import javax.persistence.*

@Entity
data class MenuItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long ?= 0,
    @Column(name = "icon_bg", length = 50)
    var iconBg: String? = null,
    @Column(name = "routing", length = 50)
    var routing: String? = null,
    @Column(name = "icon_class", length = 50)
    var icon: String? = null,
    @Column(name = "external_link", length = 50)
    var externalLink: String? = null,

    var selected : Boolean?=false,
    var closable : Boolean?=true,

    @JsonProperty("_active")
    @Column(name = "is_active", nullable = false)
    var _active: Boolean? = true,

    @JsonProperty("_disable")
    @Column(name = "is_disable", nullable = false)
    var _disable: Boolean? = true,

    @Column(name = "menu_order")
    var menuOrder: Int? = 0,
    @Column(name = "title", length = 50)
    var title: String? = null,
    @Column(name = "icon_color", length = 50)
    var iconColor: String? = null,

    @JsonProperty("_groupTitle")
    @Column(name = "is_group_title", nullable = false)
    var _groupTitle: Boolean? = false,

    @Column(name = "color", length = 50)
    var color: String? = null,
    @Column(name = "parent_id")
    var parentId: Long? = null,

    @Transient
    var items: MutableList<MenuItem>? = null,

    @JsonIgnore
    @OneToMany(mappedBy = "menuItem", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var roleMenuList: List<RoleMenu>? = null
) : BaseEntity(){
    constructor(id : Long): this(id,null)
}
