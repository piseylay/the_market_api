package com.market_api.the_market_api.repository

import com.market_api.the_market_api.model.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface UserRoleRepository : JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {
    fun findByIdAndStatusTrue(userId: Long): List<UserRole>
    @Query(value = "UPDATE user_role SET status = false WHERE user_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    fun deleteUserRoleByUserId(userId: Long)
}