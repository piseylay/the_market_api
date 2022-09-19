package com.market_api.the_market_api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.market_api.the_market_api.base.BaseEntity
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "users")
@JsonIgnoreProperties(value = ["password"], allowSetters = true)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    @NotBlank(message = "Username is mandatory")
    var username: String? = null,
    @NotBlank(message = "Email is mandatory")
    var email: String? = null,
    @NotBlank(message = "Phone is mandatory")
    var phone: String? = null,
    @Column(name = "image_path")
    var imagePath: String? = null,
    @NotBlank(message = "Password is mandatory")
    var password: String? = null,
    @Column(name = "is_admin", nullable=false)
    var _admin: Boolean ?= true,
    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    var userRole: MutableList<UserRole>?=null,
): BaseEntity() {
    constructor(id: Long):this(id,null)
}
