package com.market_api.the_market_api.repository

import com.market_api.the_market_api.model.Permission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PermissionRepository : JpaRepository<Permission,Long>, JpaSpecificationExecutor<Permission> {
    fun findByIdAndStatusTrue(id: Long): Permission?
    @Query(value = "SELECT * FROM permission WHERE status = true ORDER BY id DESC", nativeQuery = true)
    fun findAllPermissions(): List<Permission>?
}