package com.market_api.the_market_api.repository

import com.market_api.the_market_api.model.RoleMenu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface RoleMenuRepository : JpaRepository<RoleMenu,Long>, JpaSpecificationExecutor<RoleMenu>{
    @Query(value = "SELECT menu_item_id FROM role_menu WHERE role_id = ?1 AND status = true", nativeQuery = true)
    fun findRoleMenuByRoleId(id: Long): List<Long>

    @Query(value = "UPDATE role_menu SET status = false WHERE role_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    fun deleteRoleMenuByRoleId(roleId: Long)

}