package com.market_api.the_market_api.service

import com.market_api.the_market_api.base.BaseService
import com.market_api.the_market_api.model.Role
import com.market_api.the_market_api.model.RoleMenu
import com.market_api.the_market_api.model.RolePermission

interface RoleService : BaseService<Role> {
    fun roleApplyMenus(roleId: Long, menuList: LongArray): List<RoleMenu>?
    fun roleApplyPermissions(roleId: Long, permissionList: LongArray): List<RolePermission>?
    fun findRoleMenuByRoleId(id: Long): List<Long>?
    fun findRolePermissionByRoleId(id: Long): List<Long>?
    fun findByUserId(userId : Long) : List<Long>?
}