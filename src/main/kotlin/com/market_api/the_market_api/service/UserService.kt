package com.market_api.the_market_api.service

import com.market_api.the_market_api.base.BaseService
import com.market_api.the_market_api.model.User
import com.market_api.the_market_api.model.UserRole
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

interface UserService : BaseService<User> {
    fun uploadImage(id: Long?, file: MultipartFile?): User?
    fun userApplyRoles(userId: Long, roleList: LongArray): List<UserRole>?
    fun findByUsername(username: String): User?
    fun getUserInfo(http: HttpServletRequest) : User?
}