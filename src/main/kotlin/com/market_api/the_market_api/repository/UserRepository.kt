package com.market_api.the_market_api.repository

import com.market_api.the_market_api.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    fun findByUsernameAndStatusTrue(username: String): User?
    fun findByEmailAndStatusTrue(email: String): User?
    fun findByPhoneAndStatusTrue(phone: String): User?
    fun findAllByStatusTrueOrderByIdDesc(): List<User>?
    fun findByIdAndStatusTrue(id: Long?): User?
    fun existsByUsername(username: String?): Boolean
    fun findByIdAndStatus(id: Long?, status: Boolean): User?
    //fun findByUsernameOrEmailOrPhoneAndStatusTrue(username: String): User?
}