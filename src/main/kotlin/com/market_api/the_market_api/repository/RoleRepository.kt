package com.market_api.the_market_api.repository

import com.market_api.the_market_api.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role,Long> , JpaSpecificationExecutor<Role>{
    fun findByIdAndStatusTrue(id: Long) : Role?
    @Query("SELECT * FROM role WHERE status = true ORDER BY id DESC",nativeQuery = true)
    fun findAllRoles() : List<Role>?

    @Query("SELECT role.* FROM role " +
            "INNER JOIN user_role ON user_role.role_id = role.id " +
            "INNER JOIN users ON user_role.user_id = users.id " +
            "WHERE users.id=?1 AND role.status = true AND users.status=true AND user_role.status = true",nativeQuery = true)
    fun findAllByUserId(userId : Long) : MutableList<Role>?
}