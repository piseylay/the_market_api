package com.market_api.the_market_api.service.serivceImplement

import com.market_api.the_market_api.repository.MenuItemRepository
import com.market_api.the_market_api.responseFormat.exception.CustomNotAcceptableException
import com.market_api.the_market_api.responseFormat.exception.CustomNotFoundException
import com.market_api.the_market_api.service.RoleService
import com.market_api.the_market_api.model.*
import com.market_api.the_market_api.repository.PermissionRepository
import com.market_api.the_market_api.repository.RoleMenuRepository
import com.market_api.the_market_api.repository.RolePermissionRepository
import com.market_api.the_market_api.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class RoleServiceImpl : RoleService {

    @Autowired
    lateinit var roleRepository: RoleRepository
    @Autowired
    lateinit var menuItemRepository: MenuItemRepository
    @Autowired
    lateinit var roleMenuRepository: RoleMenuRepository
    @Autowired
    lateinit var permissionRepository: PermissionRepository
    @Autowired
    lateinit var rolePermissionRepository: RolePermissionRepository

    override fun findAllList(q: String?, page: Int, size: Int): Page<Role>? {
        return roleRepository.findAll({ root, _, cb ->
            val predicates = ArrayList<javax.persistence.criteria.Predicate>()
            if (q != null) {
                val roleName = cb.like(cb.upper(root.get("roleName")), "%${q.toUpperCase()}%")
                val desc = cb.like(cb.upper(root.get("description")), "%${q.toUpperCase()}%")
                predicates.add(cb.or(roleName, desc))
            }
            predicates.add(cb.isTrue(root.get<Boolean>("status")))
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
    }

    override fun findById(id: Long): Role? {
        return roleRepository.findByIdAndStatusTrue(id)
                ?: throw CustomNotFoundException("Role id $id does not exist")
    }

    override fun addNew(t: Role): Role? {
        checkRoleExceptions(t)
        return roleRepository.save(t)
    }

    override fun updateObj(id: Long, t: Role): Role? {
        val role = findById(id)
        if (!t.status) role?.status = false
        else {
            checkRoleExceptions(t)
            role?.roleName = t.roleName
            role?.description = t.description
        }
        return roleRepository.save(role!!)
    }

    override fun findAll(): List<Role>? = roleRepository.findAllRoles()

    override fun roleApplyMenus(roleId: Long, menuList: LongArray): List<RoleMenu> {
        roleMenuRepository.deleteRoleMenuByRoleId(roleId)
        val roleMenuList = ArrayList<RoleMenu>()
        for (menu in menuList) {
            checkMenuItemExceptionById(menu)
            val roleMenu = RoleMenu()
            roleMenu.role = Role(roleId)
            roleMenu.menuItem = MenuItem(menu)
            roleMenu.status = true
            roleMenuList.add(roleMenu)
        }
        if (roleMenuList.size < 1) throw CustomNotFoundException("zero result.")
        return roleMenuRepository.saveAll(roleMenuList)
    }
    override fun roleApplyPermissions(roleId: Long, permissionList: LongArray): List<RolePermission>? {
        rolePermissionRepository.deleteRolePermissionByRoleId(roleId)
        val rolePermissionList = ArrayList<RolePermission>()
        for (permission in permissionList) {
            checkPermissionExceptionById(permission)
            val rolePermission = RolePermission()
            rolePermission.role = Role(roleId)
            rolePermission.permission = Permission(permission)
            rolePermission.status = true
            rolePermissionList.add(rolePermission)
        }
        if (rolePermissionList.size < 1) throw CustomNotFoundException("zero result.")
        return rolePermissionRepository.saveAll(rolePermissionList)
    }

    override fun findRoleMenuByRoleId(id: Long): List<Long>? {
        return roleMenuRepository.findRoleMenuByRoleId(id)
    }

    override fun findRolePermissionByRoleId(id: Long): List<Long>? {
        return rolePermissionRepository.findRolePermissionByRoleId(id)
    }

    private fun checkRoleExceptions(role: Role) {
        role.roleName ?: throw CustomNotAcceptableException("Invalid Role")
    }
    private fun checkMenuItemExceptionById(id: Long) {
        menuItemRepository.findByIdAndStatusTrue(id) ?: throw CustomNotFoundException("MenuItem id $id doesn't exist")
    }
    private fun checkPermissionExceptionById(id: Long) {
        permissionRepository.findByIdAndStatusTrue(id) ?: throw CustomNotFoundException("Permission id $id doesn't exist")
    }

    override fun findByUserId(userId: Long): List<Long>? {
        val objList = roleRepository.findAllByUserId(userId)
        val idList = ArrayList<Long>()
        for (obj in objList!!){
            obj.id?.let { idList.add(it) }
        }
       return idList
    }
}