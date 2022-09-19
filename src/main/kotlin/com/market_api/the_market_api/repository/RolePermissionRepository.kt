package com.market_api.the_market_api.repository

import com.market_api.the_market_api.model.RolePermission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface RolePermissionRepository : JpaRepository<RolePermission,Long> , JpaSpecificationExecutor<RolePermission>{
    @Query(value = "UPDATE role_permission SET status = false WHERE role_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    fun deleteRolePermissionByRoleId(roleId: Long)

    @Query(value = "SELECT permission_id FROM role_permission WHERE role_id = ?1 AND status = true", nativeQuery = true)
    fun findRolePermissionByRoleId(id: Long): List<Long>
}